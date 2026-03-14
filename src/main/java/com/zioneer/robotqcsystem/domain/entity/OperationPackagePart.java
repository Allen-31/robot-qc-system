package com.zioneer.robotqcsystem.domain.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 安装包部件信息。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "安装包部件信息")
public class OperationPackagePart implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "部件名称", example = "api-gateway")
    private String part;

    @Schema(description = "版本号", example = "v3.2.1")
    private String version;
}
