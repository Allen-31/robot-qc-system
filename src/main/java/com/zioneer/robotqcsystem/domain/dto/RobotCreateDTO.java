package com.zioneer.robotqcsystem.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Create robot request.
 */
@Data
@Schema(description = "Create robot request")
public class RobotCreateDTO {

    @NotBlank(message = "Robot code is required")
    @Schema(description = "Robot code", requiredMode = Schema.RequiredMode.REQUIRED)
    private String robotCode;

    @NotBlank(message = "Robot name is required")
    @Schema(description = "Robot name", requiredMode = Schema.RequiredMode.REQUIRED)
    private String robotName;

    @Schema(description = "Serial number")
    private String serialNo;

    @Schema(description = "IP address")
    private String ip;

    @Schema(description = "Model")
    private String model;

    @Schema(description = "Firmware version")
    private String firmwareVersion;

    @Schema(description = "Robot type code")
    private String robotTypeNo;

    @Schema(description = "Robot type name")
    private String robotTypeName;

    @Schema(description = "Group code")
    private String groupNo;

    @Schema(description = "Group name")
    private String groupName;

    @NotBlank(message = "Status is required")
    @Schema(description = "Status", requiredMode = Schema.RequiredMode.REQUIRED)
    private String status;

    @Schema(description = "Online status")
    private String onlineStatus;

    @Schema(description = "Battery (%)")
    private Integer battery;

    @Schema(description = "Mileage (km)")
    private Double mileageKm;

    @Schema(description = "Current map code")
    private String currentMapCode;

    @Schema(description = "Current map name")
    private String currentMapName;

    @Schema(description = "Dispatch mode")
    private String dispatchMode;

    @Schema(description = "Control status")
    private String controlStatus;

    @Schema(description = "Exception status")
    private String exceptionStatus;

    @Schema(description = "Video URL")
    private String videoUrl;

    @Schema(description = "Location")
    private String location;

    @Schema(description = "Last inspection time")
    private LocalDateTime lastInspectionAt;

    @Schema(description = "Registered time")
    private LocalDateTime registeredAt;

    @Schema(description = "Last online time")
    private LocalDateTime lastOnlineAt;

    @Schema(description = "Last heartbeat time")
    private LocalDateTime lastHeartbeatAt;
}
