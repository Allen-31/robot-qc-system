package com.zioneer.robotqcsystem.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 新增角色请求
 */
@Data
@Schema(description = "新增角色请求")
public class RoleCreateDTO {

    @NotBlank(message = "角色编码不能为空")
    @Schema(description = "角色编码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String code;

    @NotBlank(message = "角色名称不能为空")
    @Schema(description = "角色名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "描述")
    private String description;
}
