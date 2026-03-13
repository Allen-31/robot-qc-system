package com.zioneer.robotqcsystem.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 机器人类型视图。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "机器人类型视图")
public class RobotTypeVO {

    @Schema(description = "主键")
    private Long id;

    @Schema(description = "类型编号")
    private String typeNo;

    @Schema(description = "类型名称")
    private String typeName;

    @Schema(description = "二维图文件名")
    private String image2d;

    @Schema(description = "二维图数据（Base64或URL）")
    private String image2dData;

    @Schema(description = "零部件数量")
    private Integer partsCount;

    @Schema(description = "状态")
    private String status;

    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

    @Schema(description = "更新时间")
    private LocalDateTime updatedAt;

    @Schema(description = "标注点列表")
    private List<RobotTypePointVO> points;
}
