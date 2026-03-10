package com.zioneer.robotqcsystem.service;

import com.zioneer.robotqcsystem.domain.dto.LoginRequest;
import com.zioneer.robotqcsystem.domain.entity.SysUser;
import com.zioneer.robotqcsystem.domain.vo.LoginResponse;

/**
 * 认证服务：登录、登出、当前用户
 */
public interface AuthService {

    /**
     * 登录，校验用户名密码并返回 token 与用户信息
     */
    LoginResponse login(LoginRequest request);

    /**
     * 登出（可选：将 token 加入黑名单）
     */
    void logout(String token);

    /**
     * 根据用户编码获取当前用户信息（用于 /auth/me）
     */
    LoginResponse.UserInfoVO getCurrentUser(String userCode);
}
