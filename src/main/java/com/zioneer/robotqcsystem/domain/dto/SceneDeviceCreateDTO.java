package com.zioneer.robotqcsystem.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 新增场景设备请求。
 */
@Data
@Schema(description = "新增场景设备请求")
public class SceneDeviceCreateDTO {

    @NotBlank(message = "设备编码不能为空")
    @Schema(description = "设备编码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String code;

    @NotBlank(message = "设备名称不能为空")
    @Schema(description = "设备名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @NotBlank(message = "设备类型不能为空")
    @Schema(description = "设备类型", requiredMode = Schema.RequiredMode.REQUIRED)
    private String type;

    @Schema(description = "IP地址")
    private String ip;

    @Schema(description = "在线状态")
    private String onlineStatus;

    @Schema(description = "是否异常")
    private Boolean isAbnormal;

    @Schema(description = "异常详情")
    private String exceptionDetail;

    @Schema(description = "地图编码")
    private String mapCode;
}
