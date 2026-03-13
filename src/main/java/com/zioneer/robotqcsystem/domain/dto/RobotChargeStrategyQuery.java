package com.zioneer.robotqcsystem.domain.dto;

import com.zioneer.robotqcsystem.common.page.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 充电策略分页查询条件。
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "充电策略分页查询条件")
public class RobotChargeStrategyQuery extends PageQuery {

    @Schema(description = "关键字（编码/名称）")
    private String keyword;

    @Schema(description = "状态")
    private String status;
}
