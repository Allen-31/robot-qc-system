package com.zioneer.robotqcsystem.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 工作站配置视图（2.2.1：id, name, workshopCode, wireHarnessType, robotGroup, enabled）
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "工作站配置")
public class QcWorkstationConfigVO {

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "配置ID（Snowflake），JSON 序列化为字符串避免前端精度丢失")
    private Long id;
    @Schema(description = "工作站名称")
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
