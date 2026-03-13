package com.zioneer.robotqcsystem.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 归位策略触发规则视图。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "归位策略触发规则视图")
public class RobotHomingTriggerRuleVO {

    @Schema(description = "空闲等待秒数")
    private Integer idleWaitSeconds;
}
