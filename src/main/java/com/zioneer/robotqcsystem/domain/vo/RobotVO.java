package com.zioneer.robotqcsystem.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 机器人视图对象（详情/列表）
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "机器人信息")
public class RobotVO {

    @Schema(description = "主键")
    private Long id;

    @Schema(description = "机器人编码")
    private String robotCode;

    @Schema(description = "机器人名称")
    private String robotName;

    @Schema(description = "型号")
    private String model;

    @Schema(description = "状态")
    private String status;

    @Schema(description = "位置")
    private String location;

    @Schema(description = "最近巡检时间")
    private LocalDateTime lastInspectionAt;
}
