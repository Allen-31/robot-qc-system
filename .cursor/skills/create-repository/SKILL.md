---
name: create-repository
description: 创建数据访问层（JPA Repository 或 MyBatis Mapper）。
---

# 创建 Repository / Mapper

当用户需要新建数据访问层时，按项目技术栈生成 JPA Repository 或 MyBatis Mapper。

## 何时使用

- 用户说「创建 Repository」「建 Mapper」「新增 Xxx 数据访问」等。
- 在 create-module 流程中生成数据访问层时。

## 本项目（MyBatis）

- 生成 **Mapper 接口**，放在 `mapper/` 包下，如 `com.zioneer.robotqcsystem.mapper.XxxMapper`。
- 方法名与 SQL 语义一致：`selectById`、`selectPage`、`insert`、`updateById`、`deleteById` 等。
- 对应 XML 写在 `src/main/resources/mapper/` 下，如 `XxxMapper.xml`。
- 分页查询使用 MyBatis 分页插件或 `PageHelper`，与现有项目一致。

## 若使用 JPA

- 生成接口：`extends JpaRepository<Entity, IdType>`。
- 示例：`public interface UserRepository extends JpaRepository<User, Long> { }`
- 自定义方法按 Spring Data 命名规范或 `@Query`。

## 规范

- 只做数据访问，**禁止**在 Mapper/Repository 中写业务逻辑。
- 列表查询必须支持分页，避免 `SELECT *`，按需列出字段。
