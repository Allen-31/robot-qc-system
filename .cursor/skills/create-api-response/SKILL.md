---
name: create-api-response
description: 创建或复用统一 API 响应结构（code/message/data），本仓库已使用 Result<T>。
---

# 统一 API 响应结构

当用户需要统一接口返回格式时，按本技能生成或对齐现有实现。

## 何时使用

- 用户说「创建统一返回」「建 ApiResponse」「统一响应结构」等。
- 新项目从零搭建时。

## 标准结构（企业常见）

- **code**：业务或 HTTP 状态码（0/200 表示成功）。
- **message**：提示信息（如 "success"、错误说明）。
- **data**：业务数据，泛型 `T`，可为 null。

## 本仓库已有实现

本项目已使用 **`Result<T>`**（`com.zioneer.robotqcsystem.common.result.Result`），无需再建 `ApiResponse`。

- 成功：`Result.ok()`、`Result.ok(data)`
- 失败：`Result.fail(code, message)`、`Result.fail(ResultCode.xxx)`
- 字段：`code`、`message`、`data`

若用户明确要求「新建一个 ApiResponse」，可生成与 `Result` 同结构的类并说明「项目中已有 Result，建议直接使用 Result 以保持一致」。

## 若需从零生成（新项目）

```java
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private int code;
    private String message;
    private T data;

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
            .code(0)
            .message("success")
            .data(data)
            .build();
    }

    public static <T> ApiResponse<T> error(int code, String message) {
        return ApiResponse.<T>builder()
            .code(code)
            .message(message)
            .data(null)
            .build();
    }
}
```

Controller 统一返回 `ApiResponse<T>` 或本仓库的 `Result<T>`。
