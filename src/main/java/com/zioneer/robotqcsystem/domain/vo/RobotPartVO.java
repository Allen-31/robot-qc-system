package com.zioneer.robotqcsystem.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 机器人零部件视图。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "机器人零部件视图")
public class RobotPartVO {

    @Schema(description = "主键")
    private Long id;

    @Schema(description = "零部件编码")
    private String partNo;

    @Schema(description = "零部件名称")
    private String name;

    @Schema(description = "位置")
    private String position;

    @Schema(description = "类型")
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

    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

    @Schema(description = "更新时间")
    private LocalDateTime updatedAt;

    @Schema(description = "技术参数列表")
    private List<RobotPartParamVO> technicalParams;
}
