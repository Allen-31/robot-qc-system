package com.zioneer.robotqcsystem.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 质检区指标汇总（2.3.1.2）
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "质检区指标汇总")
public class QcStatisticsSummaryVO {

    @Schema(description = "质检区ID/编码")
    private String workstationId;
    @Schema(description = "质检区名称")
    private String workstationName;
    @Schema(description = "时间维度 day/week")
    private String dimension;
    @Schema(description = "基准日期")
    private String date;
    @Schema(description = "展示用如今日/本周")
    private String periodLabel;
    @Schema(description = "质检总数")
    private Integer totalCount;
    @Schema(description = "平均用时(分钟)")
    private Double avgDurationMinutes;
    @Schema(description = "质检失败量(NG数)")
    private Integer failCount;
    @Schema(description = "缺陷数")
    private Integer defectCount;
    @Schema(description = "复检数")
    private Integer reinspectionCount;
}
