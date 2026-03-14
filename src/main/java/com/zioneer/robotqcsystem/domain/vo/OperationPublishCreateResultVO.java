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
 * 发布任务创建结果。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "发布任务创建结果")
public class OperationPublishCreateResultVO {

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "发布任务ID")
    private Long id;

    @Schema(description = "任务名称")
    private String name;

    @Schema(description = "状态", example = "pending")
    private String status;

    @Schema(description = "创建时间")
    private LocalDateTime createdAt;
}
