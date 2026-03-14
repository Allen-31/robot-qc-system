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
 * 发布任务设备进度视图对象。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "发布任务设备进度")
public class OperationPublishDeviceVO {

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "设备进度ID")
    private Long id;

    @Schema(description = "设备名称")
    private String deviceName;

    @Schema(description = "IP")
    private String ip;

    @Schema(description = "状态", example = "upgrading")
    private String status;

    @Schema(description = "安装包名称")
    private String packageName;

    @Schema(description = "版本号", example = "v5.1.0")
    private String version;

    @Schema(description = "更新时间")
    private LocalDateTime updatedAt;

    @Schema(description = "完成时间")
    private LocalDateTime completedAt;

    @Schema(description = "进度(0-100)")
    private Integer progress;
}
