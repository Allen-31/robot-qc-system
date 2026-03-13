package com.zioneer.robotqcsystem.service.deploy.impl;

import com.zioneer.robotqcsystem.common.constant.CommonConstant;
import com.zioneer.robotqcsystem.common.exception.BusinessException;
import com.zioneer.robotqcsystem.domain.dto.RoleCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.RolePermissionUpdateDTO;
import com.zioneer.robotqcsystem.domain.dto.RoleUpdateDTO;
import com.zioneer.robotqcsystem.domain.entity.RolePermission;
import com.zioneer.robotqcsystem.domain.entity.SysRole;
import com.zioneer.robotqcsystem.domain.vo.RolePermissionVO;
import com.zioneer.robotqcsystem.domain.vo.RoleVO;
import com.zioneer.robotqcsystem.integration.keycloak.KeycloakAdminClient;
import com.zioneer.robotqcsystem.mapper.SysRoleMapper;
import com.zioneer.robotqcsystem.service.deploy.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 角色管理服务实现（Keycloak）：角色增删改查走 Keycloak Realm Roles；权限配置仍存本地 sys_role_permission。
 */
@Slf4j
@Primary
@Service
@RequiredArgsConstructor
public class KeycloakRoleServiceImpl implements RoleService {

    private final KeycloakAdminClient keycloakAdmin;
    private final SysRoleMapper sysRoleMapper;

    private static final String KEYCLOAK_DEFAULT_ROLE_PREFIX = "default-roles-";
    private static final Set<String> KEYCLOAK_INTERNAL_ROLES = Set.of(
            "uma_authorization",
            "offline_access"
    );

    @Override
    public List<RoleVO> list(String keyword) {
        List<Map<String, Object>> kcRoles = keycloakAdmin.listRealmRoles();
        return kcRoles.stream()
                .map(this::toRoleVO)
                .filter(vo -> !isKeycloakDefaultRole(vo.getCode()))
                .filter(vo -> !KEYCLOAK_INTERNAL_ROLES.contains(vo.getCode()))
                .filter(vo -> keyword == null || keyword.isBlank()
                        || (vo.getCode() != null && vo.getCode().toLowerCase().contains(keyword.toLowerCase()))
                        || (vo.getName() != null && vo.getName().toLowerCase().contains(keyword.toLowerCase()))
                        || (vo.getDescription() != null && vo.getDescription().toLowerCase().contains(keyword.toLowerCase())))
                .collect(Collectors.toList());
    }

    private boolean isKeycloakDefaultRole(String name) {
        return name != null && name.startsWith(KEYCLOAK_DEFAULT_ROLE_PREFIX);
    }

    private boolean isKeycloakInternalRole(String code) {
        return code != null && KEYCLOAK_INTERNAL_ROLES.contains(code);
    }

    private RoleVO toRoleVO(Map<String, Object> r) {
        RoleVO vo = new RoleVO();
        vo.setCode((String) r.get("name"));
        vo.setName((String) r.get("name"));
        vo.setDescription((String) r.get("description"));
        vo.setMemberCount(0);
        vo.setUpdatedAt(null);
        return vo;
    }

    @Override
    public void create(RoleCreateDTO dto) {
        if (isKeycloakDefaultRole(dto.getCode())) {
            throw new BusinessException("角色编码不能以 " + KEYCLOAK_DEFAULT_ROLE_PREFIX + " 开头");
        }
        if (isKeycloakInternalRole(dto.getCode())) {
            throw new BusinessException("该角色编码为 Keycloak 内置角色，请使用其他编码");
        }
        if (keycloakAdmin.getRealmRoleByName(dto.getCode()) != null) {
            throw new BusinessException("角色编码已存在: " + dto.getCode());
        }
        keycloakAdmin.createRealmRole(dto.getCode(), dto.getDescription());
        log.info("create role via Keycloak, code={}", dto.getCode());
    }

    @Override
    public void update(String code, RoleUpdateDTO dto) {
        if (isKeycloakDefaultRole(code)) {
            throw new BusinessException("Keycloak 默认角色不可编辑");
        }
        if (isKeycloakInternalRole(code)) {
            throw new BusinessException("Keycloak 内置角色不可编辑");
        }
        Map<String, Object> role = keycloakAdmin.getRealmRoleByName(code);
        if (role == null) {
            throw new BusinessException("角色不存在");
        }
        String roleId = (String) role.get("id");
        keycloakAdmin.updateRealmRoleById(roleId, code, dto.getDescription());
        log.info("update role via Keycloak, code={}", code);
    }

    @Override
    public void deleteByCode(String code) {
        if (isKeycloakDefaultRole(code)) {
            throw new BusinessException("Keycloak 默认角色不可删除");
        }
        if (isKeycloakInternalRole(code)) {
            throw new BusinessException("Keycloak 内置角色不可删除");
        }
        if (CommonConstant.SUPER_ADMIN_ROLE_CODE.equalsIgnoreCase(code)) {
            throw new BusinessException("超级管理员角色禁止删除");
        }
        if (keycloakAdmin.getRealmRoleByName(code) == null) {
            throw new BusinessException("角色不存在");
        }
        List<Map<String, Object>> all = keycloakAdmin.listRealmRoles();
        if (all.size() <= 1) {
            throw new BusinessException("至少保留一个角色，禁止删除最后一个角色");
        }
        sysRoleMapper.deletePermissionsByRole(code);
        sysRoleMapper.deleteByCode(code);
        keycloakAdmin.deleteRealmRoleByName(code);
        log.info("delete role via Keycloak, code={}", code);
    }

    @Override
    public List<RolePermissionVO> getPermissions(String roleCode) {
        if (isKeycloakDefaultRole(roleCode)) {
            throw new BusinessException("Keycloak 默认角色不支持权限配置");
        }
        if (isKeycloakInternalRole(roleCode)) {
            throw new BusinessException("Keycloak 内置角色不支持权限配置");
        }
        if (keycloakAdmin.getRealmRoleByName(roleCode) == null) {
            throw new BusinessException("角色不存在");
        }
        List<RolePermission> list = sysRoleMapper.selectPermissionsByRole(roleCode);
        return list.stream()
                .map(rp -> {
                    RolePermissionVO vo = new RolePermissionVO();
                    vo.setMenuKey(rp.getMenuKey());
                    vo.setActions(rp.getActions() != null ? rp.getActions() : Collections.emptyList());
                    return vo;
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void savePermissions(String roleCode, RolePermissionUpdateDTO dto) {
        if (isKeycloakDefaultRole(roleCode)) {
            throw new BusinessException("Keycloak 默认角色不支持权限配置");
        }
        if (isKeycloakInternalRole(roleCode)) {
            throw new BusinessException("Keycloak 内置角色不支持权限配置");
        }
        Map<String, Object> kcRole = keycloakAdmin.getRealmRoleByName(roleCode);
        if (kcRole == null) {
            throw new BusinessException("角色不存在");
        }
        SysRole localRole = SysRole.builder()
                .code(roleCode)
                .name((String) kcRole.get("name"))
                .description((String) kcRole.get("description"))
                .build();
        sysRoleMapper.insertIfNotExists(localRole);
        sysRoleMapper.deletePermissionsByRole(roleCode);
        if (dto.getPermissions() != null && !dto.getPermissions().isEmpty()) {
            for (RolePermissionUpdateDTO.PermissionItem item : dto.getPermissions()) {
                if (item.getMenuKey() == null || item.getMenuKey().isBlank()) continue;
                RolePermission rp = RolePermission.builder()
                        .roleCode(roleCode)
                        .menuKey(item.getMenuKey())
                        .actions(item.getActions() != null ? item.getActions() : Collections.emptyList())
                        .build();
                sysRoleMapper.insertPermission(rp);
            }
        }
        log.info("save role permissions (local), roleCode={}", roleCode);
    }
}
