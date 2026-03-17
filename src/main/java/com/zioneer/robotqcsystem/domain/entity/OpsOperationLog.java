package com.zioneer.robotqcsystem.domain.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Operation action log entity mapped to table ops_operation_log.
 */
@Data
public class OpsOperationLog {

    private Long id;
    private String logCode;
    private String userCode;
    private String operationType;
    private String result;
    private String failReason;
    private Integer responseTime;
    private String ip;
    private String requestInfo;
    private String responseInfo;
    private LocalDateTime createdAt;
}
