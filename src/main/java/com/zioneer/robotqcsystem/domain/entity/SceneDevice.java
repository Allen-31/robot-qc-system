package com.zioneer.robotqcsystem.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 场景设备实体（对应表 scene_device）。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SceneDevice implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String code;
    private String name;
    private String type;
    private String ip;
    private String onlineStatus;
    private Integer isAbnormal;
    private String exceptionDetail;
    private String mapCode;
    private LocalDateTime lastOnlineAt;
    private LocalDateTime lastHeartbeatAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
