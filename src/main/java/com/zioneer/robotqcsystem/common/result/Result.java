package com.zioneer.robotqcsystem.common.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Unified API response wrapper.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Unified response")
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Status")
    private int status;

    @Schema(description = "Message")
    private String message;

    @Schema(description = "Data")
    private T data;

    public static <T> Result<T> ok() {
        return Result.<T>builder()
                .status(ResultCode.SUCCESS.getCode())
                .message(ResultCode.SUCCESS.getMessage())
                .build();
    }

    public static <T> Result<T> ok(T data) {
        return Result.<T>builder()
                .status(ResultCode.SUCCESS.getCode())
                .message(ResultCode.SUCCESS.getMessage())
                .data(data)
                .build();
    }

    public static <T> Result<T> ok(String message, T data) {
        return Result.<T>builder()
                .status(ResultCode.SUCCESS.getCode())
                .message(message)
                .data(data)
                .build();
    }

    public static <T> Result<T> fail(ResultCode resultCode) {
        return Result.<T>builder()
                .status(resultCode.getCode())
                .message(resultCode.getMessage())
                .build();
    }

    public static <T> Result<T> fail(int status, String message) {
        return Result.<T>builder()
                .status(status)
                .message(message)
                .build();
    }

    public static <T> Result<T> fail(String message) {
        return fail(ResultCode.BUSINESS_ERROR.getCode(), message);
    }
}