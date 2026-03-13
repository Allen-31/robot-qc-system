package com.zioneer.robotqcsystem.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Create homing strategy request.
 */
@Data
@Schema(description = "Create homing strategy request")
public class RobotHomingStrategyCreateDTO {

    @NotBlank(message = "code is required")
    @Schema(description = "Strategy code", requiredMode = Schema.RequiredMode.REQUIRED)
    private String code;

    @NotBlank(message = "name is required")
    @Schema(description = "Strategy name", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "Status")
    private String status;

    @Schema(description = "Robot type number")
    private String robotTypeNo;

    @Schema(description = "Robot group number")
    private String robotGroupNo;

    @Valid
    @NotNull(message = "triggerRule is required")
    @Schema(description = "Trigger rule", requiredMode = Schema.RequiredMode.REQUIRED)
    private RobotHomingTriggerRuleDTO triggerRule;
}