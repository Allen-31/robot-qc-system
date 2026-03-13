package com.zioneer.robotqcsystem.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 归位策略触发规则请求。
 */
@Data
@Schema(description = "归位策略触发规则请求")
public class RobotHomingTriggerRuleDTO {

    @NotNull(message = "空闲等待秒数不能为空")
    @Schema(description = "空闲等待秒数", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer idleWaitSeconds;
}
