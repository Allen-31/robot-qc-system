# Postman 测试说明

## 1. 启动服务

确保 PostgreSQL、Redis 已启动，在项目根目录执行：

```bash
.\gradlew.bat bootRun
```

服务默认端口 **8080**，基础地址：`http://localhost:8080`

---

## 2. 无需登录的接口

### 健康检查

- **方法**: GET  
- **URL**: `http://localhost:8080/api/health`  
- **说明**: 不带头即可，用于确认服务已启动。

### 登录（获取 Token）

- **方法**: POST  
- **URL**: `http://localhost:8080/api/auth/login`  
- **Headers**: `Content-Type: application/json`  
- **Body**（raw JSON）:

```json
{
  "username": "admin",
  "password": "admin123",
  "remember": true
}
```

- **成功响应示例**（200）:

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

- **复制**响应里 `data.token` 的值，后面所有需认证的接口都要用到。

---

## 3. 需要登录的接口（带 Token）

以下接口都要在 **Headers** 里加一条：

| Key          | Value              |
|-------------|--------------------|
| Authorization | Bearer 这里粘贴上面拿到的 token |

（注意：`Bearer` 和 token 之间有一个空格。）

### 3.1 获取当前用户

- **方法**: GET  
- **URL**: `http://localhost:8080/api/auth/me`  
- **Headers**: `Authorization: Bearer <你的token>`

### 3.2 登出

- **方法**: POST  
- **URL**: `http://localhost:8080/api/auth/logout`  
- **Headers**: `Authorization: Bearer <你的token>`

### 3.3 用户管理

| 操作     | 方法 | URL |
|----------|------|-----|
| 用户分页 | GET  | `http://localhost:8080/api/deploy/users?page=1&pageSize=10` |
| 新增用户 | POST | `http://localhost:8080/api/deploy/users` |
| 更新用户 | PUT  | `http://localhost:8080/api/deploy/users/用户编码` |
| 删除用户 | DELETE | `http://localhost:8080/api/deploy/users/用户编码` |
| 更新角色 | PUT  | `http://localhost:8080/api/deploy/users/用户编码/roles` |
| 修改密码 | PUT  | `http://localhost:8080/api/deploy/users/用户编码/password` |

**新增用户 Body 示例**（POST `/api/deploy/users`）:

```json
{
  "code": "test01",
  "name": "测试用户",
  "phone": "13800138000",
  "email": "test@example.com",
  "status": "enabled",
  "roles": ["admin"],
  "password": "Test123456"
}
```

**更新用户 Body 示例**（PUT `/api/deploy/users/test01`，不含密码）:

```json
{
  "name": "测试用户改名",
  "phone": "13900139000",
  "email": "test2@example.com",
  "status": "enabled",
  "roles": ["admin"]
}
```

**更新用户角色 Body**（PUT `.../users/test01/roles`）:

```json
{
  "roles": ["admin"]
}
```

**修改密码 Body**（PUT `.../users/test01/password`）:

```json
{
  "oldPassword": "Test123456",
  "newPassword": "NewPass123"
}
```

### 3.4 角色管理

| 操作       | 方法 | URL |
|------------|------|-----|
| 角色列表   | GET  | `http://localhost:8080/api/deploy/roles` |
| 新增角色   | POST | `http://localhost:8080/api/deploy/roles` |
| 更新角色   | PUT  | `http://localhost:8080/api/deploy/roles/角色编码` |
| 删除角色   | DELETE | `http://localhost:8080/api/deploy/roles/角色编码` |
| 获取权限   | GET  | `http://localhost:8080/api/deploy/roles/admin/permissions` |
| 保存权限   | PUT  | `http://localhost:8080/api/deploy/roles/admin/permissions` |

**新增角色 Body**（POST `/api/deploy/roles`）:

```json
{
  "code": "operator",
  "name": "操作员",
  "description": "普通操作员角色"
}
```

**保存角色权限 Body**（PUT `.../roles/admin/permissions`）:

```json
{
  "permissions": [
    { "menuKey": "deploy.user", "actions": ["display", "create", "edit", "delete", "view"] },
    { "menuKey": "deploy.role", "actions": ["display", "view"] }
  ]
}
```

---

## 4. Postman 使用建议

1. **环境变量**  
   - 新建 Environment，例如：  
     - `base_url` = `http://localhost:8080`  
     - `token` = 登录后把返回的 token 填进去（或用脚本自动保存）。  
   - 请求 URL 用：`{{base_url}}/api/auth/login`、`{{base_url}}/api/deploy/users` 等。

2. **登录后自动带 Token**  
   - 在「登录」请求的 **Tests** 里写：
   ```javascript
   if (pm.response.code === 200) {
     var json = pm.response.json();
     if (json.data && json.data.token) {
       pm.environment.set("token", json.data.token);
     }
   }
   ```
   - 其他需认证的请求在 **Authorization** 选 Bearer Token，Value 填 `{{token}}`。

3. **测试顺序建议**  
   - 健康检查 → 登录 → `/api/auth/me` → 用户分页 → 角色列表 → 再测增删改。

---

## 5. 常见响应

- **200**：成功，业务数据在 `data` 里。  
- **400**：参数错误，看 `message` 里的校验提示。  
- **401**：未登录或 token 无效/过期，需重新登录获取 token。  
- **404/600**：资源不存在或业务异常，看 `message`。

默认管理员：**admin** / **admin123**（首次启动由 data.sql 初始化）。
