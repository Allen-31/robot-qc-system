---
name: create-dto
description: 创建请求/查询 DTO（CreateDTO、UpdateDTO、Query）及返回 VO，含校验注解。
---

# 创建 DTO 与 VO

当用户需要新建入参或返回对象时，按本技能生成，并与本仓库 DTO/VO 规范一致。

## 何时使用

- 用户说「创建 DTO」「建 Xxx 的入参」「新增 XxxQuery」等。
- 在 create-module 或 create-controller 流程中生成 dto/vo 时。

## DTO 类型与用途

| 类型 | 用途 | 示例 |
|------|------|------|
| **CreateDTO** | 新增接口入参 | `UserCreateDTO`，字段带校验 |
| **UpdateDTO** | 更新接口入参 | `UserUpdateDTO`，通常含 id 或由路径传入 |
| **Query** | 列表/查询条件 + 分页 | `UserQuery`，含 keyword、status、pageNum、pageSize 等 |
| **VO** | 返回给前端的视图对象 | `UserListVO`、`UserDetailVO`，只暴露必要字段 |

## 校验规范（CreateDTO / UpdateDTO）

- 必须使用 `javax.validation` 或 `jakarta.validation`：
  - 必填字符串：`@NotBlank`
  - 必填对象/数字：`@NotNull`
  - 格式：`@Email`、`@Size(min, max)`、`@Min`/`@Max`、`@Pattern`
- Controller 入参使用 `@Valid` 或 `@Validated`。

## 包路径

- DTO：`com.zioneer.robotqcsystem.domain.dto`
- VO：`com.zioneer.robotqcsystem.domain.vo`

## CreateDTO 示例

```java
@Data
public class UserCreateDTO {
    @NotBlank(message = "用户名不能为空")
    private String name;
    @NotBlank
    @Email
    private String email;
    @NotNull
    private Integer status;
}
```

## Query 示例（含分页）

```java
@Data
public class UserQuery {
    private String keyword;
    private Integer status;
    private Integer pageNum = 1;
    private Integer pageSize = 10;
}
```

## 禁止

- 禁止用 Entity 直接作为请求体或响应体；必须通过 DTO/VO 分离。
