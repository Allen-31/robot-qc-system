package com.zioneer.robotqcsystem.domain.dto;

import com.zioneer.robotqcsystem.common.page.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 机器人零部件分页查询条件。
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "机器人零部件分页查询条件")
public class RobotPartQuery extends PageQuery {

    @Schema(description = "关键字（编码/名称/型号）")
    private String keyword;

    @Schema(description = "类型")
    private String type;

    @Schema(description = "状态")
    private String status;
}
