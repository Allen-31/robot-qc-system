package com.zioneer.robotqcsystem.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 设备视图对象（3.3.2.1 列表与详情，id 与 code 对齐前端示例）
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "设备信息")
public class DeployDeviceVO {

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "设备主键ID（Snowflake），JSON 序列化为字符串避免前端精度丢失")
    private Long id;

    @Schema(description = "设备编码", example = "DEV-001")
    private String code;

    @Schema(description = "设备名称", example = "呼叫盒A-01")
    private String name;

    @Schema(description = "设备类型", example = "callBox")
    private String type;

    @Schema(description = "所属分组", example = "总装一线")
    private String group;

    @Schema(description = "地图编码", example = "MAP-001")
    private String mapCode;

    @Schema(description = "状态", example = "online")
    private String status;

    @Schema(description = "IP 地址", example = "10.10.5.101")
    private String ip;
}
