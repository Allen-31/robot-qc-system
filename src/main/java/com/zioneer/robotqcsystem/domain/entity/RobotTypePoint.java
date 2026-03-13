package com.zioneer.robotqcsystem.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 机器人类型标注点实体（对应表 robot_type_point）。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RobotTypePoint implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long robotTypeId;
    private String partName;
    private String partPosition;
    private Double x;
    private Double y;
    private Double rotation;
    private String remark;
    private Integer sortOrder;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
