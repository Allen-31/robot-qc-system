package com.zioneer.robotqcsystem.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 充电策略触发规则视图。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "充电策略触发规则视图")
public class RobotChargeTriggerRuleVO {

    @Schema(description = "低电量阈值（%）")
    private Integer lowBatteryThreshold;

    @Schema(description = "最小充电时长（分钟）")
    private Integer minChargeMinutes;

    @Schema(description = "充电方式")
    private String chargeMethod;
}
