package com.zioneer.robotqcsystem.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 发布任务设备取消结果。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "发布任务设备取消结果")
public class OperationPublishDeviceCancelResultVO {

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "设备进度ID")
    private Long deviceId;

    @Schema(description = "状态", example = "cancelled")
    private String status;
}
