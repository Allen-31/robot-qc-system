package com.zioneer.robotqcsystem.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Map switch DTO.
 */
@Data
@Schema(description = "Robot map switch")
public class OpsRobotMapSwitchDTO {

    @NotBlank(message = "mapCode is required")
    @Schema(description = "Map code", example = "MAP-001")
    private String mapCode;

    @Schema(description = "Map name", example = "Assembly Line 1 Map")
    private String mapName;
}
