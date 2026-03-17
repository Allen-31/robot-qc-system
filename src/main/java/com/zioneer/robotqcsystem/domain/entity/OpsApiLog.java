package com.zioneer.robotqcsystem.domain.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Operation API log entity mapped to table ops_api_log.
 */
@Data
public class OpsApiLog {

    private Long id;
    private String logCode;
    private String apiName;
    private String callResult;
    private String failReason;
    private Integer responseTime;
    private String requestInfo;
    private String responseInfo;
    private LocalDateTime createdAt;
}
