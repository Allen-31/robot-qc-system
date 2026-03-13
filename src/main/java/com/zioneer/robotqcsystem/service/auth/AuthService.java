package com.zioneer.robotqcsystem.service.auth;

import com.zioneer.robotqcsystem.domain.vo.LoginResponse;

/**
 * 认证服务：当前用户信息（登录由 Keycloak 负责）
 */
public interface AuthService {

    /**
     * 根据用户编码获取当前用户信息（用于 /auth/me）。若本地无用户则用 JWT 角色组装返回。
     */
    LoginResponse.UserInfoVO getCurrentUser(String userCode);
}
