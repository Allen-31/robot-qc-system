package com.zioneer.robotqcsystem.service.auth.impl;

import com.zioneer.robotqcsystem.domain.entity.SysUser;
import com.zioneer.robotqcsystem.domain.vo.LoginResponse;
import com.zioneer.robotqcsystem.mapper.SysUserMapper;
import com.zioneer.robotqcsystem.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 认证服务实现。登录由 Keycloak 负责；getCurrentUser 支持本地用户与仅 JWT 用户（角色来自 Keycloak）。
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final String ROLE_PREFIX = "ROLE_";

    private final SysUserMapper sysUserMapper;

    @Override
    public LoginResponse.UserInfoVO getCurrentUser(String userCode) {
        SysUser user = StringUtils.hasText(userCode) ? sysUserMapper.selectByCodeWithRoles(userCode) : null;
        if (user != null && "enabled".equals(user.getStatus())) {
            List<String> roles = user.getRoles() != null ? user.getRoles() : Collections.emptyList();
            roles.removeIf(r -> r == null || r.isBlank());
            return LoginResponse.UserInfoVO.builder()
                    .code(user.getCode())
                    .displayName(user.getName())
                    .roles(roles)
                    .build();
        }
        List<String> rolesFromJwt = getRolesFromCurrentAuthentication();
        return LoginResponse.UserInfoVO.builder()
                .code(userCode != null ? userCode : "unknown")
                .displayName(user != null ? user.getName() : userCode)
                .roles(rolesFromJwt)
                .build();
    }

    private List<String> getRolesFromCurrentAuthentication() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getAuthorities() == null) {
            return Collections.emptyList();
        }
        return auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(a -> a.startsWith(ROLE_PREFIX))
                .map(a -> a.substring(ROLE_PREFIX.length()))
                .collect(Collectors.toList());
    }
}
