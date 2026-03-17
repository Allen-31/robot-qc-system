package com.zioneer.robotqcsystem.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * API log view object.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "API log")
public class OpsApiLogVO {

    @Schema(description = "Log code", example = "API-20260304-001")
    private String id;

    @Schema(description = "API name")
    private String apiName;

    @Schema(description = "Call result", example = "success")
    private String callResult;

    @Schema(description = "Fail reason")
    private String failReason;

    @Schema(description = "Response time (ms)")
    private Integer responseTime;

    @Schema(description = "Request info")
    private String requestInfo;

    @Schema(description = "Response info")
    private String responseInfo;

    @Schema(description = "Created at")
    private LocalDateTime createdAt;
}
