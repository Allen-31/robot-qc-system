package com.zioneer.robotqcsystem.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 许可证实体，对应表 deploy_license。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeployLicense implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String fileName;
    private String storagePath;
    private Long sizeBytes;
    private String md5;
    private LocalDateTime effectiveAt;
    private LocalDateTime expireAt;
    private String applicant;
    private String status;
    private LocalDateTime importedAt;
}