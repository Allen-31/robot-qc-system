package com.zioneer.robotqcsystem.domain.dto;

import com.zioneer.robotqcsystem.common.page.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 质检统计查询（2.3.1.1）
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "质检统计查询条件")
public class QcStatisticsQuery extends PageQuery {

    @Schema(description = "开始日期", requiredMode = Schema.RequiredMode.REQUIRED)
    private String dateFrom;
    @Schema(description = "结束日期", requiredMode = Schema.RequiredMode.REQUIRED)
    private String dateTo;
    @Schema(description = "工厂")
    private String factory;
    @Schema(description = "车间")
    private String workshop;
    @Schema(description = "工作站")
    private String workstation;
    @Schema(description = "工位")
    private String station;
}
