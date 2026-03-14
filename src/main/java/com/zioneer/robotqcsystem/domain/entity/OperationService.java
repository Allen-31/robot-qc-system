package com.zioneer.robotqcsystem.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Operation service entity mapped to table operation_service.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperationService implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String type;
    private String version;
    private String ip;
    private String status;
    private BigDecimal cpuUsage;
    private BigDecimal memoryUsage;
    private String runtime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
