package com.zioneer.robotqcsystem.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Robot mode switch DTO.
 */
@Data
@Schema(description = "Robot mode switch")
public class OpsRobotModeSwitchDTO {

    @NotBlank(message = "mode is required")
    @Schema(description = "Mode", example = "auto")
    private String mode;
}
