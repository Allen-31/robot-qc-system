package com.zioneer.robotqcsystem.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 复检记录实体（qc_reinspection_record）
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QcReinspectionRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String reinspectionNo;
    private Long workOrderId;
    private String qualityResult;
    private String status;
    private String reinspectionResult;
    private String defectType;
    private LocalDateTime reinspectionTime;
    private String reviewer;
    private String videoUrl;
    private String imageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
