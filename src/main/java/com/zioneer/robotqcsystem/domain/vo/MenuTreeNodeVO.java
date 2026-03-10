package com.zioneer.robotqcsystem.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 菜单树节点 VO（与前端 MenuNode 对应）
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "菜单树节点")
public class MenuTreeNodeVO {

    @Schema(description = "主键")
    private Long id;
    @Schema(description = "业务编码，全局唯一")
    private String code;
    @Schema(description = "前端 i18n key")
    private String nameKey;
    @Schema(description = "路由路径")
    private String path;
    @Schema(description = "父节点 id，null 表示一级")
    private Long parentId;
    @Schema(description = "同层排序")
    private Integer sortOrder;
    @Schema(description = "图标名")
    private String icon;
    @Schema(description = "权限标识")
    private String permission;
    @Schema(description = "enabled / disabled")
    private String status;
    @Schema(description = "子节点")
    private List<MenuTreeNodeVO> children;
}
