package com.zioneer.robotqcsystem.domain.dto;

import com.zioneer.robotqcsystem.common.page.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 复检记录列表查询（2.1.5.1）
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "复检记录查询条件")
public class QcReinspectionRecordQuery extends PageQuery {

    @Schema(description = "复检单号/工单号/线束编码")
    private String keyword;
    @Schema(description = "状态 pending/completed/cancelled")
    private String status;
    @Schema(description = "复检结果 ok/ng/pending")
    private String reinspectionResult;
    @Schema(description = "复检时间起")
    private String dateFrom;
    @Schema(description = "复检时间止")
    private String dateTo;
}
