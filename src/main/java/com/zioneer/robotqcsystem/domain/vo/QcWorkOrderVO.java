package com.zioneer.robotqcsystem.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * 工单视图（2.1.3 列表/详情，含 videoUrl/imageUrl）
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "工单信息")
public class QcWorkOrderVO {

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "工单ID（Snowflake），JSON 序列化为字符串避免前端精度丢失")
    private Long id;
    @Schema(description = "工单号")
    private String workOrderNo;
    @Schema(description = "线束编码")
    private String harnessCode;
    @Schema(description = "搬运耗时")
    private BigDecimal movingDuration;
    @Schema(description = "线束类型")
    private String harnessType;
    @Schema(description = "工位编码")
    private String stationCode;
    @Schema(description = "状态")
    private String status;
    @Schema(description = "质检结果 ok/ng/pending")
    private String qualityResult;
    @Schema(description = "关联调度ID列表")
    private List<String> taskIds;
    @Schema(description = "检测耗时")
    private BigDecimal detectionDuration;
    @Schema(description = "创建时间")
    private String createdAt;
    @Schema(description = "开始时间")
    private String startedAt;
    @Schema(description = "结束时间")
    private String endedAt;
    @Schema(description = "缺陷类型")
    private String defectType;
    @Schema(description = "缺陷描述")
    private String defectDescription;
    @Schema(description = "视频URL")
    private String videoUrl;
    @Schema(description = "图片URL")
    private String imageUrl;
}
