package com.zioneer.robotqcsystem.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 更新工单（2.1.3.3）
 */
@Data
@Schema(description = "更新工单请求")
public class QcWorkOrderUpdateDTO {

    @Schema(description = "线束类型")
    private String harnessType;
    @Schema(description = "缺陷类型")
    private String defectType;
    @Schema(description = "缺陷描述")
    private String defectDescription;
    @Schema(description = "质检结果 ok/ng/pending")
    private String qualityResult;
}
