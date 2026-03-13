package com.zioneer.robotqcsystem.service.auth;

import com.zioneer.robotqcsystem.domain.dto.LoginRequest;
import com.zioneer.robotqcsystem.domain.vo.LoginResponse;

/**
 * BFF 登录：后端代前端向 Keycloak 用用户名密码换 token，供自有登录页使用。
 */
public interface KeycloakLoginService {

    /**
     * 使用用户名密码向 Keycloak 换取 access_token，并解析出用户信息返回。
     *
     * @param request 用户名、密码
     * @return token + 用户信息
     * @throws org.springframework.security.authentication.BadCredentialsException 用户名或密码错误
     */
    LoginResponse login(LoginRequest request);
}
