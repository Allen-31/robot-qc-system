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

    @Schema(description = "关键字（编码/名称/序列号/IP）")
    private String keyword;

    @Schema(description = "机器人编码（模糊）")
    private String robotCode;

    @Schema(description = "状态")
    private String status;

    @Schema(description = "机器人类型编号")
    private String robotTypeNo;

    @Schema(description = "分组编号")
    private String groupNo;

    @Schema(description = "在线状态")
    private String onlineStatus;

    @Schema(description = "异常状态")
    private String exceptionStatus;
}
