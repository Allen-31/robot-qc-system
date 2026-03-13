package com.zioneer.robotqcsystem.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 新增线束类型（2.2.3.2）
 */
@Data
@Schema(description = "新增线束类型请求")
public class QcWireHarnessTypeCreateDTO {

    @NotBlank(message = "名称不能为空")
    @Schema(description = "线束类型名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "项目")
    private String project;

    @Schema(description = "任务类型")
    private String taskType;

    @Schema(description = "平面结构文件路径")
    private String planarStructureFile;

    @Schema(description = "三维结构文件路径")
    private String threeDStructureFile;
}
