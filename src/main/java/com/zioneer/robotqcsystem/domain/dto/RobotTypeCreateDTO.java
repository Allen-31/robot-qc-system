package com.zioneer.robotqcsystem.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

/**
 * 新增机器人类型请求。
 */
@Data
@Schema(description = "新增机器人类型请求")
public class RobotTypeCreateDTO {

    @NotBlank(message = "类型编号不能为空")
    @Schema(description = "类型编号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String typeNo;

    @NotBlank(message = "类型名称不能为空")
    @Schema(description = "类型名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String typeName;

    @Schema(description = "二维图文件名")
    private String image2d;

    @Schema(description = "二维图数据（Base64或URL）")
    private String image2dData;

    @Schema(description = "状态")
    private String status;

    @Valid
    @Schema(description = "标注点列表")
    private List<RobotTypePointDTO> points;
}
