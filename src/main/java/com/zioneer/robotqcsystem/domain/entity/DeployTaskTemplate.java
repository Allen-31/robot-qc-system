package com.zioneer.robotqcsystem.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 任务模板实体，对应表 deploy_task_template。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeployTaskTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    private String code;
    private String name;
    private Boolean enabled;
    private String createdBy;
    private LocalDateTime createdAt;
    private String updatedBy;
    private LocalDateTime updatedAt;
}