---
name: create-entity
description: 创建 JPA 或 MyBatis 实体类（Entity），含表映射与基础审计字段。
---

# 创建 Entity 实体类

当用户需要新建与数据库表对应的实体类时，按本技能生成。

## 何时使用

- 用户说「创建实体」「建表对应的 Entity」「新增 Xxx 实体」等。
- 在 create-module 流程中生成 entity 时。

## 使用 JPA 时

- 必须包含注解：`@Entity`、`@Table(name = "表名")`、`@Id`、`@GeneratedValue`。
- 表名、字段名使用 **snake_case**（如 `created_at`），与 java-database-review 一致。
- 使用 Lombok：`@Data`，必要时 `@Builder`、`@NoArgsConstructor`、`@AllArgsConstructor`。

## 基础字段（建议）

- `id`：主键，Long 类型。
- `createdAt`：创建时间，`LocalDateTime` 或 `Instant`。
- `updatedAt`：更新时间。
- 若使用软删除：`isDeleted` 或 `deleted`。

## 使用 MyBatis 时（本项目）

- 可不使用 `@Entity`，类为 POJO，与 mapper XML 字段映射一致。
- 仍建议：`@Data`、表名/字段名 snake_case 对应、含 id 与创建/更新时间字段。
- 包路径：`com.zioneer.robotqcsystem.domain.entity`。

## 示例（JPA）

```java
@Entity
@Table(name = "sys_user")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
```
