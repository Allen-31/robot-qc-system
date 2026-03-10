package com.zioneer.robotqcsystem.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 更新用户角色请求
 */
@Data
@Schema(description = "更新用户角色请求")
public class UserRolesUpdateDTO {

    @Schema(description = "角色编码列表")
    private List<String> roles;
}
