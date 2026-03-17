package com.zioneer.robotqcsystem.domain.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Operation exception notification entity mapped to table ops_exception_notification.
 */
@Data
public class OpsExceptionNotification {

    private Long id;
    private String code;
    private String level;
    private String type;
    private String sourceSystem;
    private String issue;
    private String status;
    private String relatedTask;
    private String robotCode;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
