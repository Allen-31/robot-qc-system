package com.zioneer.robotqcsystem.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

/**
 * 发布任务更新请求。
 */
@Data
@Schema(description = "发布任务更新请求")
public class OperationPublishUpdateDTO {

    @Size(max = 255, message = "任务名称长度不能超过255")
    @Schema(description = "任务名称")
    private String name;

    @Size(max = 255, message = "安装包名称长度不能超过255")
    @Schema(description = "安装包名称")
    private String packageName;

    @Size(max = 500, message = "目标机器人数量不能超过500")
    @Schema(description = "目标机器人列表")
    private List<String> targetRobots;

    @Size(max = 200, message = "目标机器人组数量不能超过200")
    @Schema(description = "目标机器人组列表")
    private List<String> targetRobotGroups;

    @Size(max = 200, message = "目标机器人类型数量不能超过200")
    @Schema(description = "目标机器人类型列表")
    private List<String> targetRobotTypes;

    @Size(max = 32, message = "升级策略长度不能超过32")
    @Schema(description = "升级策略", example = "idle")
    private String strategy;

    @Schema(description = "升级后是否重启")
    private Boolean restartAfterUpgrade;
}
