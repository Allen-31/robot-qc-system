package com.zioneer.robotqcsystem.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 机器人实体（与表 robot 对应，请求/响应请使用 DTO/VO）
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Robot implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String robotCode;
    private String robotName;
    private String serialNo;
    private String ip;
    private String model;
    private String firmwareVersion;
    private String robotTypeNo;
    private String robotTypeName;
    private String groupNo;
    private String groupName;
    private String status;
    private String onlineStatus;
    private Integer battery;
    private Double mileageKm;
    private String currentMapCode;
    private String currentMapName;
    private String dispatchMode;
    private String controlStatus;
    private String exceptionStatus;
    private String chassisMode;
    private String armMode;
    private Boolean isCharging;
    private Boolean isHoming;
    private Boolean isLifted;
    private String videoUrl;
    private String location;
    private LocalDateTime lastInspectionAt;
    private LocalDateTime registeredAt;
    private LocalDateTime lastOnlineAt;
    private LocalDateTime lastHeartbeatAt;
}
