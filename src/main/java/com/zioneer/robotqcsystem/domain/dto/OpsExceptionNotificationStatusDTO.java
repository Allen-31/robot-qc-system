package com.zioneer.robotqcsystem.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Update status DTO for exception notifications.
 */
@Data
@Schema(description = "Exception notification status update")
public class OpsExceptionNotificationStatusDTO {

    @NotBlank(message = "Status is required")
    @Schema(description = "Status", example = "processing")
    private String status;
}
