package com.zioneer.robotqcsystem.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Update charge strategy request.
 */
@Data
@Schema(description = "Update charge strategy request")
public class RobotChargeStrategyUpdateDTO {

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
    private RobotChargeTriggerRuleDTO triggerRule;
}