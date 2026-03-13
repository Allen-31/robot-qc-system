package com.zioneer.robotqcsystem.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 质检工作站实体（qc_workstation），业务列表与配置共用
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QcWorkstation implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String code;
    private String name;
    private String workshopCode;
    private String status;
    private String robotGroup;
    private String wireHarnessType;
    private Boolean enabled;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
