package com.zioneer.robotqcsystem.domain.dto;

import com.zioneer.robotqcsystem.common.page.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 质检记录列表查询（2.1.4.1）
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "质检记录查询条件")
public class QcQualityRecordQuery extends PageQuery {

    @Schema(description = "工单号/线束编码/线束类型/质检台等模糊")
    private String keyword;
    @Schema(description = "是否只显示NG工单，默认 true")
    private Boolean onlyNg;
}
