package com.zioneer.robotqcsystem.controller.auth;

import com.zioneer.robotqcsystem.common.result.Result;
import com.zioneer.robotqcsystem.domain.dto.LoginRequest;
import com.zioneer.robotqcsystem.domain.vo.LoginResponse;
import com.zioneer.robotqcsystem.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 登录与认证（auth 模块）
 */
@Tag(name = "登录与认证", description = "登录、登出、获取当前用户")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public Result<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        LoginResponse data = authService.login(request);
        return Result.ok(data);
    }

    @Operation(summary = "登出")
    @PostMapping("/logout")
    public Result<LogoutResult> logout(HttpServletRequest request, Authentication authentication) {
        String token = resolveToken(request);
        authService.logout(token);
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

    private String resolveToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring(7).trim();
        }
        return null;
    }

    @lombok.Data
    @lombok.Builder
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    public static class LogoutResult {
        private Boolean success;
    }
}
