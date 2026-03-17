package com.zioneer.robotqcsystem.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Robot lift switch DTO.
 */
@Data
@Schema(description = "Robot lift switch")
public class OpsRobotLiftDTO {

    @NotNull(message = "enabled is required")
    @Schema(description = "Lift enabled", example = "true")
    private Boolean enabled;
}
