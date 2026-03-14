package com.zioneer.robotqcsystem.domain.dto;

import com.zioneer.robotqcsystem.common.page.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 许可证列表查询参数。
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "许可证列表查询参数")
public class DeployLicenseQuery extends PageQuery {

    @Schema(description = "关键字（名称/文件名/申请人）")
    private String keyword;

    @Schema(description = "状态", example = "active")
    private String status;
}