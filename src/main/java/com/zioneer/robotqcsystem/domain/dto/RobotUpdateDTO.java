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

    @Schema(description = "型号")
    private String model;

    @NotBlank(message = "状态不能为空")
    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED)
    private String status;

    @Schema(description = "位置")
    private String location;

    @Schema(description = "最近巡检时间")
    private LocalDateTime lastInspectionAt;
}
