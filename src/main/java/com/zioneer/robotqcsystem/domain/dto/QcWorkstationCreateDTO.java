package com.zioneer.robotqcsystem.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 新增工作站（业务 2.1.1 / 配置 2.2.1 风格）
 */
@Data
@Schema(description = "新增工作站请求")
public class QcWorkstationCreateDTO {

    @NotBlank(message = "工作站名称不能为空")
    @Schema(description = "工作站名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "车间编码")
    private String workshopCode;

    @Schema(description = "线束类型")
    private String wireHarnessType;

    @Schema(description = "机器人分组")
    private String robotGroup;

    @Schema(description = "是否启用")
    private Boolean enabled;
}
