---
name: create-exception-handler
description: 创建全局异常处理（@RestControllerAdvice），统一返回 Result 结构。
---

# 创建全局异常处理

当用户需要统一处理接口异常并返回统一结构时，按本技能生成或对齐现有实现。

## 何时使用

- 用户说「创建全局异常处理」「统一异常返回」「GlobalExceptionHandler」等。
- 新项目从零搭建时。

## 本仓库已有实现

本项目已使用 **`GlobalExceptionHandler`**（`@RestControllerAdvice`），统一将异常转换为 **`Result`** 返回，勿重复创建。

- 业务异常：`BusinessException` → `Result.fail(code, message)`。
- 参数校验：`MethodArgumentNotValidException` 等 → 返回 400 及校验信息。
- 其他：可统一返回 500 及安全提示信息，不把堆栈暴露给前端。

若用户要求「新建全局异常处理」，可先检查是否已有 `GlobalExceptionHandler`，有则在其上扩展；无则按下面模板生成。

## 若需从零生成（新项目）

- 使用 `@RestControllerAdvice`（或 `@ControllerAdvice`）。
- 使用 `@ExceptionHandler` 处理各类异常，返回类型为 **`Result<?>`** 或 `ApiResponse<?>`（与项目统一响应一致）。

示例骨架：

```java
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public Result<?> handleBusiness(BusinessException e) {
        return Result.fail(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> handleValid(MethodArgumentNotValidException e) {
        // 解析 @Valid 校验错误，返回 400 + 字段错误信息
        return Result.fail(400, "参数校验失败");
    }

    @ExceptionHandler(Exception.class)
    public Result<?> handleOther(Exception e) {
        log.error("unexpected error", e);
        return Result.fail(500, "系统繁忙");
    }
}
```

- 业务异常使用自定义 `BusinessException`，携带 code 与 message。
- 禁止空 catch；记录日志时包含必要上下文与业务 ID。
