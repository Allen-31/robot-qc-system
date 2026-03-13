package com.zioneer.robotqcsystem.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 更新机器人分组请求。
 */
@Data
@Schema(description = "更新机器人分组请求")
public class RobotGroupUpdateDTO {

    @NotBlank(message = "分组名称不能为空")
    @Schema(description = "分组名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String groupName;

    @Schema(description = "分组描述")
    private String description;
}
