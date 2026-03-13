package com.zioneer.robotqcsystem.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 充电策略触发规则请求。
 */
@Data
@Schema(description = "充电策略触发规则请求")
public class RobotChargeTriggerRuleDTO {

    @NotNull(message = "低电量阈值不能为空")
    @Schema(description = "低电量阈值（%）", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer lowBatteryThreshold;

    @NotNull(message = "最小充电时长不能为空")
    @Schema(description = "最小充电时长（分钟）", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer minChargeMinutes;

    @NotNull(message = "充电方式不能为空")
    @Schema(description = "充电方式", requiredMode = Schema.RequiredMode.REQUIRED)
    private String chargeMethod;
}
