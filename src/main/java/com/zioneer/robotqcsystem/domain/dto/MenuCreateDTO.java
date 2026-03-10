package com.zioneer.robotqcsystem.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 新增菜单请求
 */
@Data
@Schema(description = "新增菜单请求")
public class MenuCreateDTO {

    @NotBlank(message = "菜单编码不能为空")
    @Schema(description = "业务编码，全局唯一", requiredMode = Schema.RequiredMode.REQUIRED)
    private String code;

    @NotBlank(message = "nameKey 不能为空")
    @Schema(description = "前端 i18n key", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nameKey;

    @Schema(description = "路由路径，目录可空")
    private String path;
    @Schema(description = "父节点 id，null 表示一级")
    private Long parentId;
    @Schema(description = "同层排序")
    private Integer sortOrder = 0;
    @Schema(description = "图标名")
    private String icon;
    @Schema(description = "权限标识")
    private String permission;
    @Schema(description = "enabled / disabled")
    private String status = "enabled";
}
