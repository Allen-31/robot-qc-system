package com.zioneer.robotqcsystem.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Dispatch mode switch DTO.
 */
@Data
@Schema(description = "Robot dispatch mode switch")
public class OpsRobotDispatchModeDTO {

    @NotBlank(message = "dispatchMode is required")
    @Schema(description = "Dispatch mode", example = "auto")
    private String dispatchMode;
}
