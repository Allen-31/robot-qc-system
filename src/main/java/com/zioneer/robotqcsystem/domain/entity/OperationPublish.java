package com.zioneer.robotqcsystem.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 发布任务实体，对应表 operation_publish。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperationPublish implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String packageName;
    private List<String> targetRobots;
    private List<String> targetRobotGroups;
    private List<String> targetRobotTypes;
    private String strategy;
    private Boolean restartAfterUpgrade;
    private String status;
    private String creator;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime completedAt;
}
