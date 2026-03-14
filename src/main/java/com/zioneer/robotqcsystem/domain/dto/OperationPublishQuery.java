package com.zioneer.robotqcsystem.domain.dto;

import com.zioneer.robotqcsystem.common.page.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 发布任务列表查询参数。
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "发布任务列表查询")
public class OperationPublishQuery extends PageQuery {

    @Schema(description = "关键字（名称/安装包/创建人）")
    private String keyword;

    @Schema(description = "状态", example = "running")
    private String status;

    @Schema(description = "升级策略", example = "idle")
    private String strategy;

    @Schema(description = "安装包名称", example = "robot-rb-series-v5.1.0.zip")
    private String packageName;

    @Schema(description = "创建人", example = "admin")
    private String creator;
}
