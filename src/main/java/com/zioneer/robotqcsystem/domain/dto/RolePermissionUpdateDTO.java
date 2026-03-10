package com.zioneer.robotqcsystem.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 角色权限保存请求（与前端 menuKey + actions 对应）
 */
@Data
@Schema(description = "角色权限保存请求")
public class RolePermissionUpdateDTO {

    @Schema(description = "权限项：菜单 key + 勾选动作")
    private List<PermissionItem> permissions;

    @Data
    @Schema(description = "单条权限")
    public static class PermissionItem {
        @Schema(description = "菜单/功能 key")
        private String menuKey;
        @Schema(description = "动作：display, create, edit, delete, view 等")
        private List<String> actions;
    }
}
