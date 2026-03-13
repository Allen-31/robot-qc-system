package com.zioneer.robotqcsystem.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Homing strategy entity (robot_homing_strategy).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RobotHomingStrategy implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String code;
    private String name;
    private String status;
    private String robotTypeNo;
    private String robotGroupNo;
    private Integer idleWaitSeconds;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}