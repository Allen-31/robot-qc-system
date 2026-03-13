package com.zioneer.robotqcsystem.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 线束类型视图（2.2.3）
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "线束类型")
public class QcWireHarnessTypeVO {

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "主键（Snowflake），JSON 序列化为字符串避免前端精度丢失")
    private Long id;
    @Schema(description = "名称")
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
