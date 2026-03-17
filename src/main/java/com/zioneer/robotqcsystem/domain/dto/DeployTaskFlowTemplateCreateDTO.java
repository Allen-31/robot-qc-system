package com.zioneer.robotqcsystem.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

/**
 * 任务流模板创建参数。
 */
@Data
@Schema(description = "任务流模板创建参数")
public class DeployTaskFlowTemplateCreateDTO {

    @NotBlank(message = "模板编码不能为空")
    @Schema(description = "模板编码", example = "TASK-T01")
    private String code;

    @NotBlank(message = "模板名称不能为空")
    @Schema(description = "模板名称", example = "巡检任务流模板")
    private String name;

    @NotNull(message = "优先级不能为空")
    @Positive(message = "优先级需大于0")
    @Schema(description = "优先级", example = "1")
    private Integer priority;

    @Schema(description = "是否启用", example = "true")
    private Boolean enabled;
}