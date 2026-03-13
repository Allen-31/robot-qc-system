package com.zioneer.robotqcsystem.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 更新车间配置（2.2.5.3）
 */
@Data
@Schema(description = "更新车间配置请求")
public class QcWorkshopUpdateDTO {

    @NotBlank(message = "车间名称不能为空")
    @Schema(description = "车间名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "车间地点")
    private String location;

    @Schema(description = "是否启用")
    private Boolean enabled;
}
