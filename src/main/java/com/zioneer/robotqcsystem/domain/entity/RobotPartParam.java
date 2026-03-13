package com.zioneer.robotqcsystem.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 机器人零部件技术参数实体（对应表 robot_part_param）。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RobotPartParam implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long partId;
    private String name;
    private String value;
    private String unit;
    private String range;
    private Integer sortOrder;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
