package com.zioneer.robotqcsystem.domain.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 机器人实体
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Robot implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    @NotBlank(message = "机器人编码不能为空")
    private String robotCode;
    @NotBlank(message = "机器人名称不能为空")
    private String robotName;
    private String model;
    @NotBlank(message = "状态不能为空")
    private String status;
    private String location;
    private LocalDateTime lastInspectionAt;
}
