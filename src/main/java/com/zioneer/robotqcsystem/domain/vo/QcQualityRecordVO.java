package com.zioneer.robotqcsystem.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 质检记录列表项（2.1.4：与工单结构一致，含 dispatchCode）
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "质检记录")
public class QcQualityRecordVO {

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "工单ID（Snowflake），JSON 序列化为字符串避免前端精度丢失")
    private Long id;
    @Schema(description = "工单号")
    private String workOrderNo;
    @Schema(description = "线束编码")
    private String harnessCode;
    @Schema(description = "线束类型")
    private String harnessType;
    @Schema(description = "质检台编码")
    private String stationCode;
    @Schema(description = "状态")
    private String status;
    @Schema(description = "质检结果")
    private String qualityResult;
    @Schema(description = "调度编码")
    private String dispatchCode;
    @Schema(description = "移动时长")
    private BigDecimal movingDuration;
    @Schema(description = "检测时长")
    private BigDecimal detectionDuration;
    @Schema(description = "创建时间")
    private String createdAt;
    @Schema(description = "开始时间")
    private String startedAt;
    @Schema(description = "结束时间")
    private String endedAt;
}
