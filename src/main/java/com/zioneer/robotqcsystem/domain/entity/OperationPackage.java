package com.zioneer.robotqcsystem.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 安装包实体，对应表 operation_package。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperationPackage implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String type;
    private List<OperationPackagePart> targetParts;
    private String description;
    private Long sizeBytes;
    private String md5;
    private String uploader;
    private LocalDateTime uploadedAt;
    private String storagePath;
}
