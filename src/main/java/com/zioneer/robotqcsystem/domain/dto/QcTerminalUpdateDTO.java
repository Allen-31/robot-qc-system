package com.zioneer.robotqcsystem.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 更新终端配置（2.2.4.3）
 */
@Data
@Schema(description = "更新终端配置请求")
public class QcTerminalUpdateDTO {

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
}
