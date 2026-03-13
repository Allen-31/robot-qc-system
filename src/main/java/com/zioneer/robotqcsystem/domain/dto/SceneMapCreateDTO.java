package com.zioneer.robotqcsystem.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 新增场景地图请求。
 */
@Data
@Schema(description = "新增场景地图请求")
public class SceneMapCreateDTO {

    @NotBlank(message = "地图编码不能为空")
    @Schema(description = "地图编码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String code;

    @NotBlank(message = "地图名称不能为空")
    @Schema(description = "地图名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @NotBlank(message = "地图类型不能为空")
    @Schema(description = "地图类型", requiredMode = Schema.RequiredMode.REQUIRED)
    private String type;

    @Schema(description = "编辑状态")
    private String editStatus;

    @Schema(description = "发布状态")
    private String publishStatus;

    @Schema(description = "地图文件地址")
    private String mapFileUrl;

    @Schema(description = "地图版本")
    private String mapVersion;

    @Schema(description = "分辨率")
    private Double resolution;

    @Schema(description = "原点X")
    private Double originX;

    @Schema(description = "原点Y")
    private Double originY;
}
