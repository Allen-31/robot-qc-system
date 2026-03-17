package com.zioneer.robotqcsystem.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 任务模板更新参数。
 */
@Data
@Schema(description = "任务模板更新参数")
public class DeployTaskTemplateUpdateDTO {

    @Schema(description = "模板名称")
    private String name;

    @Schema(description = "是否启用")
    private Boolean enabled;
}