# 机器人质检系统 - 后端 API 接口文档（给前端）

**文档版本**: 1.0  
**基础路径**: `http://{host}:8080/api`（开发环境如 `http://localhost:8080/api`）  
**认证方式**: 除登录接口外，请求头需携带 `Authorization: Bearer <token>`

---

## 一、通用约定

### 1.1 统一响应格式

所有接口均返回 JSON，结构为：

```json
{
  "code": 200,
  "message": "操作成功",
  "data": { ... }
}
```

| 字段    | 类型   | 说明                    |
|---------|--------|-------------------------|
| code    | number | 状态码，200 表示成功    |
| message | string | 提示信息                |
| data    | object | 业务数据，无数据时为 null |

- **成功**：`code === 200`，业务数据在 `data` 中。
- **失败**：`code !== 200`，`message` 为错误说明，`data` 通常为 null。

### 1.2 分页结构

分页接口的 `data` 结构：

```json
{
  "list": [ ... ],
  "total": 100,
  "pageNum": 1,
  "pageSize": 20,
  "pages": 5
}
```

### 1.3 认证

- 登录成功后，响应 `data.token` 即为 JWT。
- 调用除「登录」以外的接口时，在请求头添加：  
  `Authorization: Bearer <token>`
- Token 过期或未携带会返回 `401`，前端需跳转登录页。

### 1.4 时间格式

- 日期时间：`yyyy-MM-dd HH:mm:ss`
- 仅日期：`yyyy-MM-dd`

---

## 二、登录与认证

### 2.1 用户登录

- **URL**: `POST /api/auth/login`
- **认证**: 不需要
- **Content-Type**: `application/json`

**请求体**

| 字段      | 类型    | 必填 | 说明           |
|-----------|---------|------|----------------|
| username  | string  | 是   | 用户编码       |
| password  | string  | 是   | 密码           |
| remember  | boolean | 否   | 记住登录，延长 token 有效期 |

**请求示例**

```json
{
  "username": "admin",
  "password": "admin123",
  "remember": true
}
```

**成功响应**（200）

```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiJ9...",
    "user": {
      "code": "admin",
      "displayName": "管理员",
      "roles": ["admin"]
    }
  }
}
```

**失败**：返回 HTTP 401，body 中 `code: 401`，`message` 为「用户名或密码错误」或「账号已禁用」。

---

### 2.2 登出

- **URL**: `POST /api/auth/logout`
- **认证**: 需要 Bearer Token

**成功响应**（200）

```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "success": true
  }
}
```

---

### 2.3 获取当前用户信息

- **URL**: `GET /api/auth/me`
- **认证**: 需要 Bearer Token

**成功响应**（200）

```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "code": "admin",
    "displayName": "管理员",
    "roles": ["admin"]
  }
}
```

用于前端展示当前用户、菜单/权限控制。

---

## 三、用户管理（部署配置 - 用户）

**基础路径**: `/api/deploy/users`  
**认证**: 所有接口均需要 Bearer Token

### 3.1 用户分页列表

- **URL**: `GET /api/deploy/users`

**Query 参数**

| 参数      | 类型   | 必填 | 说明                          |
|-----------|--------|------|-------------------------------|
| page      | number | 否   | 页码，从 1 开始，默认 1        |
| pageSize  | number | 否   | 每页条数，默认 20              |
| keyword   | string | 否   | 关键词（编码/姓名/手机/邮箱）   |
| role      | string | 否   | 角色编码筛选                   |
| status    | string | 否   | 状态：enabled / disabled       |

**成功响应**（200）

```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "list": [
      {
        "code": "admin",
        "name": "管理员",
        "phone": null,
        "email": null,
        "status": "enabled",
        "lastLoginAt": "2026-03-10 12:00:00",
        "roles": ["admin"]
      }
    ],
    "total": 1,
    "pageNum": 1,
    "pageSize": 20,
    "pages": 1
  }
}
```

---

### 3.2 新增用户

- **URL**: `POST /api/deploy/users`
- **Content-Type**: `application/json`

**请求体**

| 字段     | 类型   | 必填 | 说明                    |
|----------|--------|------|-------------------------|
| code     | string | 是   | 用户编码，唯一           |
| name     | string | 是   | 姓名                     |
| phone    | string | 否   | 手机号                   |
| email    | string | 否   | 邮箱                     |
| status   | string | 否   | enabled / disabled，默认 enabled |
| roles    | string[] | 否 | 角色编码数组             |
| password | string | 是   | 密码                     |

**成功响应**（200）：`data` 为 null。

---

### 3.3 更新用户

- **URL**: `PUT /api/deploy/users/{code}`
- **Content-Type**: `application/json`
- **路径参数**: `code` — 用户编码

**请求体**（不包含密码）

| 字段   | 类型     | 必填 | 说明        |
|--------|----------|------|-------------|
| name   | string   | 是   | 姓名        |
| phone  | string   | 否   | 手机号      |
| email  | string   | 否   | 邮箱        |
| status | string   | 否   | enabled/disabled |
| roles  | string[] | 否   | 角色编码数组 |

**成功响应**（200）：`data` 为 null。

---

### 3.4 删除用户

- **URL**: `DELETE /api/deploy/users/{code}`
- **路径参数**: `code` — 用户编码

**成功响应**（200）：`data` 为 null。

---

### 3.5 更新用户角色

- **URL**: `PUT /api/deploy/users/{code}/roles`
- **Content-Type**: `application/json`
- **路径参数**: `code` — 用户编码

**请求体**

```json
{
  "roles": ["admin", "operator"]
}
```

**成功响应**（200）：`data` 为 null。

---

### 3.6 修改密码

- **URL**: `PUT /api/deploy/users/{code}/password`
- **Content-Type**: `application/json`
- **路径参数**: `code` — 用户编码

**请求体**

| 字段         | 类型   | 必填 | 说明   |
|--------------|--------|------|--------|
| oldPassword  | string | 是   | 原密码 |
| newPassword  | string | 是   | 新密码 |

**成功响应**（200）

```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "success": true
  }
}
```

**失败（原密码错误）**：HTTP 200，`data` 为：

```json
{
  "success": false,
  "error": "old_password_invalid"
}
```

前端可根据 `data.success === false` 且 `data.error === "old_password_invalid"` 提示用户。

---

## 四、角色管理（部署配置 - 角色）

**基础路径**: `/api/deploy/roles`  
**认证**: 所有接口均需要 Bearer Token

### 4.1 角色列表

- **URL**: `GET /api/deploy/roles`

**Query 参数**

| 参数    | 类型   | 必填 | 说明（关键词：编码/名称/描述） |
|---------|--------|------|--------------------------------|
| keyword | string | 否   | 关键词                         |

**成功响应**（200）

```json
{
  "code": 200,
  "message": "操作成功",
  "data": [
    {
      "code": "admin",
      "name": "超级管理员",
      "description": "系统默认超级管理员角色",
      "memberCount": 1,
      "updatedAt": "2026-03-10 12:00:00"
    }
  ]
}
```

---

### 4.2 新增角色

- **URL**: `POST /api/deploy/roles`
- **Content-Type**: `application/json`

**请求体**

| 字段        | 类型   | 必填 | 说明   |
|-------------|--------|------|--------|
| code        | string | 是   | 角色编码，唯一 |
| name        | string | 是   | 角色名称     |
| description | string | 否   | 描述         |

**成功响应**（200）：`data` 为 null。  
**失败**：如编码重复，`code !== 200`，`message` 如「角色编码已存在: xxx」。

---

### 4.3 更新角色

- **URL**: `PUT /api/deploy/roles/{code}`
- **Content-Type**: `application/json`
- **路径参数**: `code` — 角色编码

**请求体**

| 字段        | 类型   | 必填 | 说明   |
|-------------|--------|------|--------|
| name        | string | 是   | 角色名称 |
| description | string | 否   | 描述     |

**成功响应**（200）：`data` 为 null。

---

### 4.4 删除角色

- **URL**: `DELETE /api/deploy/roles/{code}`
- **路径参数**: `code` — 角色编码

**成功响应**（200）：`data` 为 null。  
**失败**：若为最后一个角色，`message` 会提示「至少保留一个角色」等，前端可按需解析。

---

### 4.5 获取角色权限配置

- **URL**: `GET /api/deploy/roles/{code}/permissions`
- **路径参数**: `code` — 角色编码

**成功响应**（200）

```json
{
  "code": 200,
  "message": "操作成功",
  "data": [
    {
      "menuKey": "deploy.user",
      "actions": ["display", "create", "edit", "delete", "view"]
    },
    {
      "menuKey": "deploy.role",
      "actions": ["display", "view"]
    }
  ]
}
```

`menuKey` 与前端菜单/路由 key 对应，`actions` 为勾选的动作（如 display、create、edit、delete、view）。

---

### 4.6 保存角色权限配置

- **URL**: `PUT /api/deploy/roles/{code}/permissions`
- **Content-Type**: `application/json`
- **路径参数**: `code` — 角色编码

**请求体**

```json
{
  "permissions": [
    {
      "menuKey": "deploy.user",
      "actions": ["display", "create", "edit", "delete", "view"]
    },
    {
      "menuKey": "deploy.role",
      "actions": ["display", "view"]
    }
  ]
}
```

**成功响应**（200）：`data` 为 null。

---

## 五、菜单管理（部署配置 - 菜单）

**基础路径**: `/api/deploy/menus`  
**认证**: 所有接口均需要 Bearer Token

### 5.1 获取菜单树

- **URL**: `GET /api/deploy/menus`

**Query 参数**

| 参数   | 类型    | 必填 | 说明 |
|--------|---------|------|------|
| status | string  | 否   | `enabled` / `disabled`，不传返回全部 |
| tree   | boolean | 否   | `true` 树形（默认），`false` 扁平列表 |

**成功响应**（200）：`data` 为树形数组，每节点含 `id`、`code`、`nameKey`、`path`、`parentId`、`sortOrder`、`icon`、`permission`、`status`、`children`（子节点数组）。

---

### 5.2 新增菜单节点

- **URL**: `POST /api/deploy/menus`
- **Content-Type**: `application/json`

**请求体**

| 字段      | 类型   | 必填 | 说明 |
|-----------|--------|------|------|
| code      | string | 是   | 业务编码，全局唯一 |
| nameKey   | string | 是   | 前端 i18n key |
| path      | string | 否   | 路由路径，目录可空 |
| parentId  | number \| null | 否 | 父节点 id，null 表示一级 |
| sortOrder | number | 否   | 同层排序，默认 0 |
| icon      | string | 否   | 图标名 |
| permission| string | 否   | 权限标识 |
| status    | string | 否   | `enabled` / `disabled`，默认 `enabled` |

**成功响应**（200）：`data` 为新节点主键 `id`。  
**失败**：如 `code` 重复，`message` 如「菜单编码已存在: xxx」。

---

### 5.3 更新菜单节点

- **URL**: `PUT /api/deploy/menus/{id}`
- **路径参数**: `id` — 菜单主键
- **Content-Type**: `application/json`

**请求体**（均为可选，传则更新）

| 字段      | 类型   | 说明 |
|-----------|--------|------|
| nameKey   | string | i18n key |
| path      | string | 路由路径 |
| sortOrder | number | 同层排序 |
| icon      | string | 图标名 |
| permission| string | 权限标识 |
| status    | string | `enabled` / `disabled` |

**成功响应**（200）：`data` 为 null。

---

### 5.4 删除菜单节点

- **URL**: `DELETE /api/deploy/menus/{id}`
- **路径参数**: `id` — 菜单主键

**成功响应**（200）：`data` 为 null。  
**失败**：若存在子菜单，`message` 为「存在子菜单，无法删除」。

---

### 5.5 批量更新排序

- **URL**: `PUT /api/deploy/menus/order`
- **Content-Type**: `application/json`

**请求体**

```json
{
  "orders": [
    { "id": 2, "sortOrder": 1 },
    { "id": 3, "sortOrder": 2 }
  ]
}
```

**成功响应**（200）：`data` 为 null。

---

## 六、机器人管理

**基础路径**: `/api/robots`  
**认证**: 所有接口均需要 Bearer Token

### 6.1 分页查询机器人列表

- **URL**: `GET /api/robots`

**Query 参数**（与分页结构一致，可直接用 `UserQuery` 风格对象传参）

| 参数       | 类型   | 必填 | 说明 |
|------------|--------|------|------|
| robotCode  | string | 否   | 机器人编码（模糊） |
| status     | string | 否   | 状态 |
| pageNum    | number | 否   | 页码，默认 1 |
| pageSize   | number | 否   | 每页条数，默认 20 |

**成功响应**（200）：`data` 为分页结构，`list` 中每项为机器人对象（`id`、`robotCode`、`robotName`、`model`、`status`、`location`、`lastInspectionAt`）。

### 6.2 机器人详情

- **URL**: `GET /api/robots/{id}`
- **路径参数**: `id` — 机器人主键

**成功响应**（200）：`data` 为单条机器人对象。

### 6.3 新增机器人

- **URL**: `POST /api/robots`
- **Content-Type**: `application/json`

**请求体**

| 字段             | 类型   | 必填 | 说明 |
|------------------|--------|------|------|
| robotCode        | string | 是   | 机器人编码，唯一 |
| robotName        | string | 是   | 机器人名称 |
| model            | string | 否   | 型号 |
| status           | string | 是   | 状态 |
| location         | string | 否   | 位置 |
| lastInspectionAt | string | 否   | 最近巡检时间（日期时间格式） |

**成功响应**（200）：`data` 为新机器人主键 `id`。

### 6.4 更新机器人

- **URL**: `PUT /api/robots/{id}`
- **路径参数**: `id` — 机器人主键
- **Content-Type**: `application/json`

**请求体**

| 字段             | 类型   | 必填 | 说明 |
|------------------|--------|------|------|
| robotName        | string | 是   | 机器人名称 |
| model            | string | 否   | 型号 |
| status           | string | 是   | 状态 |
| location         | string | 否   | 位置 |
| lastInspectionAt | string | 否   | 最近巡检时间 |

**成功响应**（200）：`data` 为 null。

### 6.5 删除机器人

- **URL**: `DELETE /api/robots/{id}`
- **路径参数**: `id` — 机器人主键

**成功响应**（200）：`data` 为 null。

---

## 七、错误码说明

| code | 说明       |
|------|------------|
| 200  | 成功       |
| 400  | 请求参数错误（校验失败等） |
| 401  | 未授权（未登录或 token 无效/过期） |
| 403  | 无权限     |
| 404  | 资源不存在 |
| 500  | 服务器内部错误 |
| 600  | 业务异常（如「用户编码已存在」） |

错误时以 `message` 作为提示文案展示即可。

---

## 八、在线文档与 OpenAPI

- **Swagger UI（在线调试）**  
  服务启动后访问：`http://{host}:8080/swagger-ui.html`  
  可在页面中直接调用接口、查看请求/响应结构。

- **OpenAPI 3.0 JSON（供前端代码生成或对接）**  
  `http://{host}:8080/v3/api-docs`  
  前端可用该地址做接口联调或生成 TypeScript 类型/请求封装。

---

**文档位置**：本文件位于项目 `docs/API接口文档.md`，可直接发给前端使用。后续接口变更会同步更新此文档与 Swagger。
