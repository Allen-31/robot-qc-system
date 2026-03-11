---
name: create-service
description: 创建 Service 接口与实现类，包含 list/getById/create/update/delete 等 CRUD 方法。
---

# 创建 Service 层

当用户需要新建业务服务层时，按本技能生成接口 + 实现类，遵循本仓库分层规范。

## 何时使用

- 用户说「创建 Service」「建 Xxx 业务层」「新增 XxxService」等。
- 在 create-module 流程中生成 service 时。

## 生成内容

- **接口**：`XxxService.java`，放在 `service/` 包下。
- **实现类**：`XxxServiceImpl.java`，放在 `service/impl/` 包下，实现 `XxxService`。

## 方法约定（与 Controller 对应）

| 方法 | 含义 | 典型签名 |
|------|------|----------|
| `page` / `list` | 分页列表 | `PageResult<XxxVO> page(XxxQuery query)` 或带分页参数 |
| `getById` / `detail` | 单条详情 | `XxxVO getById(Long id)` |
| `create` | 新增 | `Long create(XxxCreateDTO dto)` 或 `void create(...)` |
| `update` | 更新 | `void update(Long id, XxxUpdateDTO dto)` |
| `delete` | 删除 | `void delete(Long id)` 或按业务主键 |

## 实现类规范

- 注入 Mapper 或 Repository，**禁止**在 Service 中写 SQL。
- 事务：在实现类方法上加 `@Transactional(rollbackFor = Exception.class)`（写操作）。
- 流程：校验(validate) → 转换(convert) → 调用 Mapper/Repository → 返回。
- 业务异常使用 `BusinessException`，由全局异常处理器统一返回 `Result.fail`。

## 包路径

- 接口：`com.zioneer.robotqcsystem.service`
- 实现：`com.zioneer.robotqcsystem.service.impl`
