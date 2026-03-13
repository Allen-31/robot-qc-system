package com.zioneer.robotqcsystem.domain.dto;

import com.zioneer.robotqcsystem.common.page.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 场景地图分页查询条件。
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "场景地图分页查询条件")
public class SceneMapQuery extends PageQuery {

    @Schema(description = "关键字（编码/名称）")
    private String keyword;

    @Schema(description = "地图类型")
    private String type;

    @Schema(description = "编辑状态")
    private String editStatus;

    @Schema(description = "发布状态")
    private String publishStatus;
}
