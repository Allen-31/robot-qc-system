package com.zioneer.robotqcsystem.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * 发布任务创建请求。
 */
@Data
@Schema(description = "发布任务创建请求")
public class OperationPublishCreateDTO {

    @NotBlank(message = "任务名称不能为空")
    @Schema(description = "任务名称")
    private String name;

    @NotBlank(message = "安装包不能为空")
    @Schema(description = "安装包名称")
    private String packageName;

    @Schema(description = "目标机器人列表")
    private List<String> targetRobots;

    @Schema(description = "目标机器人组列表")
    private List<String> targetRobotGroups;

    @Schema(description = "目标机器人类型列表")
    private List<String> targetRobotTypes;

    @NotBlank(message = "升级策略不能为空")
    @Schema(description = "升级策略", example = "idle")
    private String strategy;

    @NotNull(message = "是否重启不能为空")
    @Schema(description = "升级后是否重启")
    private Boolean restartAfterUpgrade;
}
