package com.zioneer.robotqcsystem.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 工作站视图（业务列表 2.1.1：id, code, name, workshopCode, status, robotGroup）
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "工作站信息-业务列表")
public class QcWorkstationVO {

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "主键（Snowflake），JSON 序列化为字符串避免前端精度丢失")
    private Long id;
    @Schema(description = "工作站编码")
    private String code;
    @Schema(description = "工作站名称")
    private String name;
    @Schema(description = "车间编码")
    private String workshopCode;
    @Schema(description = "状态")
    private String status;
    @Schema(description = "机器人分组")
    private String robotGroup;
}
