package com.zioneer.robotqcsystem.service.impl;

import com.zioneer.robotqcsystem.domain.dto.LoginRequest;
import com.zioneer.robotqcsystem.domain.entity.SysUser;
import com.zioneer.robotqcsystem.domain.vo.LoginResponse;
import com.zioneer.robotqcsystem.mapper.SysUserMapper;
import com.zioneer.robotqcsystem.security.JwtUtil;
import com.zioneer.robotqcsystem.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * 认证服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final SysUserMapper sysUserMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public LoginResponse login(LoginRequest request) {
        String username = request != null ? request.getUsername() : null;
        log.debug("登录尝试 username=[{}], passwordLength=[{}]", username, request != null && request.getPassword() != null ? request.getPassword().length() : 0);
        SysUser user = StringUtils.hasText(username) ? sysUserMapper.selectByCodeWithRoles(username) : null;
        if (user == null) {
            log.warn("登录失败: 用户不存在 username=[{}]", username);
            throw new org.springframework.security.authentication.BadCredentialsException("用户名或密码错误");
        }
        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            log.warn("登录失败: 密码不匹配 username=[{}]", username);
            throw new org.springframework.security.authentication.BadCredentialsException("用户名或密码错误");
        }
        if (!"enabled".equals(user.getStatus())) {
            throw new org.springframework.security.authentication.DisabledException("账号已禁用");
        }
        sysUserMapper.updateLastLoginAt(user.getCode(), LocalDateTime.now());
        long ttl = Boolean.TRUE.equals(request.getRemember())
                ? jwtUtil.getRememberTokenSeconds()
                : jwtUtil.getAccessTokenSeconds();
        String token = jwtUtil.generateToken(user.getCode(), ttl);
        List<String> roles = user.getRoles() != null ? user.getRoles() : Collections.emptyList();
        roles.removeIf(r -> r == null || r.isBlank());
        LoginResponse.UserInfoVO userInfo = LoginResponse.UserInfoVO.builder()
                .code(user.getCode())
                .displayName(user.getName())
                .roles(roles)
                .build();
        return LoginResponse.builder()
                .token(token)
                .user(userInfo)
                .build();
    }

    @Override
    public void logout(String token) {
        if (StringUtils.hasText(token)) {
            // 可选：将 token 加入 Redis 黑名单，此处仅服务端无状态，客户端丢弃 token 即可
        }
    }

    @Override
    public LoginResponse.UserInfoVO getCurrentUser(String userCode) {
        SysUser user = sysUserMapper.selectByCodeWithRoles(userCode);
        if (user == null) {
            throw new org.springframework.security.authentication.BadCredentialsException("用户不存在");
        }
        List<String> roles = user.getRoles() != null ? user.getRoles() : Collections.emptyList();
        roles.removeIf(r -> r == null || r.isBlank());
        return LoginResponse.UserInfoVO.builder()
                .code(user.getCode())
                .displayName(user.getName())
                .roles(roles)
                .build();
    }
}
