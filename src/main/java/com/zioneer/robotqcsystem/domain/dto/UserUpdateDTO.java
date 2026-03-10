package com.zioneer.robotqcsystem.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

/**
 * 更新用户请求（不含密码）
 */
@Data
@Schema(description = "更新用户请求")
public class UserUpdateDTO {

    @NotBlank(message = "姓名不能为空")
    @Schema(description = "姓名", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "手机号")
    private String phone;
    @Schema(description = "邮箱")
    private String email;
    @Schema(description = "状态：enabled/disabled")
    private String status;
    @Schema(description = "角色编码列表")
    private List<String> roles;
}
