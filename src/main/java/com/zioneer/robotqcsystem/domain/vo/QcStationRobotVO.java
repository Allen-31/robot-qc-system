package com.zioneer.robotqcsystem.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 当前质检台关联机器人（2.1.2.2）
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "质检台机器人信息")
public class QcStationRobotVO {

    @Schema(description = "机器人编号")
    private String robotCode;
    @Schema(description = "状态 idle/running/fault")
    private String status;
    @Schema(description = "状态文案")
    private String statusText;
    @Schema(description = "电量百分比")
    private Integer battery;
    @Schema(description = "异常信息")
    private String exceptionInfo;
}
