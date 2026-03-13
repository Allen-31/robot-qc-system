package com.zioneer.robotqcsystem.domain.dto;

import com.zioneer.robotqcsystem.common.page.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 场景设备分页查询条件。
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "场景设备分页查询条件")
public class SceneDeviceQuery extends PageQuery {

    @Schema(description = "关键字（编码/名称/类型/IP）")
    private String keyword;

    @Schema(description = "在线状态")
    private String onlineStatus;

    @Schema(description = "是否异常")
    private Boolean isAbnormal;

    @Schema(description = "地图编码")
    private String mapCode;
}
