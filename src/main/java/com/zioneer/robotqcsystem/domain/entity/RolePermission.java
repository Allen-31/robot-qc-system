package com.zioneer.robotqcsystem.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 角色权限（菜单+动作）
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RolePermission implements Serializable {

    private static final long serialVersionUID = 1L;

    private String roleCode;
    private String menuKey;
    /** 动作列表：display, create, edit, delete, view 等 */
    private List<String> actions;
}
