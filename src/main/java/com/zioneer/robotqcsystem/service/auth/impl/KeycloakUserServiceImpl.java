package com.zioneer.robotqcsystem.service.auth.impl;

import com.zioneer.robotqcsystem.common.constant.CommonConstant;
import com.zioneer.robotqcsystem.common.exception.BusinessException;
import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.common.result.ResultCode;
import com.zioneer.robotqcsystem.domain.dto.AdminResetPasswordDTO;
import com.zioneer.robotqcsystem.domain.dto.PasswordUpdateDTO;
import com.zioneer.robotqcsystem.domain.dto.UserCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.UserQuery;
import com.zioneer.robotqcsystem.domain.dto.UserRolesUpdateDTO;
import com.zioneer.robotqcsystem.domain.dto.UserUpdateDTO;
import com.zioneer.robotqcsystem.domain.vo.PasswordUpdateResultVO;
import com.zioneer.robotqcsystem.domain.vo.UserListVO;
import com.zioneer.robotqcsystem.integration.keycloak.KeycloakAdminClient;
import com.zioneer.robotqcsystem.service.auth.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户管理服务实现（Keycloak）：用户增删改查、角色分配、修改密码均走 Keycloak Admin API。
 */
@Slf4j
@Primary
@Service
@RequiredArgsConstructor
public class KeycloakUserServiceImpl implements UserService {

    private final KeycloakAdminClient keycloakAdmin;
    private final RestTemplate restTemplate;

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuerUri;

    @Value("${app.keycloak.client-id:robot-qc-frontend}")
    private String clientId;

    private static final String KEYCLOAK_DEFAULT_ROLE_PREFIX = "default-roles-";
    private static final Set<String> KEYCLOAK_INTERNAL_ROLES = Set.of("uma_authorization", "offline_access");

    private static boolean isBusinessRole(String roleName) {
        if (roleName == null || roleName.isBlank()) return false;
        if (roleName.startsWith(KEYCLOAK_DEFAULT_ROLE_PREFIX)) return false;
        if (KEYCLOAK_INTERNAL_ROLES.contains(roleName)) return false;
        return true;
    }

    @Override
    public PageResult<UserListVO> page(UserQuery query) {
        String search = StringUtils.hasText(query.getKeyword()) ? query.getKeyword() : null;
        Boolean enabled = null;
        if ("enabled".equalsIgnoreCase(query.getStatus())) enabled = true;
        if ("disabled".equalsIgnoreCase(query.getStatus())) enabled = false;

        String roleFilter = StringUtils.hasText(query.getRole()) ? query.getRole() : null;
        List<UserListVO> allFiltered;
        if (roleFilter != null) {
            int maxFetch = 500;
            List<Map<String, Object>> kcUsers = keycloakAdmin.listUsers(0, maxFetch, search, enabled);
            allFiltered = new ArrayList<>();
            for (Map<String, Object> u : kcUsers) {
                UserListVO vo = toUserListVO(u);
                if (vo != null && vo.getRoles() != null && vo.getRoles().contains(roleFilter)) {
                    allFiltered.add(vo);
                }
            }
        } else {
            int total = keycloakAdmin.countUsers(search, enabled);
            if (total == 0) {
                return PageResult.empty(query);
            }
            int first = query.getOffset();
            int max = query.getPageSize();
            List<Map<String, Object>> kcUsers = keycloakAdmin.listUsers(first, max, search, enabled);
            allFiltered = new ArrayList<>();
            for (Map<String, Object> u : kcUsers) {
                UserListVO vo = toUserListVO(u);
                if (vo != null) allFiltered.add(vo);
            }
            return PageResult.of(allFiltered, (long) total, query.getPageNum(), query.getPageSize());
        }

        int total = allFiltered.size();
        if (total == 0) {
            return PageResult.empty(query);
        }
        int from = query.getOffset();
        int to = Math.min(from + query.getPageSize(), total);
        List<UserListVO> pageList = from < total ? allFiltered.subList(from, to) : Collections.emptyList();
        return PageResult.of(pageList, (long) total, query.getPageNum(), query.getPageSize());
    }

    private UserListVO toUserListVO(Map<String, Object> u) {
        String id = (String) u.get("id");
        if (id == null) return null;
        String username = (String) u.get("username");
        String firstName = (String) u.get("firstName");
        String lastName = (String) u.get("lastName");
        String name = StringUtils.hasText(firstName) || StringUtils.hasText(lastName)
                ? (firstName != null ? firstName : "").trim() + " " + (lastName != null ? lastName : "").trim()
                : username;
        if (name != null) name = name.trim();
        if (!StringUtils.hasText(name)) name = username;
        List<Map<String, Object>> roleMappings = keycloakAdmin.getUserRealmRoleMappings(id);
        List<String> roles = roleMappings.stream()
                .map(r -> (String) r.get("name"))
                .filter(StringUtils::hasText)
                .filter(KeycloakUserServiceImpl::isBusinessRole)
                .collect(Collectors.toList());

        UserListVO vo = new UserListVO();
        vo.setCode(username);
        vo.setName(name);
        vo.setEmail((String) u.get("email"));
        vo.setPhone(null);
        if (u.get("attributes") instanceof Map && ((Map<?, ?>) u.get("attributes")).containsKey("phone")) {
            Object ph = ((Map<?, ?>) u.get("attributes")).get("phone");
            if (ph instanceof List && !((List<?>) ph).isEmpty()) {
                vo.setPhone(String.valueOf(((List<?>) ph).get(0)));
            }
        }
        vo.setStatus(Boolean.TRUE.equals(u.get("enabled")) ? "enabled" : "disabled");
        vo.setLastLoginAt(null);
        vo.setRoles(roles);
        return vo;
    }

    @Override
    public void create(UserCreateDTO dto) {
        if (!StringUtils.hasText(dto.getPassword()) || dto.getPassword().length() < 8) {
            throw new BusinessException("密码至少需要 8 位（符合 Keycloak 策略）");
        }
        Map<String, Object> kc = keycloakAdmin.getUserByUsername(dto.getCode());
        if (kc != null) {
            throw new BusinessException("用户编码已存在: " + dto.getCode());
        }
        Map<String, Object> userRep = new java.util.HashMap<>();
        userRep.put("username", dto.getCode());
        userRep.put("firstName", StringUtils.hasText(dto.getName()) ? dto.getName() : dto.getCode());
        userRep.put("lastName", "User");
        userRep.put("email", StringUtils.hasText(dto.getEmail()) ? dto.getEmail() : dto.getCode() + "@placeholder.local");
        userRep.put("emailVerified", true);
        userRep.put("enabled", !"disabled".equalsIgnoreCase(dto.getStatus()));
        if (StringUtils.hasText(dto.getPhone())) {
            userRep.put("attributes", Map.of("phone", List.of(dto.getPhone())));
        }
        String userId = keycloakAdmin.createUser(userRep);
        if (userId == null) {
            throw new BusinessException("创建用户失败");
        }
        try {
            keycloakAdmin.setUserPassword(userId, dto.getPassword(), false);
        } catch (HttpClientErrorException e) {
            keycloakAdmin.deleteUser(userId);
            if (e.getStatusCode().value() == 400) {
                throw new BusinessException("密码不符合 Keycloak 策略，请使用至少 8 位密码");
            }
            log.warn("Keycloak 设置密码失败 code={} status={}", dto.getCode(), e.getStatusCode());
            throw new BusinessException("设置密码失败，请稍后重试");
        }
        keycloakAdmin.clearUserRequiredActions(userId);
        if (dto.getRoles() != null && !dto.getRoles().isEmpty()) {
            List<Map<String, Object>> rolesToAdd = new ArrayList<>();
            for (String roleName : dto.getRoles()) {
                Map<String, Object> role = keycloakAdmin.getRealmRoleByName(roleName);
                if (role != null) rolesToAdd.add(role);
            }
            keycloakAdmin.addRealmRolesToUser(userId, rolesToAdd);
        }
        log.info("create user via Keycloak, code={}", dto.getCode());
    }

    @Override
    public void update(String code, UserUpdateDTO dto) {
        Map<String, Object> kc = keycloakAdmin.getUserByUsername(code);
        if (kc == null) {
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "用户不存在");
        }
        String userId = (String) kc.get("id");
        Map<String, Object> userRep = new java.util.HashMap<>(kc);
        userRep.put("firstName", dto.getName());
        userRep.put("lastName", "");
        userRep.put("email", dto.getEmail());
        userRep.put("enabled", !"disabled".equalsIgnoreCase(dto.getStatus()));
        if (StringUtils.hasText(dto.getPhone())) {
            userRep.put("attributes", Map.of("phone", List.of(dto.getPhone())));
        }
        keycloakAdmin.updateUser(userId, userRep);

        List<Map<String, Object>> currentRoles = keycloakAdmin.getUserRealmRoleMappings(userId);
        keycloakAdmin.removeRealmRolesFromUser(userId, currentRoles);
        if (dto.getRoles() != null && !dto.getRoles().isEmpty()) {
            List<Map<String, Object>> toAdd = new ArrayList<>();
            for (String roleName : dto.getRoles()) {
                Map<String, Object> role = keycloakAdmin.getRealmRoleByName(roleName);
                if (role != null) toAdd.add(role);
            }
            keycloakAdmin.addRealmRolesToUser(userId, toAdd);
        }
        log.info("update user via Keycloak, code={}", code);
    }

    @Override
    public void deleteByCode(String code) {
        if (CommonConstant.SUPER_ADMIN_USER_CODE.equalsIgnoreCase(code)) {
            throw new BusinessException(ResultCode.BUSINESS_ERROR.getCode(), "超级管理员账号禁止删除");
        }
        Map<String, Object> kc = keycloakAdmin.getUserByUsername(code);
        if (kc == null) {
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "用户不存在");
        }
        keycloakAdmin.deleteUser((String) kc.get("id"));
        log.info("delete user via Keycloak, code={}", code);
    }

    @Override
    public void updateRoles(String code, UserRolesUpdateDTO dto) {
        Map<String, Object> kc = keycloakAdmin.getUserByUsername(code);
        if (kc == null) {
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "用户不存在");
        }
        String userId = (String) kc.get("id");
        List<Map<String, Object>> current = keycloakAdmin.getUserRealmRoleMappings(userId);
        List<Map<String, Object>> toPreserve = current.stream()
                .filter(r -> !isBusinessRole((String) r.get("name")))
                .collect(Collectors.toList());
        keycloakAdmin.removeRealmRolesFromUser(userId, current);
        List<Map<String, Object>> toAdd = new ArrayList<>(toPreserve);
        if (dto.getRoles() != null && !dto.getRoles().isEmpty()) {
            for (String roleName : dto.getRoles()) {
                Map<String, Object> role = keycloakAdmin.getRealmRoleByName(roleName);
                if (role != null) toAdd.add(role);
            }
        }
        if (!toAdd.isEmpty()) {
            keycloakAdmin.addRealmRolesToUser(userId, toAdd);
        }
        keycloakAdmin.clearUserRequiredActions(userId);
        log.info("update user roles via Keycloak, code={}", code);
    }

    @Override
    public PasswordUpdateResultVO updatePassword(String code, PasswordUpdateDTO dto) {
        Map<String, Object> kc = keycloakAdmin.getUserByUsername(code);
        if (kc == null) {
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "用户不存在");
        }
        if (!verifyPassword(code, dto.getOldPassword())) {
            return PasswordUpdateResultVO.builder().success(false).error("old_password_invalid").build();
        }
        keycloakAdmin.setUserPassword((String) kc.get("id"), dto.getNewPassword(), false);
        return PasswordUpdateResultVO.builder().success(true).build();
    }

    @Override
    public void resetPasswordByAdmin(String code, AdminResetPasswordDTO dto) {
        Map<String, Object> kc = keycloakAdmin.getUserByUsername(code);
        if (kc == null) {
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "用户不存在");
        }
        try {
            keycloakAdmin.setUserPassword((String) kc.get("id"), dto.getNewPassword(), false);
            keycloakAdmin.clearUserRequiredActions((String) kc.get("id"));
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 400) {
                throw new BusinessException("密码不符合 Keycloak 策略，请使用至少 8 位密码");
            }
            throw new BusinessException("重置密码失败");
        }
        log.info("admin reset password for user, code={}", code);
    }

    private boolean verifyPassword(String username, String password) {
        String tokenUrl = issuerUri.replaceFirst("/$", "") + "/protocol/openid-connect/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "password");
        body.add("client_id", clientId);
        body.add("username", username);
        body.add("password", password);
        try {
            ResponseEntity<Map> r = restTemplate.postForEntity(
                    tokenUrl, new HttpEntity<>(body, headers), Map.class);
            return r.getStatusCode().is2xxSuccessful() && r.getBody() != null && r.getBody().containsKey("access_token");
        } catch (Exception e) {
            return false;
        }
    }
}
