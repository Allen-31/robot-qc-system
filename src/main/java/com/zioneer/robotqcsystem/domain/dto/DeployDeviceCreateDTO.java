package com.zioneer.robotqcsystem.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 新增设备请求（3.3.2.2）
 */
@Data
@Schema(description = "新增设备请求")
public class DeployDeviceCreateDTO {

    @NotBlank(message = "设备编码不能为空")
    @Schema(description = "设备编码", example = "DEV-002", requiredMode = Schema.RequiredMode.REQUIRED)
    private String code;

    @NotBlank(message = "设备名称不能为空")
    @Schema(description = "设备名称", example = "呼叫盒A-02", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @NotBlank(message = "设备类型不能为空")
    @Schema(description = "设备类型", example = "callBox", requiredMode = Schema.RequiredMode.REQUIRED)
    private String type;

    @Schema(description = "所属分组", example = "总装一线")
    private String group;

    @Schema(description = "地图编码", example = "MAP-001")
    private String mapCode;

    @Schema(description = "IP 地址", example = "10.10.5.102")
    private String ip;
}
