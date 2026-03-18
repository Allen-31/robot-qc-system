package com.zioneer.robotqcsystem.common.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Unified result status values.
 */
@Getter
@AllArgsConstructor
public enum ResultCode {

    SUCCESS(0, "Success"),
    BAD_REQUEST(400, "Bad request"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not found"),
    INTERNAL_ERROR(500, "Internal server error"),
    BUSINESS_ERROR(600, "Business error");

    private final int code;
    private final String message;
}