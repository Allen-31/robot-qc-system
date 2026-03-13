package com.zioneer.robotqcsystem.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 工单实体（qc_work_order）
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QcWorkOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String workOrderNo;
    private String harnessCode;
    private BigDecimal movingDuration;
    private String harnessType;
    private String stationCode;
    private Long stationId;
    private String status;
    private String qualityResult;
    private List<String> taskIds;
    private BigDecimal detectionDuration;
    private LocalDateTime createdAt;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
    private String defectType;
    private String defectDescription;
    private String videoUrl;
    private String imageUrl;
    private LocalDateTime updatedAt;
}
