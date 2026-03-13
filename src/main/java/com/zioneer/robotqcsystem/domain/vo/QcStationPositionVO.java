package com.zioneer.robotqcsystem.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 工位列表项（2.1.2 station-positions：id, stationId, workstationId, name, mapPoint, status）
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "工位信息-业务列表")
public class QcStationPositionVO {

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "主键（Snowflake），JSON 序列化为字符串避免前端精度丢失")
    private Long id;
    @Schema(description = "工位编码")
    private String stationId;
    @Schema(description = "工作站ID")
    private String workstationId;
    @Schema(description = "工位名称")
    private String name;
    @Schema(description = "地图点位")
    private String mapPoint;
    @Schema(description = "状态")
    private String status;
}
