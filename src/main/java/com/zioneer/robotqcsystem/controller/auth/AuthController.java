package com.zioneer.robotqcsystem.controller.auth;

import com.zioneer.robotqcsystem.common.result.Result;
import com.zioneer.robotqcsystem.domain.dto.LoginRequest;
import com.zioneer.robotqcsystem.domain.vo.LoginResponse;
import com.zioneer.robotqcsystem.service.auth.AuthService;
import com.zioneer.robotqcsystem.service.auth.KeycloakLoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 认证（auth 模块）。BFF 登录：前端使用自有登录页，提交用户名密码到本接口，后端代向 Keycloak 换 token。
 */
@Tag(name = "认证", description = "登录、登出、获取当前用户")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final KeycloakLoginService keycloakLoginService;

    @Operation(summary = "登录（BFF：后端代向 Keycloak 换 token，前端使用自有登录页）")
    @PostMapping("/login")
    public Result<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        LoginResponse data = keycloakLoginService.login(request);
        return Result.ok(data);
    }

    @Operation(summary = "登出")
    @PostMapping("/logout")
    public Result<LogoutResult> logout() {
        return Result.ok(LogoutResult.builder().success(true).build());
    }

    @Operation(summary = "获取当前用户信息")
    @GetMapping("/me")
    public Result<LoginResponse.UserInfoVO> me(Authentication authentication) {
        String userCode = authentication != null ? authentication.getName() : null;
        if (!StringUtils.hasText(userCode)) {
            return Result.fail(401, "未登录");
        }
        LoginResponse.UserInfoVO user = authService.getCurrentUser(userCode);
        return Result.ok(user);
    }

    @lombok.Data
    @lombok.Builder
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    public static class LogoutResult {
        private Boolean success;
    }
}
