package com.zioneer.robotqcsystem.domain.dto;

import com.zioneer.robotqcsystem.common.page.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户分页查询条件
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "用户查询条件")
public class UserQuery extends PageQuery {

    @Schema(description = "关键词：编码/姓名/手机/邮箱/角色")
    private String keyword;

    @Schema(description = "角色编码")
    private String role;

    @Schema(description = "状态：enabled/disabled")
    private String status;
}
