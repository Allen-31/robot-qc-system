package com.zioneer.robotqcsystem.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Charge strategy view.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Charge strategy view")
public class RobotChargeStrategyVO {

    @Schema(description = "Strategy code")
    private String code;

    @Schema(description = "Strategy name")
    private String name;

    @Schema(description = "Status")
    private String status;

    @Schema(description = "Robot type number")
    private String robotTypeNo;

    @Schema(description = "Robot group number")
    private String robotGroupNo;

    @Schema(description = "Trigger rule")
    private RobotChargeTriggerRuleVO triggerRule;

    @Schema(description = "Created time")
    private LocalDateTime createdAt;

    @Schema(description = "Updated time")
    private LocalDateTime updatedAt;
}