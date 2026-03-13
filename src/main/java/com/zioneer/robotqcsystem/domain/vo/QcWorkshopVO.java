package com.zioneer.robotqcsystem.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 车间配置视图（2.2.5）
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "车间配置")
public class QcWorkshopVO {

    @Schema(description = "车间编码")
    private String code;
    @Schema(description = "车间名称")
    private String name;
    @Schema(description = "车间地点")
    private String location;
    @Schema(description = "是否启用")
    private Boolean enabled;
}
