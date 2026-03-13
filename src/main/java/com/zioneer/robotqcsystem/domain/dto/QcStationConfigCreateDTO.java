package com.zioneer.robotqcsystem.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 新增工位配置（2.2.2.2）
 */
@Data
@Schema(description = "新增工位配置请求")
public class QcStationConfigCreateDTO {

    @NotBlank(message = "工作站ID不能为空")
    @Schema(description = "工作站ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String workstationId;

    @NotBlank(message = "工位ID不能为空")
    @Schema(description = "工位ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String stationId;

    @NotBlank(message = "地图点位不能为空")
    @Schema(description = "地图点位", requiredMode = Schema.RequiredMode.REQUIRED)
    private String mapPoint;

    @NotBlank(message = "呼叫盒编码不能为空")
    @Schema(description = "呼叫盒编码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String callBoxCode;

    @Schema(description = "线束类型")
    private String wireHarnessType;

    @Schema(description = "是否开启检测，默认 true")
    private Boolean detectionEnabled;

    @Schema(description = "是否启用，默认 true")
    private Boolean enabled;
}
