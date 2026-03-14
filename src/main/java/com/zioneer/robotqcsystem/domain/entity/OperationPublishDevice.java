package com.zioneer.robotqcsystem.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 发布任务设备实体，对应表 operation_publish_device。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperationPublishDevice implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long publishId;
    private String deviceName;
    private String ip;
    private String status;
    private String packageName;
    private String version;
    private Integer progress;
    private LocalDateTime updatedAt;
    private LocalDateTime completedAt;
}
