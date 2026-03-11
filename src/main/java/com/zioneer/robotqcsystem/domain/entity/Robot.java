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
    private String model;
    private String status;
    private String location;
    private LocalDateTime lastInspectionAt;
}
