package com.zioneer.robotqcsystem.domain.dto;

import com.zioneer.robotqcsystem.common.page.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 安装包列表查询参数。
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "安装包列表查询")
public class OperationPackageQuery extends PageQuery {

    @Schema(description = "关键字（名称/描述）")
    private String keyword;

    @Schema(description = "类型", example = "cloud")
    private String type;

    @Schema(description = "部件名称", example = "api-gateway")
    private String part;

    @Schema(description = "MD5")
    private String md5;

    @Schema(description = "上传者")
    private String uploader;
}
