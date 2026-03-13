package com.zioneer.robotqcsystem.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 机器人类型标注点视图。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "机器人类型标注点视图")
public class RobotTypePointVO {

    @Schema(description = "零部件名称")
    private String partName;

    @Schema(description = "零部件位置")
    private String partPosition;

    @Schema(description = "坐标X（百分比）")
    private Double x;

    @Schema(description = "坐标Y（百分比）")
    private Double y;

    @Schema(description = "旋转角度")
    private Double rotation;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "排序")
    private Integer sortOrder;
}
