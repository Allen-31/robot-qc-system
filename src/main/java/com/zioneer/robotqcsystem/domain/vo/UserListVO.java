package com.zioneer.robotqcsystem.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户列表项 VO（不包含密码）
 */
@Data
@Schema(description = "用户列表项")
public class UserListVO {

    @Schema(description = "用户编码")
    private String code;
    @Schema(description = "姓名")
    private String name;
    @Schema(description = "手机号")
    private String phone;
    @Schema(description = "邮箱")
    private String email;
    @Schema(description = "状态：enabled/disabled")
    private String status;
    @Schema(description = "最后登录时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastLoginAt;
    @Schema(description = "角色名称列表")
    private List<String> roles;
}
