package com.zioneer.robotqcsystem.common.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 统一响应码
 */
@Getter
@AllArgsConstructor
public enum ResultCode {

    SUCCESS(200, "操作成功"),
    BAD_REQUEST(400, "请求参数错误"),
    UNAUTHORIZED(401, "未授权"),
    FORBIDDEN(403, "无权限"),
    NOT_FOUND(404, "资源不存在"),
    INTERNAL_ERROR(500, "服务器内部错误"),
    BUSINESS_ERROR(600, "业务异常");

    private final int code;
    private final String message;
}
