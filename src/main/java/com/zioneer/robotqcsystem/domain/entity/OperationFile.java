package com.zioneer.robotqcsystem.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 运营文件实体（4.4.1 文件管理，与表 operation_file 对应）
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperationFile implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String type;
    private Long sizeBytes;
    /** 标签，逗号分隔存储 */
    private String tags;
    private String storagePath;
    private String previewContent;
    private LocalDateTime createdAt;
}
