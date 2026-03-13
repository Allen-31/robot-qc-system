package com.zioneer.robotqcsystem.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 管理员重置用户密码请求（无需原密码）
 */
@Data
@Schema(description = "管理员重置密码请求")
public class AdminResetPasswordDTO {

    @NotBlank(message = "新密码不能为空")
    @Size(min = 8, message = "密码至少需要 8 位")
    @Schema(description = "新密码", requiredMode = Schema.RequiredMode.REQUIRED, minLength = 8)
    private String newPassword;
}
