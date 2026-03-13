package com.zioneer.robotqcsystem.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 机器人零部件实体（对应表 robot_part）。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RobotPart implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String partNo;
    private String name;
    private String position;
    private String type;
    private String model;
    private String vendor;
    private String supplier;
    private String lifecycle;
    private String status;
    private String remark;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
