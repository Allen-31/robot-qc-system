package com.zioneer.robotqcsystem.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Operation log view object.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Operation log")
public class OpsOperationLogVO {

    @Schema(description = "Log code", example = "OP-20260304-001")
    private String id;

    @Schema(description = "User")
    private String user;

    @Schema(description = "Operation type")
    private String operationType;

    @Schema(description = "Result", example = "success")
    private String result;

    @Schema(description = "Fail reason")
    private String failReason;

    @Schema(description = "Response time (ms)")
    private Integer responseTime;

    @Schema(description = "IP address")
    private String ip;

    @Schema(description = "Request info")
    private String requestInfo;

    @Schema(description = "Response info")
    private String responseInfo;

    @Schema(description = "Created at")
    private LocalDateTime createdAt;
}
