package com.zioneer.robotqcsystem.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 运营文件视图（4.4.1 文件列表/上传返回）
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "文件信息")
public class OperationFileVO {

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "文件ID，JSON 序列化为字符串")
    private Long id;

    @Schema(description = "文件名", example = "station-a1-inspection-video.mp4")
    private String name;

    @Schema(description = "文件类型/分类", example = "视频")
    private String type;

    @Schema(description = "大小（人类可读）", example = "128.4 MB")
    private String size;

    @Schema(description = "标签列表", example = "[\"工位\", \"质检结果\"]")
    private List<String> tags;

    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

    @Schema(description = "预览地址（视频/图片等）")
    private String previewUrl;

    @Schema(description = "预览文本内容（列表可为 null）")
    private String previewContent;
}
