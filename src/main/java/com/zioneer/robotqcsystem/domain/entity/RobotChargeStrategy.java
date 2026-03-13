package com.zioneer.robotqcsystem.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Charge strategy entity (robot_charge_strategy).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RobotChargeStrategy implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String code;
    private String name;
    private String status;
    private String robotTypeNo;
    private String robotGroupNo;
    private Integer lowBatteryThreshold;
    private Integer minChargeMinutes;
    private String chargeMethod;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}