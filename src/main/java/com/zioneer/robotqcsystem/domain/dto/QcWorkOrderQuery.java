package com.zioneer.robotqcsystem.domain.dto;

import com.zioneer.robotqcsystem.common.page.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 工单列表查询（2.1.3.1）
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "工单查询条件")
public class QcWorkOrderQuery extends PageQuery {

    @Schema(description = "工单号/线束编码等关键词")
    private String keyword;
    @Schema(description = "状态 pending/running/paused/finished/ng/cancelled")
    private String status;
    @Schema(description = "质检结果 ok/ng/pending")
    private String qualityResult;
    @Schema(description = "工位/质检台编码")
    private String stationCode;
    @Schema(description = "线束类型")
    private String harnessType;
    @Schema(description = "创建开始日期 yyyy-MM-dd")
    private String dateFrom;
    @Schema(description = "创建结束日期 yyyy-MM-dd")
    private String dateTo;
    @Schema(description = "排序 startedAt_desc/createdAt_desc")
    private String orderBy;
}
