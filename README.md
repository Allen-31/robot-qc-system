# 机器人质检管理系统 (RobotQcSystem)

基于 **Spring Boot 3 + PostgreSQL + MyBatis + Redis** 的后台架构，高内聚低耦合，便于扩展。

## 技术栈

| 类别     | 技术 |
|----------|------|
| 框架     | Spring Boot 3.3.5, Spring Web, Validation, AOP, Security |
| 认证     | JWT（Bearer Token） |
| 数据库   | PostgreSQL, MyBatis 3.0.3 |
| 缓存     | Spring Data Redis |
| 文档     | SpringDoc OpenAPI 3 (Swagger UI) |
| 工具     | Lombok, Hutool, Guava, Commons Lang3 |

## 项目结构

```
src/main/java/com/zioneer/robotqcsystem/
├── RobotQcSystemApplication.java    # 启动类
├── common/                           # 公共层
│   ├── constant/                     # 常量
│   ├── exception/                    # 全局异常处理
│   ├── page/                         # 分页查询与结果
│   └── result/                       # 统一响应封装
├── config/                           # 配置
│   ├── MyBatisConfig.java
│   ├── RedisConfig.java
│   └── SwaggerConfig.java
├── controller/                       # 控制器
├── domain/entity/                    # 实体
├── mapper/                           # MyBatis Mapper 接口
├── service/                          # 业务接口
│   └── impl/                         # 业务实现
src/main/resources/
├── application.properties
├── schema.sql                        # 建表脚本（启动时执行）
└── mapper/*.xml                      # MyBatis XML
```

## 运行前准备

1. **PostgreSQL**：创建数据库并配置连接
   ```bash
   createdb robot_qc
   ```
   或使用环境变量：`DB_URL`, `DB_USERNAME`, `DB_PASSWORD`（默认 `localhost:5432/robot_qc`, postgres/postgres）

2. **Redis**：本地 6379 或通过 `spring.data.redis.host/port` 配置

## 启动

```bash
./gradlew bootRun
```

默认端口 **8080**。

## 接口文档（Swagger）

启动后访问：

- **Swagger UI（网页调试）**: http://localhost:8080/swagger-ui.html  
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs  

可在 Swagger UI 中直接调用接口进行测试。

## 认证与用户（与接口文档一致）

- `POST /api/auth/login` - 登录（body: username, password, remember）→ 返回 token 与 user  
- `POST /api/auth/logout` - 登出  
- `GET /api/auth/me` - 获取当前用户（需 Header: Authorization: Bearer &lt;token&gt;）  
- `GET /api/deploy/users` - 用户分页（keyword, role, status, page, pageSize）  
- `POST /api/deploy/users` - 新增用户  
- `PUT /api/deploy/users/{code}` - 更新用户  
- `DELETE /api/deploy/users/{code}` - 删除用户  
- `PUT /api/deploy/users/{code}/roles` - 更新用户角色  
- `PUT /api/deploy/users/{code}/password` - 修改密码  
- `GET /api/deploy/roles` - 角色列表  
- `POST /api/deploy/roles` - 新增角色  
- `PUT /api/deploy/roles/{code}` - 更新角色  
- `DELETE /api/deploy/roles/{code}` - 删除角色  
- `GET /api/deploy/roles/{code}/permissions` - 获取角色权限  
- `PUT /api/deploy/roles/{code}/permissions` - 保存角色权限  

**默认管理员**：编码 `admin`，密码 `admin123`（首次启动由 data.sql 初始化）。

**给前端的接口文档**：见 [docs/API接口文档.md](docs/API接口文档.md)，含基础路径、认证方式、统一响应、登录/用户/角色等接口说明，可直接发给前端联调。  
**在线文档**：启动后访问 http://localhost:8080/swagger-ui.html；OpenAPI JSON：http://localhost:8080/v3/api-docs。  
**Postman 测试**：详见 [docs/POSTMAN_测试说明.md](docs/POSTMAN_测试说明.md)。

## 其他示例接口

- `GET /api/health` - 健康检查  
- `GET /api/robot/page` - 机器人分页列表  
- `GET /api/robot/{id}` - 机器人详情  
- `POST /api/robot` - 新增机器人  
- `PUT /api/robot` - 更新机器人  
- `DELETE /api/robot/{id}` - 删除机器人  

## 构建

```bash
./gradlew build
```
