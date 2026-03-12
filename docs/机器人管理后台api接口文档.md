# 机器人管理后台 API 接口文档

## 目录（按菜单结构）

- **一、通用（登录相关）**：登录、登出、获取当前用户
- **二、质检业务（一级菜单）**：2.1 业务、2.2 配置、2.3 统计
- **三、部署配置（一级菜单）**：3.1 任务、3.2 机器人、3.3 场景、3.4 设置、3.5 用户
- **四、运维管理（一级菜单）**：4.1 任务、4.2 机器人、4.3 通知、4.4 文件、4.5 升级、4.6 服务
- **五、数据统计（一级菜单）**：5.1 设备统计、5.2 异常统计

---

## 一、通用（登录相关）

### 1.1 登录
- **接口描述**：前端登录页提交用户名、密码后调用，验证通过返回 token 与用户信息，用于后续请求鉴权。
- **URL**：`POST /api/auth/login`
- **请求方式**：POST

### 1.2 登出
- **接口描述**：用户登出时调用，服务端使当前 token 失效；请求需携带 token。
- **URL**：`POST /api/auth/logout`
- **请求方式**：POST

### 1.3 获取当前用户信息
- **接口描述**：根据当前 token 获取当前登录用户信息，用于刷新用户及权限。
- **URL**：`GET /api/auth/me`
- **请求方式**：GET（无 body）

---

## 二、质检业务（一级菜单）

### 2.1 业务（二级菜单）

#### 2.1.1 工作站管理（三级菜单）
**接口 2.1.1.1 工作站列表**

- **接口描述**：质检业务-工作站管理页获取工作站列表。
- **URL**：`GET /api/qc/workstations`
- **请求方式**：GET

**接口 2.1.1.2** 新增/更新/删除工作站：`POST /api/qc/workstations`、`PUT /api/qc/workstations/{id}`、`DELETE /api/qc/workstations/{id}`，请求体与返回格式同 2.2.1 工作站配置风格，此处略。

#### 2.1.2 工位管理（三级菜单）

**接口 2.1.2.1 工位列表**
- **接口描述**：质检业务-工位管理页获取工位列表。
- **URL**：`GET /api/qc/station-positions`
- **请求方式**：GET

#### 2.1.3 工单管理（三级菜单）

**接口 2.1.3.1 工单列表（分页/筛选）**
接口描述：质检业务-工单管理页获取工单列表。
URL：/api/qc/work-orders
请求方式：GET
请求字段（查询参数，拼在 URL）
请求参数示例
返回字段
返回示例
接口 2.1.3.2 工单详情
接口描述：根据工单 ID 获取单条工单完整信息。
URL：/api/qc/work-orders/{id}
请求方式：GET
请求字段：路径参数 id（工单ID），无请求体。
请求参数示例
返回字段：与列表单条结构一致，可增加 videoUrl、imageUrl 等扩展字段。
返回示例

2.1.4 复检记录（三级菜单）
接口 2.1.4.1 复检记录列表（分页/筛选）
接口描述：质检业务-复检记录页获取复检记录列表。
URL：/api/qc/reinspection-records
请求方式：GET
请求字段（查询参数）
请求参数示例
返回字段
返回示例
接口 2.1.4.2 创建复检
URL：/api/qc/reinspection-records
请求方式：POST
请求字段
请求参数示例
返回示例
接口 2.1.4.3 取消复检
URL：/api/qc/reinspection-records/{id}/cancel
请求方式：POST
请求参数示例：无请求体。
返回示例

2.2 配置（二级菜单）
2.2.1 工作站配置（三级菜单）
接口 2.2.1.1 工作站配置列表
接口描述：质检配置-工作站配置页获取列表。
URL：/api/qc/config/workstations
请求方式：GET
请求字段：无（或 keyword、workshopCode 等查询参数）。
请求参数示例
返回字段
返回示例
接口 2.2.1.2 新增工作站配置
URL：/api/qc/config/workstations
请求方式：POST
请求参数示例
返回示例
接口 2.2.1.3 更新/删除工作站配置
更新：PUT /api/qc/config/workstations/{id}，请求体同新增字段。
删除：DELETE /api/qc/config/workstations/{id}。
PUT 请求参数示例
PUT 返回示例
DELETE 返回示例

2.2.2 工位配置（三级菜单）
接口 2.2.2.1 工位配置列表
接口描述：质检配置-工位配置页，按工作站或全局获取工位配置。
URL：/api/qc/config/stations
请求方式：GET
请求字段（查询参数）
请求参数示例
返回字段（data 为数组）
返回示例

2.2.3 线束类型（三级菜单）
接口 2.2.3.1 线束类型列表
URL：/api/qc/config/wire-harness-types
请求方式：GET
请求参数示例：无。
返回示例

2.2.4 终端配置（三级菜单）
接口 2.2.4.1 终端配置列表
URL：/api/qc/config/terminals
请求方式：GET
请求参数示例：无或 workstationId=xxx。
返回示例

2.2.5 车间配置（三级菜单）
接口 2.2.5.1 车间配置列表
URL：/api/qc/config/workshops
请求方式：GET
请求参数示例：无。
返回示例

2.3 统计（二级菜单）
2.3.1 质检统计（三级菜单）
接口 2.3.1.1 质检统计数据
接口描述：按日期/工厂/车间/工作站/工位等维度查询质检统计。
URL：/api/qc/statistics 或 /api/statistics/quality
请求方式：GET
请求字段（查询参数）
请求参数示例
返回字段
返回示例

2.3.2 质检报表（三级菜单）
可与 2.3.1 共用同一接口，或单独提供导出接口（如 GET /api/qc/statistics/export 返回文件流）。若为同一列表接口，请求与返回格式同 2.3.1。

---

## 三、部署配置（一级菜单）

### 3.1 任务（二级菜单）
3.1.1 任务模板（三级菜单）
接口 3.1.1.1 任务模板列表
接口描述：部署配置-任务模板页获取列表，支持关键词筛选。
URL：/api/deploy/task-templates
请求方式：GET
请求字段（查询参数）
请求参数示例
返回字段（data 为数组）
返回示例
接口 3.1.1.2 新增任务模板
URL：/api/deploy/task-templates
请求方式：POST
请求参数示例
返回示例
接口 3.1.1.3 更新任务模板
URL：/api/deploy/task-templates/{code}
请求方式：PUT
请求参数示例
返回示例
接口 3.1.1.4 删除任务模板
URL：/api/deploy/task-templates/{code}
请求方式：DELETE
返回示例

3.1.2 动作模板（三级菜单）
接口 3.1.2.1 动作模板列表
URL：/api/deploy/action-templates
请求方式：GET
请求参数示例
返回示例
接口 3.1.2.2 新增动作模板
URL：/api/deploy/action-templates
请求方式：POST
请求参数示例
返回示例
接口 3.1.2.3 更新/删除动作模板
更新：PUT /api/deploy/action-templates/{code}，请求体 { "name": "xxx", "enabled": true }，返回 { "code": 200, "msg": "success", "data": null, "timestamp": ..., "traceId": "..." }。
删除：DELETE /api/deploy/action-templates/{code}，返回同上。

3.2 机器人（二级菜单）
3.2.1 充电策略（三级菜单）
接口 3.2.1.1 充电策略列表
URL：/api/deploy/charge-strategies
请求方式：GET
请求参数示例
返回示例
接口 3.2.1.2 新增充电策略
URL：/api/deploy/charge-strategies
请求方式：POST
请求参数示例
返回示例
接口 3.2.1.3 更新充电策略
URL：/api/deploy/charge-strategies/{code}
请求方式：PUT
请求参数示例
返回示例
接口 3.2.1.4 删除充电策略
URL：/api/deploy/charge-strategies/{code}
请求方式：DELETE
返回示例

3.2.2 归巢策略（三级菜单）
接口 3.2.2.1 归巢策略列表
URL：/api/deploy/homing-strategies
请求方式：GET
请求参数示例
返回示例
接口 3.2.2.2 新增归巢策略
URL：/api/deploy/homing-strategies
请求方式：POST
请求参数示例
返回示例
接口 3.2.2.3 更新/删除归巢策略
更新：PUT /api/deploy/homing-strategies/{code}，请求体含 name、status、robotType、robotGroup、robot、triggerRule（idleWaitSeconds）。返回示例：{ "code": 200, "msg": "success", "data": null, "timestamp": 1672531200, "traceId": "..." }。
删除：DELETE /api/deploy/homing-strategies/{code}，返回同上。

3.2.3 机器人列表、机器人类型、零部件、分组（三级菜单）
机器人列表（部署侧，与运维侧机器人管理区分）：GET /api/deploy/robots 或与运维共用列表，按业务约定。
机器人类型列表：GET /api/deploy/robot-types，返回示例：{ "code": 200, "msg": "success", "data": [ { "code": "AMR", "name": "AMR" }, { "code": "AGV", "name": "AGV" } ], "timestamp": 1672531200, "traceId": "..." }。
机器人分组列表：GET /api/deploy/robot-groups，返回示例：{ "code": 200, "msg": "success", "data": [ { "code": "RG-01", "name": "总装一线" } ], "timestamp": 1672531200, "traceId": "..." }。
机器人零部件列表：GET /api/deploy/robot-parts，返回示例：{ "code": 200, "msg": "success", "data": [ { "part": "motion-controller", "version": "v5.1.0" } ], "timestamp": 1672531200, "traceId": "..." }。

3.3 场景（二级菜单）
3.3.1 地图管理（三级菜单）
接口 3.3.1.1 地图列表（分页/筛选）
URL：/api/deploy/maps
请求方式：GET
请求参数示例
返回示例
接口 3.3.1.2 新增地图
URL：/api/deploy/maps
请求方式：POST
请求参数示例
返回示例
接口 3.3.1.3 更新地图
URL：/api/deploy/maps/{code}
请求方式：PUT
请求参数示例
返回示例
接口 3.3.1.4 删除地图
URL：/api/deploy/maps/{code}
请求方式：DELETE
返回示例
接口 3.3.1.5 发布地图
URL：/api/deploy/maps/{code}/publish
请求方式：POST
请求参数示例：无请求体。
返回示例

3.3.2 设备管理（三级菜单）
接口 3.3.2.1 设备列表
URL：/api/deploy/devices
请求方式：GET
请求参数示例
返回示例
接口 3.3.2.2 新增/更新/删除设备
新增：POST /api/deploy/devices，请求体示例：{ "code": "DEV-002", "name": "呼叫盒A-02", "type": "callBox", "mapCode": "MAP-001", "ip": "10.10.5.102" }，返回示例：{ "code": 200, "msg": "success", "data": { "id": "DEV-002", ... }, "timestamp": ..., "traceId": "..." }。
更新：PUT /api/deploy/devices/{id}，返回 data: null。
删除：DELETE /api/deploy/devices/{id}，返回 data: null。

3.4 设置（二级菜单）
3.4.1 配置模板（对应设置页）
接口 3.4.1.1 配置模板列表
URL：/api/deploy/config-templates
请求方式：GET
请求参数示例：无。
返回示例
接口 3.4.1.2 新增配置模板
URL：/api/deploy/config-templates
请求方式：POST
请求参数示例
返回示例
接口 3.4.1.3 更新/删除配置模板
更新：PUT /api/deploy/config-templates/{code}，请求体 { "name": "产线快检模板（已更新）" }，返回 { "code": 200, "msg": "success", "data": null, "timestamp": ..., "traceId": "..." }。
删除：DELETE /api/deploy/config-templates/{code}，返回同上。

3.5 用户（二级菜单）
3.5.1 用户管理（三级菜单）
接口 3.5.1.1 用户分页列表
URL：/api/deploy/users
请求方式：GET
请求参数示例
返回示例
接口 3.5.1.2 新增用户
URL：/api/deploy/users
请求方式：POST
请求参数示例
返回示例
接口 3.5.1.3 更新用户
URL：/api/deploy/users/{code}
请求方式：PUT
请求参数示例
返回示例
接口 3.5.1.4 删除用户
URL：/api/deploy/users/{code}
请求方式：DELETE
返回示例
接口 3.5.1.5 更新用户角色
URL：/api/deploy/users/{code}/roles
请求方式：PUT
请求参数示例
返回示例
接口 3.5.1.6 修改密码
URL：/api/deploy/users/{code}/password
请求方式：PUT
请求参数示例
返回示例

3.5.2 角色管理（三级菜单）
接口 3.5.2.1 角色列表
URL：/api/deploy/roles
请求方式：GET
请求参数示例
返回示例
接口 3.5.2.2 新增角色
URL：/api/deploy/roles
请求方式：POST
请求参数示例
返回示例
接口 3.5.2.3 更新角色
URL：/api/deploy/roles/{code}
请求方式：PUT
请求参数示例
返回示例
接口 3.5.2.4 删除角色
URL：/api/deploy/roles/{code}
请求方式：DELETE
返回示例

3.5.3 权限管理（分配权限，在角色管理页内）
接口 3.5.3.1 获取角色权限
URL：/api/deploy/roles/{code}/permissions
请求方式：GET
返回示例
接口 3.5.3.2 保存角色权限
URL：/api/deploy/roles/{code}/permissions
请求方式：PUT
请求参数示例
返回示例

---

## 四、运维管理（一级菜单）

### 4.1 任务（二级菜单）
4.1.1 任务管理（三级菜单）
接口 4.1.1.1 任务列表（分页/筛选）
接口描述：运维-任务管理页获取任务列表。
URL：/api/operation/tasks
请求方式：GET
请求参数示例
返回示例
接口 4.1.1.2 暂停任务
URL：/api/operation/tasks/{id}/pause
请求方式：POST
请求参数示例：无请求体。
返回示例
接口 4.1.1.3 恢复任务
URL：/api/operation/tasks/{id}/resume
请求方式：POST
返回示例
接口 4.1.1.4 取消任务
URL：/api/operation/tasks/{id}/cancel
请求方式：POST
返回示例

4.2 机器人（二级菜单）
4.2.1 机器人管理（三级菜单）
接口 4.2.1.1 机器人列表（分页/筛选）
URL：/api/operation/robots
请求方式：GET
请求参数示例
返回示例
接口 4.2.1.2 机器人操作（复位）
URL：/api/v1/robot/reset 或按现有 API 文档路径
请求方式：POST
请求参数示例
返回示例
接口 4.2.1.3 暂停机器人
URL：/api/v1/robot/pause
请求方式：POST
请求参数示例
返回示例
接口 4.2.1.4 归巢、切换地图、调度模式、取消充电 等：请求体与返回格式同上风格，如 { "robotCode": "RB-B201" } / { "robotCode": "RB-C301", "map": "仓储B图" } / { "robotCode": "RB-C301", "mode": "manual" } / { "robotCode": "RB-A102" }，返回 data 内为业务 code、message。

4.3 通知（二级菜单）
4.3.1 异常通知（三级菜单）
接口 4.3.1.1 异常通知列表（分页/筛选）
URL：/api/operation/exception-notifications 或 /api/operation/notifications/exception
请求方式：GET
请求参数示例
返回示例
接口 4.3.1.2 处理/关闭异常
URL：/api/operation/exception-notifications/{id}
请求方式：PUT
请求参数示例
返回示例
关闭时请求示例
返回示例

4.3.2 登录日志（三级菜单）
接口 4.3.2.1 登录日志列表
URL：/api/operation/logs/login
请求方式：GET
请求参数示例
返回示例

4.3.3 操作日志（三级菜单）
接口 4.3.3.1 操作日志列表
URL：/api/operation/logs/operation
请求方式：GET
请求参数示例
返回示例

4.3.4 API 日志（三级菜单）
接口 4.3.4.1 API 调用日志列表
URL：/api/operation/logs/api
请求方式：GET
请求参数示例
返回示例

4.4 文件（二级菜单）
4.4.1 文件管理（三级菜单）
接口 4.4.1.1 文件列表（分页/筛选）
URL：/api/operation/files
请求方式：GET
请求参数示例
返回示例
接口 4.4.1.2 上传文件
URL：/api/operation/files
请求方式：POST（multipart/form-data）
请求参数示例：表单字段如 file（文件）、name、type、tags（可选），按后端约定。
返回示例
接口 4.4.1.3 删除文件
URL：/api/operation/files/{id}
请求方式：DELETE
返回示例

4.5 升级（二级菜单）
4.5.1 软件包管理（三级菜单）
接口 4.5.1.1 软件包列表
URL：/api/operation/packages
请求方式：GET
请求参数示例
返回示例
接口 4.5.1.2 上传软件包
URL：/api/operation/packages
请求方式：POST（multipart/form-data）
返回示例
接口 4.5.1.3 删除软件包
URL：/api/operation/packages/{id}
请求方式：DELETE
返回示例

4.5.2 发布管理（三级菜单）
接口 4.5.2.1 发布任务列表
URL：/api/operation/publish
请求方式：GET
请求参数示例
返回示例
接口 4.5.2.2 创建发布任务
URL：/api/operation/publish
请求方式：POST
请求参数示例
返回示例
接口 4.5.2.3 取消发布任务
URL：/api/operation/publish/{id}/cancel
请求方式：POST
返回示例
接口 4.5.2.4 升级设备目录（可选）
URL：/api/operation/publish/devices 或 /api/operation/upgrade-devices
请求方式：GET
返回示例

4.6 服务（二级菜单）
4.6.1 服务管理（三级菜单）
接口 4.6.1.1 服务列表
URL：/api/operation/services
请求方式：GET
请求参数示例：无。
返回示例

---

## 五、数据统计（一级菜单）

### 5.1 设备统计（二级菜单）
接口 5.1.1 设备统计数据
接口描述：数据统计-设备统计页，设备运行与异常概览。
URL：/api/statistics/devices
请求方式：GET
请求参数示例
返回示例

5.2 异常统计（二级菜单）
接口 5.2.1 异常统计数据（分页/筛选）
接口描述：数据统计-异常统计页，按等级/类型/状态/时间筛选异常记录。
URL：/api/statistics/exceptions
请求方式：GET
请求参数示例
返回示例
