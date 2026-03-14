package com.zioneer.robotqcsystem.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 发布任务视图对象。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "发布任务信息")
public class OperationPublishVO {

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "发布任务ID")
    private Long id;

    @Schema(description = "任务名称")
    private String name;

    @Schema(description = "安装包名称")
    private String packageName;

    @Schema(description = "目标机器人列表")
    private List<String> targetRobots;

    @Schema(description = "目标机器人组列表")
    private List<String> targetRobotGroups;

    @Schema(description = "目标机器人类型列表")
    private List<String> targetRobotTypes;

    @Schema(description = "升级策略", example = "idle")
    private String strategy;

    @Schema(description = "升级后是否重启")
    private Boolean restartAfterUpgrade;

    @Schema(description = "状态", example = "running")
    private String status;

    @Schema(description = "创建人")
    private String creator;

    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

    @Schema(description = "完成时间")
    private LocalDateTime completedAt;

    @Schema(description = "设备进度列表")
    private List<OperationPublishDeviceVO> devices;
}
