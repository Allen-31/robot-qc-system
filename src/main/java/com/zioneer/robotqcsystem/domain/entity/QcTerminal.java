package com.zioneer.robotqcsystem.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 终端配置实体（qc_terminal）
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QcTerminal implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String code;
    private String sn;
    private String terminalType;
    private String terminalIp;
    private String workstationId;
    private List<String> boundStationIds;
    private Boolean online;
    private String currentUser;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
