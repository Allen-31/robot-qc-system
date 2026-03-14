package com.zioneer.robotqcsystem.domain.dto;

import com.zioneer.robotqcsystem.common.page.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 任务流列表查询参数。
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "任务流列表查询参数")
public class OperationTaskQuery extends PageQuery {

    @Schema(description = "关键字（任务编码/外部单号/描述）")
    private String keyword;

    @Schema(description = "状态", example = "running")
    private String status;

    @Schema(description = "机器人编码", example = "RB-A101")
    private String robot;
}