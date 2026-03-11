package com.zioneer.robotqcsystem.domain.dto;

import com.zioneer.robotqcsystem.common.page.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 机器人分页查询条件
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "机器人查询条件")
public class RobotQuery extends PageQuery {

    @Schema(description = "机器人编码（模糊）")
    private String robotCode;

    @Schema(description = "状态")
    private String status;
}
