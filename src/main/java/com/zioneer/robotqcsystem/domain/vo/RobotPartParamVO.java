package com.zioneer.robotqcsystem.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 机器人零部件技术参数视图。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "机器人零部件技术参数视图")
public class RobotPartParamVO {

    @Schema(description = "参数名称")
    private String name;

    @Schema(description = "参数值")
    private String value;

    @Schema(description = "单位")
    private String unit;

    @Schema(description = "范围")
    private String range;

    @Schema(description = "排序")
    private Integer sortOrder;
}
