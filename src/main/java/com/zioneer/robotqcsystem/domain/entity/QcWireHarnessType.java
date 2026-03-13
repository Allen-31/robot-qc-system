package com.zioneer.robotqcsystem.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 线束类型实体（qc_wire_harness_type）
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QcWireHarnessType implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String project;
    private String taskType;
    private String planarStructureFile;
    private String threeDStructureFile;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
