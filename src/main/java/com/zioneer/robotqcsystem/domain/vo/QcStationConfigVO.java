package com.zioneer.robotqcsystem.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 工位配置视图（2.2.2）
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "工位配置")
public class QcStationConfigVO {

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "主键ID（Snowflake），JSON 序列化为字符串避免前端精度丢失")
    private Long id;
    @Schema(description = "工作站ID")
    private String workstationId;
    @Schema(description = "工位ID")
    private String stationId;
    @Schema(description = "地图点位")
    private String mapPoint;
    @Schema(description = "呼叫盒编码")
    private String callBoxCode;
    @Schema(description = "线束类型")
    private String wireHarnessType;
    @Schema(description = "是否开启检测")
    private Boolean detectionEnabled;
    @Schema(description = "是否启用")
    private Boolean enabled;
}
