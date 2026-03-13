package com.zioneer.robotqcsystem.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 终端配置视图（2.2.4）
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "终端配置")
public class QcTerminalVO {

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "主键（Snowflake），JSON 序列化为字符串避免前端 Number 精度丢失")
    private Long id;
    @Schema(description = "终端编码")
    private String code;
    @Schema(description = "终端序列号")
    private String sn;
    @Schema(description = "终端类型")
    private String terminalType;
    @Schema(description = "终端IP")
    private String terminalIp;
    @Schema(description = "所属工作站ID")
    private String workstationId;
    @Schema(description = "绑定工位ID列表")
    private List<String> boundStationIds;
    @Schema(description = "是否在线")
    private Boolean online;
    @Schema(description = "当前用户")
    private String currentUser;
}
