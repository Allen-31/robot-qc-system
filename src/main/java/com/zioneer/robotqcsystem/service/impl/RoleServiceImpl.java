package com.zioneer.robotqcsystem.service.impl;

import com.zioneer.robotqcsystem.common.constant.CommonConstant;
import com.zioneer.robotqcsystem.common.exception.BusinessException;
import com.zioneer.robotqcsystem.domain.dto.RoleCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.RolePermissionUpdateDTO;
import com.zioneer.robotqcsystem.domain.dto.RoleUpdateDTO;
import com.zioneer.robotqcsystem.domain.entity.RolePermission;
import com.zioneer.robotqcsystem.domain.entity.SysRole;
import com.zioneer.robotqcsystem.domain.vo.RolePermissionVO;
import com.zioneer.robotqcsystem.domain.vo.RoleVO;
import com.zioneer.robotqcsystem.mapper.SysRoleMapper;
import com.zioneer.robotqcsystem.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色管理服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final SysRoleMapper sysRoleMapper;

    @Override
    public List<RoleVO> list(String keyword) {
        List<SysRole> list = sysRoleMapper.selectList(keyword);
        return list.stream().map(this::toVO).collect(Collectors.toList());
    }

    private RoleVO toVO(SysRole r) {
        RoleVO vo = new RoleVO();
        vo.setCode(r.getCode());
        vo.setName(r.getName());
        vo.setDescription(r.getDescription());
        vo.setMemberCount(r.getMemberCount());
        vo.setUpdatedAt(r.getUpdatedAt());
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(RoleCreateDTO dto) {
        if (sysRoleMapper.selectByCode(dto.getCode()) != null) {
            log.warn("create role failed, code already exists: code={}", dto.getCode());
            throw new BusinessException("角色编码已存在: " + dto.getCode());
        }
        SysRole role = SysRole.builder()
                .code(dto.getCode())
                .name(dto.getName())
                .description(dto.getDescription())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        sysRoleMapper.insert(role);
        log.info("create role, code={}", dto.getCode());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(String code, RoleUpdateDTO dto) {
        if (sysRoleMapper.selectByCode(code) == null) {
            log.warn("update role failed, role not found: code={}", code);
            throw new BusinessException("角色不存在");
        }
        SysRole role = SysRole.builder()
                .code(code)
                .name(dto.getName())
                .description(dto.getDescription())
                .updatedAt(LocalDateTime.now())
                .build();
        sysRoleMapper.updateByCode(role);
        log.info("update role, code={}", code);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByCode(String code) {
        if (CommonConstant.SUPER_ADMIN_ROLE_CODE.equalsIgnoreCase(code)) {
            log.warn("delete role failed, super admin role cannot be deleted: code={}", code);
            throw new BusinessException("超级管理员角色禁止删除");
        }
        if (sysRoleMapper.selectByCode(code) == null) {
            log.warn("delete role failed, role not found: code={}", code);
            throw new BusinessException("角色不存在");
        }
        int totalRoles = sysRoleMapper.selectList(null).size();
        if (totalRoles <= 1) {
            log.warn("delete role failed, cannot delete last role: code={}", code);
            throw new BusinessException("至少保留一个角色，禁止删除最后一个角色");
        }
        sysRoleMapper.deleteByCode(code);
        log.info("delete role, code={}", code);
    }

    @Override
    public List<RolePermissionVO> getPermissions(String roleCode) {
        if (sysRoleMapper.selectByCode(roleCode) == null) {
            log.warn("getPermissions failed, role not found: roleCode={}", roleCode);
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
        if (sysRoleMapper.selectByCode(roleCode) == null) {
            log.warn("savePermissions failed, role not found: roleCode={}", roleCode);
            throw new BusinessException("角色不存在");
        }
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
        log.info("save role permissions, roleCode={}", roleCode);
    }
}
