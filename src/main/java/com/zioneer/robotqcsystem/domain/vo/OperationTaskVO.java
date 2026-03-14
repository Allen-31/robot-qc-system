package com.zioneer.robotqcsystem.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 任务流信息。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "任务流信息")
public class OperationTaskVO {

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "任务ID")
    private Long id;

    @Schema(description = "任务编码")
    private String code;

    @Schema(description = "外部单号")
    private String externalCode;

    @Schema(description = "状态", example = "running")
    private String status;

    @Schema(description = "机器人编码")
    private String robot;

    @Schema(description = "优先级")
    private Integer priority;

    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

    @Schema(description = "结束时间")
    private LocalDateTime endedAt;

    @Schema(description = "描述")
    private String description;
}