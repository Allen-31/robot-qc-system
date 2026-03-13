package com.zioneer.robotqcsystem.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 更新设备请求（3.3.2.2）
 */
@Data
@Schema(description = "更新设备请求")
public class DeployDeviceUpdateDTO {

    @NotBlank(message = "设备名称不能为空")
    @Schema(description = "设备名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @NotBlank(message = "设备类型不能为空")
    @Schema(description = "设备类型", requiredMode = Schema.RequiredMode.REQUIRED)
    private String type;

    @Schema(description = "所属分组")
    private String group;

    @Schema(description = "地图编码")
    private String mapCode;

    @Schema(description = "状态", example = "online")
    private String status;

    @Schema(description = "IP 地址")
    private String ip;
}
