package com.zioneer.robotqcsystem.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 质检车间实体（qc_workshop），主键 code
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QcWorkshop implements Serializable {

    private static final long serialVersionUID = 1L;

    private String code;
    private String name;
    private String location;
    private Boolean enabled;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
