package com.zioneer.robotqcsystem.common.logging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zioneer.robotqcsystem.service.ops.OpsApiLogService;
import com.zioneer.robotqcsystem.service.ops.OpsOperationLogService;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
@Order(1)
public class ApiLogAspect {

    private final ObjectMapper objectMapper;
    private final OpsApiLogService apiLogService;
    private final OpsOperationLogService operationLogService;

    @Around("within(@org.springframework.web.bind.annotation.RestController *)")
    public Object logApi(ProceedingJoinPoint joinPoint) throws Throwable {
        String method = "";
        String uri = "";
        String ip = null;
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            method = request.getMethod();
            uri = request.getRequestURI();
            ip = request.getRemoteAddr();
        }

        List<Object> args = new ArrayList<>();
        for (Object arg : joinPoint.getArgs()) {
            if (arg == null) {
                args.add(null);
                continue;
            }
            if (arg instanceof ServletRequest
                    || arg instanceof ServletResponse
                    || arg instanceof BindingResult) {
                continue;
            }
            if (arg instanceof MultipartFile) {
                args.add("[MultipartFile]");
                continue;
            }
            args.add(maskSensitive(arg));
        }
        String argsJson = toJsonSafe(args);
        log.info("API {} {} args={}", method, uri, argsJson);

        long start = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            long cost = System.currentTimeMillis() - start;
            log.info("API {} {} done costMs={}", method, uri, cost);
            recordLogs(method, uri, ip, argsJson, toJsonSafe(result), cost, null);
            return result;
        } catch (Throwable ex) {
            long cost = System.currentTimeMillis() - start;
            log.error("API {} {} failed costMs={} err={}",
                    method, uri, cost, ex.getMessage(), ex);
            recordLogs(method, uri, ip, argsJson, ex.getMessage(), cost, ex.getMessage());
            throw ex;
        }
    }

    private void recordLogs(String method,
                            String uri,
                            String ip,
                            String argsJson,
                            String responseInfo,
                            long costMs,
                            String failReason) {
        String apiName = method + " " + uri;
        String result = failReason == null ? "success" : "failed";
        String requestInfo = apiName + " " + argsJson;
        try {
            apiLogService.record(apiName, result, failReason, (int) costMs, requestInfo, responseInfo);
        } catch (Exception ex) {
            log.warn("API log persist failed: {}", ex.getMessage());
        }
        if (uri != null && uri.startsWith("/api/ops/robots") && !"GET".equalsIgnoreCase(method)) {
            try {
                String user = getCurrentUser();
                operationLogService.record(user, apiName, result, failReason, (int) costMs, ip, requestInfo, responseInfo);
            } catch (Exception ex) {
                log.warn("Operation log persist failed: {}", ex.getMessage());
            }
        }
    }

    private String getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return "anonymous";
        }
        String name = authentication.getName();
        return name != null ? name : "anonymous";
    }

    private Object maskSensitive(Object arg) {
        try {
            Object tree = objectMapper.convertValue(arg, Object.class);
            return maskTree(tree);
        } catch (IllegalArgumentException ex) {
            return arg;
        }
    }

    private Object maskTree(Object node) {
        if (node instanceof Map<?, ?> map) {
            Map<String, Object> out = new LinkedHashMap<>();
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                String key = String.valueOf(entry.getKey());
                Object value = entry.getValue();
                if (isSensitive(key)) {
                    out.put(key, "***");
                } else {
                    out.put(key, maskTree(value));
                }
            }
            return out;
        }
        if (node instanceof List<?> list) {
            List<Object> out = new ArrayList<>();
            for (Object item : list) {
                out.add(maskTree(item));
            }
            return out;
        }
        return node;
    }

    private boolean isSensitive(String key) {
        String k = key == null ? "" : key.toLowerCase(Locale.ROOT);
        return k.contains("password") || k.contains("pwd") || k.contains("secret");
    }

    private String toJsonSafe(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException ex) {
            return String.valueOf(obj);
        }
    }
}
