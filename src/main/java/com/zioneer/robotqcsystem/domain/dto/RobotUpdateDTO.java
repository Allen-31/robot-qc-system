package com.zioneer.robotqcsystem.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 更新机器人请求
 */
@Data
@Schema(description = "更新机器人请求")
public class RobotUpdateDTO {

    @NotBlank(message = "机器人名称不能为空")
    @Schema(description = "机器人名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String robotName;

    @Schema(description = "序列号")
    private String serialNo;

    @Schema(description = "IP地址")
    private String ip;

    @Schema(description = "型号")
    private String model;

    @Schema(description = "固件版本")
    private String firmwareVersion;

    @Schema(description = "机器人类型编号")
    private String robotTypeNo;

    @Schema(description = "机器人类型名称")
    private String robotTypeName;

    @Schema(description = "分组编号")
    private String groupNo;

    @Schema(description = "分组名称")
    private String groupName;

    @NotBlank(message = "状态不能为空")
    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED)
    private String status;

    @Schema(description = "在线状态")
    private String onlineStatus;

    @Schema(description = "电量（%）")
    private Integer battery;

    @Schema(description = "里程（km）")
    private Double mileageKm;

    @Schema(description = "当前地图编码")
    private String currentMapCode;

    @Schema(description = "当前地图名称")
    private String currentMapName;

    @Schema(description = "调度模式")
    private String dispatchMode;

    @Schema(description = "控制状态")
    private String controlStatus;

    @Schema(description = "异常状态")
    private String exceptionStatus;

    @Schema(description = "视频地址")
    private String videoUrl;

    @Schema(description = "位置")
    private String location;

    @Schema(description = "最近巡检时间")
    private LocalDateTime lastInspectionAt;

    @Schema(description = "注册时间")
    private LocalDateTime registeredAt;

    @Schema(description = "最近在线时间")
    private LocalDateTime lastOnlineAt;

    @Schema(description = "最近心跳时间")
    private LocalDateTime lastHeartbeatAt;
}
