package com.zioneer.robotqcsystem.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import lombok.Data;

/**
 * 任务流模板更新参数。
 */
@Data
@Schema(description = "任务流模板更新参数")
public class DeployTaskFlowTemplateUpdateDTO {

    @Schema(description = "模板名称")
    private String name;

    @Positive(message = "优先级需大于0")
    @Schema(description = "优先级")
    private Integer priority;

    @Schema(description = "是否启用")
    private Boolean enabled;
}