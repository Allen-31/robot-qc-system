package com.zioneer.robotqcsystem.domain.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Operation login log entity mapped to table ops_login_log.
 */
@Data
public class OpsLoginLog {

    private Long id;
    private String logCode;
    private String userCode;
    private String type;
    private String ip;
    private LocalDateTime createdAt;
}
