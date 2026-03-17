package com.zioneer.robotqcsystem.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Exception notification view object.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Exception notification")
public class OpsExceptionNotificationVO {

    @Schema(description = "Notification code", example = "EX-20260304-001")
    private String id;

    @Schema(description = "Level", example = "P1")
    private String level;

    @Schema(description = "Type")
    private String type;

    @Schema(description = "Source system")
    private String sourceSystem;

    @Schema(description = "Issue description")
    private String issue;

    @Schema(description = "Status", example = "pending")
    private String status;

    @Schema(description = "Related task")
    private String relatedTask;

    @Schema(description = "Robot code")
    private String robot;

    @Schema(description = "Created at")
    private LocalDateTime createdAt;
}
