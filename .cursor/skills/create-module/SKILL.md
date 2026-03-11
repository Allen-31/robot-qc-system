---
name: create-module
description: 创建完整 Spring Boot CRUD 模块（Entity/DTO/Repository 或 Mapper/Service/Controller），遵循本仓库分层与接口规范。
---

# 创建完整 Spring Boot CRUD 模块

当用户要求创建一个模块（例如：用户管理、订单管理、商品管理）时，按本技能生成完整分层代码。

## 何时使用

- 用户说「创建一个 xxx 模块」「加一个 xxx 管理」「新增 xxx 的增删改查」等。
- 需要从零生成一整套 CRUD 能力时。

## 生成结构

按资源名（如 User、Order）生成以下分层，包路径遵循项目：`com.zioneer.robotqcsystem.*`。

| 层级 | 路径 | 文件示例 |
|------|------|----------|
| 实体 | `domain/entity/` | `Xxx.java` |
| DTO | `domain/dto/` | `XxxCreateDTO.java`, `XxxUpdateDTO.java`, `XxxQuery.java`；返回用 `domain/vo/` 下 VO |
| 数据访问 | `mapper/`（MyBatis）或 `repository/`（JPA） | `XxxMapper.java` 或 `XxxRepository.java` |
| 服务 | `service/`、`service/impl/` | `XxxService.java`、`XxxServiceImpl.java` |
| 控制器 | `controller/` 或 `controller/deploy/` | `XxxController.java` |

## 文件清单（以 User 为例）

- **entity**：`User.java`（或与表对应的实体，本项目若用 MyBatis 可为 `domain/entity`）
- **dto**：`UserCreateDTO.java`、`UserUpdateDTO.java`、`UserQuery.java`（列表查询+分页）
- **vo**：列表/详情返回用 VO，如 `UserListVO.java`、`UserDetailVO.java`（与现有项目 VO 命名一致）
- **mapper**：`UserMapper.java`（MyBatis 接口 + 对应 XML，若项目用 JPA 则改为 `UserRepository.java`）
- **service**：`UserService.java`、`UserServiceImpl.java`
- **controller**：`UserController.java`

## Controller 接口约定

- 基础路径：`@RequestMapping("/api/v1/资源复数")`，如 `/api/v1/users`。
- 统一返回类型：**`Result<T>`**（本仓库使用 `Result`，勿用 `ApiResponse`）。
- 必须包含的接口：
  - `GET  /` 或 `GET  /list` — 分页列表，返回 `Result<PageResult<XxxVO>>`
  - `GET  /{id}` — 详情，返回 `Result<XxxVO>`
  - `POST /` — 新增，入参 `@Valid @RequestBody XxxCreateDTO`，返回 `Result<Long>` 或 `Result<Void>`
  - `PUT  /{id}` — 更新，入参 `@Valid @RequestBody XxxUpdateDTO`，返回 `Result<Void>`
  - `DELETE /{id}` — 删除，返回 `Result<Void>`

## 规范对齐

- 遵循 `.cursor/rules` 下 **java-layers**、**java-api-interface**、**java-style** 等规则。
- 分页使用本项目已有 `PageQuery`、`PageResult`（含 `pageNum`、`pageSize`、`list`、`total`、`pages`）。
- 列表查询入参使用 Query DTO，禁止用一长串 `@RequestParam`。
