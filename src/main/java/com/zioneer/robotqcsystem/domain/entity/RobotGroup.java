package com.zioneer.robotqcsystem.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 机器人分组实体（对应表 robot_group）。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RobotGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String groupNo;
    private String groupName;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
