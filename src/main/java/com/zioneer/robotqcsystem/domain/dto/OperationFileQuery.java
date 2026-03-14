package com.zioneer.robotqcsystem.domain.dto;

import com.zioneer.robotqcsystem.common.page.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 运营文件分页查询（4.4.1.1）
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "文件列表查询")
public class OperationFileQuery extends PageQuery {

    @Schema(description = "关键字（文件名模糊）")
    private String keyword;

    @Schema(description = "类型筛选", example = "视频")
    private String type;

    @Schema(description = "标签筛选，逗号分隔", example = "工位,质检")
    private String tags;
}
