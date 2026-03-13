package com.zioneer.robotqcsystem.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 质检统计列表行（2.3.1.1）
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "质检统计行")
public class QcStatisticsRowVO {

    @Schema(description = "日期")
    private String date;
    @Schema(description = "工厂")
    private String factory;
    @Schema(description = "车间")
    private String workshop;
    @Schema(description = "工作站")
    private String workstation;
    @Schema(description = "工位")
    private String station;
    @Schema(description = "检验员")
    private String inspector;
    @Schema(description = "线束类型")
    private String wireHarness;
    @Schema(description = "项目")
    private String project;
    @Schema(description = "检验数")
    private Integer inspectionCount;
    @Schema(description = "缺陷数")
    private Integer defectCount;
    @Schema(description = "复检数")
    private Integer reinspectionCount;
    @Schema(description = "平均耗时(分钟)")
    private Double avgDurationMin;
}
