package com.zioneer.robotqcsystem.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 许可证信息。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "许可证信息")
public class DeployLicenseVO {

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "许可证ID")
    private Long id;

    @Schema(description = "许可证名称")
    private String name;

    @Schema(description = "文件名")
    private String fileName;

    @Schema(description = "生效时间")
    private LocalDateTime effectiveAt;

    @Schema(description = "过期时间")
    private LocalDateTime expireAt;

    @Schema(description = "申请人")
    private String applicant;

    @Schema(description = "状态", example = "active")
    private String status;

    @Schema(description = "导入时间")
    private LocalDateTime importedAt;

    @Schema(description = "下载地址")
    private String downloadUrl;
}