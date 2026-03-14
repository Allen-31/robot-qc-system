package com.zioneer.robotqcsystem.domain.dto;

import com.zioneer.robotqcsystem.domain.entity.OperationPackagePart;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

/**
 * 安装包更新请求。
 */
@Data
@Schema(description = "安装包更新请求")
public class OperationPackageUpdateDTO {

    @Size(max = 255, message = "安装包名称长度不能超过255")
    @Schema(description = "安装包名称/显示名")
    private String name;

    @Size(max = 1024, message = "描述长度不能超过1024")
    @Schema(description = "描述")
    private String description;

    @Valid
    @Size(max = 200, message = "目标部件数量不能超过200")
    @Schema(description = "目标部件列表")
    private List<OperationPackagePart> targetParts;
}
