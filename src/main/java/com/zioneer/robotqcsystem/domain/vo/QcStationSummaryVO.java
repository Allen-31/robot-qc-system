package com.zioneer.robotqcsystem.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 当前质检台统计（2.1.2.3 / 2.3.1.2 风格）
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "质检台统计汇总")
public class QcStationSummaryVO {

    @Schema(description = "质检台编号")
    private String stationId;
    @Schema(description = "质检台名称")
    private String stationName;
    @Schema(description = "时间维度 day/week")
    private String dimension;
    @Schema(description = "基准日期")
    private String date;
    @Schema(description = "展示用如今日/本周")
    private String periodLabel;
    @Schema(description = "质检数量")
    private Integer inspectionCount;
    @Schema(description = "机器人效率 %")
    private Double robotEfficiencyRate;
    @Schema(description = "良品率 %")
    private Double passRate;
    @Schema(description = "排名")
    private Integer rank;
    @Schema(description = "缺陷数")
    private Integer defectCount;
    @Schema(description = "复检数")
    private Integer reinspectionCount;
}
