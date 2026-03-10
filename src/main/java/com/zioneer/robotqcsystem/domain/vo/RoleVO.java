package com.zioneer.robotqcsystem.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 角色 VO
 */
@Data
@Schema(description = "角色信息")
public class RoleVO {

    @Schema(description = "角色编码")
    private String code;
    @Schema(description = "角色名称")
    private String name;
    @Schema(description = "描述")
    private String description;
    @Schema(description = "成员数量")
    private Integer memberCount;
    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
}
