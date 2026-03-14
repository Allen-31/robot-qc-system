package com.zioneer.robotqcsystem.common.exception;

import com.zioneer.robotqcsystem.common.result.Result;
import com.zioneer.robotqcsystem.common.result.ResultCode;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<Void> handleBusinessException(BusinessException e) {
        log.warn("Business exception: {}", e.getMessage());
        return Result.fail(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<Void> handleValidException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .reduce((a, b) -> a + "; " + b)
                .orElse("Validation failed");
        log.warn("Validation failed: {}", message);
        return Result.fail(ResultCode.BAD_REQUEST.getCode(), message);
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<Void> handleBindException(BindException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .reduce((a, b) -> a + "; " + b)
                .orElse("Bind failed");
        log.warn("Bind failed: {}", message);
        return Result.fail(ResultCode.BAD_REQUEST.getCode(), message);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<Void> handleConstraintViolation(ConstraintViolationException e) {
        String message = e.getConstraintViolations().stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .reduce((a, b) -> a + "; " + b)
                .orElse("Constraint validation failed");
        log.warn("Constraint validation failed: {}", message);
        return Result.fail(ResultCode.BAD_REQUEST.getCode(), message);
    }

    @ExceptionHandler({BadCredentialsException.class, DisabledException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result<Void> handleAuthException(Exception e) {
        log.warn("Authentication failed: {}", e.getMessage());
        return Result.fail(ResultCode.UNAUTHORIZED.getCode(), e.getMessage());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<Void> handleMissingParam(MissingServletRequestParameterException e) {
        log.warn("Missing request parameter: {}", e.getParameterName());
        return Result.fail(ResultCode.BAD_REQUEST.getCode(),
                "Missing required parameter: " + e.getParameterName());
    }

    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<Void> handleDuplicateKey(DuplicateKeyException e) {
        String message = resolveDuplicateKeyMessage(e);
        log.warn("Duplicate key: {}", message);
        return Result.fail(ResultCode.BAD_REQUEST.getCode(), message);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<Void> handleException(Exception e) {
        log.error("Unhandled exception", e);
        return Result.fail(ResultCode.INTERNAL_ERROR.getCode(),
                "Internal server error");
    }

    private String resolveDuplicateKeyMessage(DuplicateKeyException e) {
        String causeMessage = e.getCause() != null ? e.getCause().getMessage() : "";
        if (causeMessage.contains("qc_terminal_code_key")) {
            return "Terminal code already exists";
        }
        if (causeMessage.contains("qc_terminal_sn_key")) {
            return "Terminal serial number already exists";
        }
        if (causeMessage.contains("qc_station") && causeMessage.contains("code")) {
            return "Station code already exists";
        }
        if (causeMessage.contains("qc_workstation") && causeMessage.contains("code")) {
            return "Workstation code already exists";
        }
        if (causeMessage.contains("qc_wire_harness_type") && causeMessage.contains("code")) {
            return "Wire harness type code already exists";
        }
        if (causeMessage.contains("robot") && causeMessage.contains("code")) {
            return "Robot code already exists";
        }
        if (causeMessage.contains("device") && (causeMessage.contains("sn") || causeMessage.contains("code"))) {
            return "Device serial number or code already exists";
        }
        return "Duplicate data";
    }
}
