package com.zioneer.robotqcsystem.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 修改密码接口响应（与文档约定一致）
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "修改密码结果")
public class PasswordUpdateResultVO {

    @Schema(description = "是否成功")
    private Boolean success;
    @Schema(description = "失败时的错误码，如 old_password_invalid")
    private String error;
}
