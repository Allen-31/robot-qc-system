package com.zioneer.robotqcsystem.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Operation service log entity mapped to table operation_service_log.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperationServiceLog implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long serviceId;
    private String logName;
    private String type;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
