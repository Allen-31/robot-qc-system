package com.zioneer.robotqcsystem.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 更新工位配置（2.2.2.3）
 */
@Data
@Schema(description = "更新工位配置请求")
public class QcStationConfigUpdateDTO {

    @NotBlank(message = "地图点位不能为空")
    @Schema(description = "地图点位", requiredMode = Schema.RequiredMode.REQUIRED)
    private String mapPoint;

    @NotBlank(message = "呼叫盒编码不能为空")
    @Schema(description = "呼叫盒编码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String callBoxCode;

    @Schema(description = "线束类型")
    private String wireHarnessType;

    @Schema(description = "是否开启检测")
    private Boolean detectionEnabled;

    @Schema(description = "是否启用")
    private Boolean enabled;
}
