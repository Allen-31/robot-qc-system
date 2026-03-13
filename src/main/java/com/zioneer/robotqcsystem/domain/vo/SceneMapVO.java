package com.zioneer.robotqcsystem.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 场景地图视图。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "场景地图视图")
public class SceneMapVO {

    @Schema(description = "主键")
    private Long id;

    @Schema(description = "地图编码")
    private String code;

    @Schema(description = "地图名称")
    private String name;

    @Schema(description = "地图类型")
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

    @Schema(description = "编辑人")
    private String editedBy;

    @Schema(description = "编辑时间")
    private LocalDateTime editedAt;

    @Schema(description = "发布人")
    private String publishedBy;

    @Schema(description = "发布时间")
    private LocalDateTime publishedAt;

    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

    @Schema(description = "更新时间")
    private LocalDateTime updatedAt;
}
