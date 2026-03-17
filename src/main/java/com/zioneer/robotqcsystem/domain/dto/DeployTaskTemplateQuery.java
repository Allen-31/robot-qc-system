package com.zioneer.robotqcsystem.domain.dto;

import com.zioneer.robotqcsystem.common.page.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 任务模板列表查询参数。
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "任务模板列表查询参数")
public class DeployTaskTemplateQuery extends PageQuery {

    @Schema(description = "关键字（编码/名称）")
    private String keyword;

    @Schema(description = "是否启用")
    private Boolean enabled;
}