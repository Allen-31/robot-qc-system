package com.zioneer.robotqcsystem.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 运营任务流实体，对应表 operation_task。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperationTask implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String code;
    private String externalCode;
    private String status;
    private String robotCode;
    private Integer priority;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime endedAt;
    private LocalDateTime updatedAt;
}