package com.zioneer.robotqcsystem.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 更新菜单请求
 */
@Data
@Schema(description = "更新菜单请求")
public class MenuUpdateDTO {

    @Schema(description = "前端 i18n key")
    private String nameKey;
    @Schema(description = "路由路径")
    private String path;
    @Schema(description = "同层排序")
    private Integer sortOrder;
    @Schema(description = "图标名")
    private String icon;
    @Schema(description = "权限标识")
    private String permission;
    @Schema(description = "enabled / disabled")
    private String status;
}
