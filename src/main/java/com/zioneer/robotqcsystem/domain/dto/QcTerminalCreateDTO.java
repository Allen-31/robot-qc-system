package com.zioneer.robotqcsystem.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

/**
 * 新增终端配置（2.2.4.2）
 */
@Data
@Schema(description = "新增终端配置请求")
public class QcTerminalCreateDTO {

    @NotBlank(message = "终端编码不能为空")
    @Schema(description = "终端编码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String code;

    @NotBlank(message = "终端序列号不能为空")
    @Schema(description = "终端序列号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String sn;

    @NotBlank(message = "终端类型不能为空")
    @Schema(description = "终端类型", requiredMode = Schema.RequiredMode.REQUIRED)
    private String terminalType;

    @NotBlank(message = "终端IP不能为空")
    @Schema(description = "终端IP", requiredMode = Schema.RequiredMode.REQUIRED)
    private String terminalIp;

    @NotBlank(message = "所属工作站ID不能为空")
    @Schema(description = "所属工作站ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String workstationId;

    @Schema(description = "绑定工位ID列表")
    private List<String> boundStationIds;
}
