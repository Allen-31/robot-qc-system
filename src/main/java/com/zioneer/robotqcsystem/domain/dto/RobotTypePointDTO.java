package com.zioneer.robotqcsystem.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 机器人类型标注点请求。
 */
@Data
@Schema(description = "机器人类型标注点请求")
public class RobotTypePointDTO {

    @NotBlank(message = "零部件名称不能为空")
    @Schema(description = "零部件名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String partName;

    @Schema(description = "零部件位置")
    private String partPosition;

    @NotNull(message = "坐标X不能为空")
    @Schema(description = "坐标X（百分比）", requiredMode = Schema.RequiredMode.REQUIRED)
    private Double x;

    @NotNull(message = "坐标Y不能为空")
    @Schema(description = "坐标Y（百分比）", requiredMode = Schema.RequiredMode.REQUIRED)
    private Double y;

    @NotNull(message = "旋转角度不能为空")
    @Schema(description = "旋转角度", requiredMode = Schema.RequiredMode.REQUIRED)
    private Double rotation;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "排序")
    private Integer sortOrder;
}
