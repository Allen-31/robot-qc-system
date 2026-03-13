package com.zioneer.robotqcsystem.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

/**
 * 更新机器人零部件请求。
 */
@Data
@Schema(description = "更新机器人零部件请求")
public class RobotPartUpdateDTO {

    @NotBlank(message = "零部件名称不能为空")
    @Schema(description = "零部件名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "位置")
    private String position;

    @NotBlank(message = "类型不能为空")
    @Schema(description = "类型", requiredMode = Schema.RequiredMode.REQUIRED)
    private String type;

    @Schema(description = "型号")
    private String model;

    @Schema(description = "厂商")
    private String vendor;

    @Schema(description = "供应商")
    private String supplier;

    @Schema(description = "生命周期")
    private String lifecycle;

    @Schema(description = "状态")
    private String status;

    @Schema(description = "备注")
    private String remark;

    @Valid
    @Schema(description = "技术参数列表")
    private List<RobotPartParamDTO> technicalParams;
}
