package com.zioneer.robotqcsystem.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 质检工位/质检台实体（qc_station）
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QcStation implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String stationId;
    private Long workstationId;
    private String name;
    private String mapPoint;
    private String status;
    private String callBoxCode;
    private String wireHarnessType;
    private Boolean detectionEnabled;
    private Boolean enabled;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
