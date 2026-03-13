package com.zioneer.robotqcsystem.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 机器人类型实体（对应表 robot_type）。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RobotType implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String typeNo;
    private String typeName;
    private String image2d;
    private String image2dData;
    private Integer partsCount;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
