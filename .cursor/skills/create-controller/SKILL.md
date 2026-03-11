---
name: create-controller
description: 创建 REST Controller，提供 list/getById/create/update/delete 接口，统一返回 Result<T>。
---

# 创建 REST Controller

当用户需要新建接口层时，按本技能生成 Controller，遵循本仓库接口规范（java-api-interface）。

## 何时使用

- 用户说「创建 Controller」「建 Xxx 接口」「新增 Xxx API」等。
- 在 create-module 流程中生成 controller 时。

## 注解与路径

- `@RestController`
- `@RequestMapping("/api/v1/资源复数")`，如 `/api/v1/users`
- 使用 Swagger/OpenAPI：`@Tag(name = "xxx")`、`@Operation(summary = "xxx")` 在类与方法上

## 必须提供的接口

| 方法 | 路径 | 说明 | 返回类型 |
|------|------|------|----------|
| GET | `/` 或 `/list` | 分页列表 | `Result<PageResult<XxxVO>>` |
| GET | `/{id}` | 详情 | `Result<XxxVO>` |
| POST | `/` | 新增 | `Result<Long>` 或 `Result<Void>` |
| PUT | `/{id}` | 更新 | `Result<Void>` |
| DELETE | `/{id}` | 删除 | `Result<Void>` |

## 入参规范

- 列表：使用 Query DTO，如 `@Valid UserQuery query`，不要一长串 `@RequestParam`。
- 新增/更新：`@Valid @RequestBody XxxCreateDTO` / `XxxUpdateDTO`。
- 路径变量：`@PathVariable Long id`（或业务主键类型）。

## 返回类型

- **统一使用 `Result<T>`**（本仓库已有，勿用 `ApiResponse`）。
- 成功：`return Result.ok(data)` 或 `Result.ok()`；失败由全局异常处理返回 `Result.fail`。
- Controller 内**禁止**写业务逻辑，只做接收、校验、调用 Service、返回。

## 包路径

- `com.zioneer.robotqcsystem.controller` 或 `controller.deploy`（按现有模块划分）
