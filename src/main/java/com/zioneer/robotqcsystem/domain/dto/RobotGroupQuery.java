package com.zioneer.robotqcsystem.domain.dto;

import com.zioneer.robotqcsystem.common.page.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 机器人分组分页查询条件。
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "机器人分组分页查询条件")
public class RobotGroupQuery extends PageQuery {

    @Schema(description = "关键字（编号/名称）")
    private String keyword;
}
