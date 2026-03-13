package com.zioneer.robotqcsystem.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 工位视图对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "工位信息")
public class QcStationVO {

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "主键（Snowflake），JSON 序列化为字符串避免前端精度丢失")
    private Long id;
    @Schema(description = "工位编码")
    private String code;
    @Schema(description = "工位名称")
    private String name;
    @Schema(description = "工作站ID")
    private Long workstationId;
    @Schema(description = "描述")
    private String description;
    @Schema(description = "排序")
    private Integer sortOrder;
    @Schema(description = "创建时间")
    private LocalDateTime createdAt;
    @Schema(description = "更新时间")
    private LocalDateTime updatedAt;
}
