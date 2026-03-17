package com.zioneer.robotqcsystem.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Create DTO for exception notifications.
 */
@Data
@Schema(description = "Exception notification create DTO")
public class OpsExceptionNotificationCreateDTO {

    @NotBlank(message = "Level is required")
    @Schema(description = "Level", example = "P1")
    private String level;

    @NotBlank(message = "Type is required")
    @Schema(description = "Type", example = "Communication Exception")
    private String type;

    @Schema(description = "Source system", example = "Device Access Gateway")
    private String sourceSystem;

    @NotBlank(message = "Issue is required")
    @Schema(description = "Issue description")
    private String issue;

    @Schema(description = "Status", example = "pending")
    private String status;

    @Schema(description = "Related task code", example = "TK-20260304-004")
    private String relatedTask;

    @Schema(description = "Robot code", example = "RB-A101")
    private String robot;
}
