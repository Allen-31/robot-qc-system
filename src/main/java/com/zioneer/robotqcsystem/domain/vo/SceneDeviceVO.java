package com.zioneer.robotqcsystem.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 场景设备视图。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "场景设备视图")
public class SceneDeviceVO {

    @Schema(description = "主键")
    private Long id;

    @Schema(description = "设备编码")
    private String code;

    @Schema(description = "设备名称")
    private String name;

    @Schema(description = "设备类型")
    private String type;

    @Schema(description = "IP地址")
    private String ip;

    @Schema(description = "在线状态")
    private String onlineStatus;

    @Schema(description = "是否异常")
    private Boolean isAbnormal;

    @Schema(description = "异常详情")
    private String exceptionDetail;

    @Schema(description = "地图编码")
    private String mapCode;

    @Schema(description = "最近在线时间")
    private LocalDateTime lastOnlineAt;

    @Schema(description = "最近心跳时间")
    private LocalDateTime lastHeartbeatAt;

    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

    @Schema(description = "更新时间")
    private LocalDateTime updatedAt;
}
