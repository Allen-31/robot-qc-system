package com.zioneer.robotqcsystem.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 任务模板创建参数。
 */
@Data
@Schema(description = "任务模板创建参数")
public class DeployTaskTemplateCreateDTO {

    @NotBlank(message = "模板编码不能为空")
    @Schema(description = "模板编码", example = "TASK-A01")
    private String code;

    @NotBlank(message = "模板名称不能为空")
    @Schema(description = "模板名称", example = "巡检任务模板")
    private String name;

    @Schema(description = "是否启用", example = "true")
    private Boolean enabled;
}