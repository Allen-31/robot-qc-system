package com.zioneer.robotqcsystem.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 登录成功响应：token + 用户简要信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "登录成功响应")
public class LoginResponse {

    @Schema(description = "JWT 访问令牌")
    private String token;

    @Schema(description = "当前用户信息")
    private UserInfoVO user;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "用户简要信息")
    public static class UserInfoVO {
        @Schema(description = "用户编码")
        private String code;
        @Schema(description = "显示名称")
        private String displayName;
        @Schema(description = "角色编码列表")
        private List<String> roles;
    }
}
