package com.zioneer.robotqcsystem.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 角色权限项 VO（与前端 menuList + PermissionAction 对应）
 */
@Data
@Schema(description = "角色权限项")
public class RolePermissionVO {

    @Schema(description = "菜单/功能 key")
    private String menuKey;
    @Schema(description = "勾选动作：display, create, edit, delete, view 等")
    private List<String> actions;
}
