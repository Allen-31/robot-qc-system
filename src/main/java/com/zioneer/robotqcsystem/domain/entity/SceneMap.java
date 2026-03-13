package com.zioneer.robotqcsystem.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 场景地图实体（对应表 scene_map）。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SceneMap implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String code;
    private String name;
    private String type;
    private String editStatus;
    private String publishStatus;
    private String mapFileUrl;
    private String mapVersion;
    private Double resolution;
    private Double originX;
    private Double originY;
    private String editedBy;
    private LocalDateTime editedAt;
    private String publishedBy;
    private LocalDateTime publishedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
