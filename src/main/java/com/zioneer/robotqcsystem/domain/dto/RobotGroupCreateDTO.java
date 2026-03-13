package com.zioneer.robotqcsystem.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 新增机器人分组请求。
 */
@Data
@Schema(description = "新增机器人分组请求")
public class RobotGroupCreateDTO {

    @NotBlank(message = "分组编号不能为空")
    @Schema(description = "分组编号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String groupNo;

    @NotBlank(message = "分组名称不能为空")
    @Schema(description = "分组名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String groupName;

    @Schema(description = "分组描述")
    private String description;
}
