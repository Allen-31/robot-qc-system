package com.zioneer.robotqcsystem.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.zioneer.robotqcsystem.domain.entity.OperationPackagePart;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 安装包视图对象。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "安装包信息")
public class OperationPackageVO {

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "安装包ID")
    private Long id;

    @Schema(description = "安装包名称")
    private String name;

    @Schema(description = "类型", example = "cloud")
    private String type;

    @Schema(description = "目标部件列表")
    private List<OperationPackagePart> targetParts;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "文件大小（可读）")
    private String size;

    @Schema(description = "MD5")
    private String md5;

    @Schema(description = "上传者")
    private String uploader;

    @Schema(description = "上传时间")
    private LocalDateTime uploadedAt;

    @Schema(description = "下载地址")
    private String downloadUrl;
}
