package com.zioneer.robotqcsystem.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 部署设备实体（呼叫盒等，与表 deploy_device 对应）
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeployDevice implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String code;
    private String name;
    private String type;
    private String deviceGroup;
    private String mapCode;
    private String status;
    private String ip;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
