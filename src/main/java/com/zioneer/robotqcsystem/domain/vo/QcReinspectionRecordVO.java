package com.zioneer.robotqcsystem.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 复检记录视图（2.1.5）
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "复检记录")
public class QcReinspectionRecordVO {

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "记录ID（Snowflake），JSON 序列化为字符串避免前端精度丢失")
    private Long id;
    @Schema(description = "复检单号")
    private String reinspectionNo;
    @Schema(description = "关联工单号")
    private String workOrderNo;
    @Schema(description = "线束编码")
    private String harnessCode;
    @Schema(description = "线束类型")
    private String harnessType;
    @Schema(description = "工位编码")
    private String stationCode;
    @Schema(description = "原质检结果")
    private String qualityResult;
    @Schema(description = "状态 pending/completed/cancelled")
    private String status;
    @Schema(description = "复检结果")
    private String reinspectionResult;
    @Schema(description = "缺陷类型")
    private String defectType;
    @Schema(description = "复检时间")
    private String reinspectionTime;
    @Schema(description = "复检人")
    private String reviewer;
    @Schema(description = "视频地址")
    private String videoUrl;
    @Schema(description = "图片地址")
    private String imageUrl;
}
