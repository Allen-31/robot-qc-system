package com.zioneer.robotqcsystem.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 工单复检提交（2.1.3.4）
 */
@Data
@Schema(description = "工单复检请求")
public class QcWorkOrderReviewDTO {

    @NotBlank(message = "复检结果不能为空")
    @Schema(description = "复检结果 ok/ng/pending", requiredMode = Schema.RequiredMode.REQUIRED)
    private String qualityResult;

    @Schema(description = "缺陷类型")
    private String defectType;

    @Schema(description = "缺陷描述")
    private String defectDescription;
}
