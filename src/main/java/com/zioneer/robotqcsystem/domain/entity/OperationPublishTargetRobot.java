package com.zioneer.robotqcsystem.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 发布目标机器人简要信息。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperationPublishTargetRobot {

    private String robotCode;
    private String ip;
}

