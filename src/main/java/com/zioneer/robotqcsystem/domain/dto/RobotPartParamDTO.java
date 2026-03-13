package com.zioneer.robotqcsystem.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 机器人零部件技术参数请求。
 */
@Data
@Schema(description = "机器人零部件技术参数请求")
public class RobotPartParamDTO {

    @NotBlank(message = "参数名称不能为空")
    @Schema(description = "参数名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "参数值")
    private String value;

    @Schema(description = "单位")
    private String unit;

    @Schema(description = "范围")
    private String range;

    @Schema(description = "排序")
    private Integer sortOrder;
}
