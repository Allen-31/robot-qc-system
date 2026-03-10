package com.zioneer.robotqcsystem.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 系统菜单实体
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String code;
    private String nameKey;
    private String path;
    private Long parentId;
    private Integer sortOrder;
    private String icon;
    private String permission;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /** 子节点（树形组装，非表字段） */
    private List<SysMenu> children;
}
