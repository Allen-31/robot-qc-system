package com.zioneer.robotqcsystem.controller.auth;

import com.zioneer.robotqcsystem.common.result.Result;
import com.zioneer.robotqcsystem.mapper.SysUserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * 开发辅助（auth 模块，仅开发环境启用）
 */
@Tag(name = "开发辅助", description = "仅开发环境启用")
@RestController
@RequestMapping("/api/auth/dev")
@RequiredArgsConstructor
@ConditionalOnProperty(name = "app.dev.reset-admin-enabled", havingValue = "true")
public class DevController {

    private final SysUserMapper sysUserMapper;
    private final PasswordEncoder passwordEncoder;

    private static final String ADMIN_CODE = "admin";
    private static final String DEFAULT_PASSWORD = "admin123";

    @Operation(summary = "将 admin 密码重置为 admin123")
    @PostMapping("/reset-admin-password")
    public Result<String> resetAdminPassword() {
        if (sysUserMapper.selectByCode(ADMIN_CODE) == null) {
            return Result.fail("用户 admin 不存在");
        }
        String hash = passwordEncoder.encode(DEFAULT_PASSWORD);
        sysUserMapper.updatePasswordHash(ADMIN_CODE, hash, LocalDateTime.now());
        return Result.ok("已将 admin 密码重置为 " + DEFAULT_PASSWORD + "，请用该账号登录");
    }
}
