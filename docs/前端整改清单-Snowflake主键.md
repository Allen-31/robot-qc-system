# 前端整改清单：Snowflake 主键（id 改为数值型）

后端已将**设备、机器人、质检配置与业务**等模块的主键统一为 Snowflake（`id` 为 `Long` 数值）。以下为前端需要配合修改的项。

---

## 一、统一约定

| 项目 | 说明 |
|------|------|
| **主键 `id`** | 接口中一律为 **数值类型**（number / bigint），不要按字符串处理。 |
| **业务编码** | 列表/表格展示仍用 `code`、`workOrderNo`、`reinspectionNo`、`sn` 等业务编码，**不要**用 `id` 做展示。 |
| **更新/删除/详情** | 请求路径中的 `{id}` 必须传 **数值**，例如 `PUT /api/deploy/devices/1234567890123456789`，不要加引号或转成字符串。 |

---

## 二、按模块列出的接口与类型

### 1. 设备管理 `/api/deploy/devices`

| 接口 | 路径参数 | 响应中 id |
|------|----------|-----------|
| GET 列表 | - | `data.list[].id` → **number** |
| GET 详情 | `GET /{id}` | `data.id` → **number** |
| PUT 更新 | `PUT /{id}`，**id 为 number** | - |
| DELETE 删除 | `DELETE /{id}`，**id 为 number** | - |

**前端整改**：调用更新/删除/详情时，路径中的 `id` 使用接口返回的数值 `id`，不要用 `code` 或字符串。

---

### 2. 机器人 `/api/deploy/robots`

| 接口 | 路径参数 | 响应中 id |
|------|----------|-----------|
| GET 列表 | - | `data.list[].id` → **number** |
| GET 详情 | `GET /{id}` | `data.id` → **number** |
| PUT 更新 | `PUT /{id}`，**id 为 number** | - |
| DELETE 删除 | `DELETE /{id}`，**id 为 number** | - |

**前端整改**：同上，路径 `id` 用数值；列表/展示可用 `code` 等。

---

### 3. 质检-车间配置 `/api/qc/config/workshops`

| 说明 | 说明 |
|------|------|
| 车间**未改** | 仍使用 **code**（字符串）作为路径参数：`PUT /{code}`、`DELETE /{code}`，**无需改**。 |

---

### 4. 质检-工作站配置 `/api/qc/config/workstations`

| 接口 | 路径参数 | 响应中 id |
|------|----------|-----------|
| GET 列表 | - | `data.list[].id` → **number** |
| PUT 更新 | `PUT /{id}`，**id 为 number** | - |
| DELETE 删除 | `DELETE /{id}`，**id 为 number** | - |

**前端整改**：更新/删除使用返回的数值 `id`，不要用 `code` 或字符串。

---

### 5. 质检-工位配置 `/api/qc/config/stations`

| 接口 | 路径参数 | 响应中 id |
|------|----------|-----------|
| GET 列表 | - | `data.list[].id` → **number**（QcStationConfigVO / 工位配置） |
| PUT 更新 | `PUT /{id}`，**id 为 number** | - |
| DELETE 删除 | `DELETE /{id}`，**id 为 number** | - |

**前端整改**：路径 `id` 用数值；列表展示用 `code` 等。

---

### 6. 质检-线束类型 `/api/qc/config/wire-harness-types`

| 接口 | 路径参数 | 响应中 id |
|------|----------|-----------|
| GET 列表 | - | `data.list[].id` → **number** |
| PUT 更新 | `PUT /{id}`，**id 为 number** | - |
| DELETE 删除 | `DELETE /{id}`，**id 为 number** | - |

**前端整改**：同上，`id` 用数值。

---

### 7. 质检-终端配置 `/api/qc/config/terminals`

| 接口 | 路径参数 | 响应中 id |
|------|----------|-----------|
| GET 列表 | - | `data.list[].id` → **number** |
| PUT 更新 | `PUT /{id}`，**id 为 number** | - |
| DELETE 删除 | `DELETE /{id}`，**id 为 number** | - |

**前端整改**：同上。

---

### 8. 质检-工作站（业务侧） `/api/qc/workstations`

| 接口 | 路径参数 | 响应中 id |
|------|----------|-----------|
| GET 列表 | - | `data.list[].id` → **number** |
| PUT 更新 | `PUT /{id}`，**id 为 number** | - |
| DELETE 删除 | `DELETE /{id}`，**id 为 number** | - |

**前端整改**：路径 `id` 用数值。

---

### 9. 质检-工位列表与统计 `/api/qc/station-positions`、`/api/qc/stations/{stationId}`

| 接口 | 说明 |
|------|------|
| GET `/api/qc/station-positions` | 返回 `QcStationPositionVO[]`，其中 **id → number**。 |
| GET `/api/qc/stations/{stationId}/robots` | `stationId` 仍为**字符串**（质检台编号），**无需改**。 |
| GET `/api/qc/stations/{stationId}/statistics` | 同上，`stationId` 为字符串，**无需改**。 |

**前端整改**：仅确认工位列表中的 `id` 类型为 number；若某处用工位 id 调其他接口，传数值。

---

### 10. 工单管理 `/api/qc/work-orders`

| 接口 | 路径参数 | 响应中 id |
|------|----------|-----------|
| GET 列表 | - | `data.list[].id` → **number** |
| GET 详情 | `GET /{id}` | `data.id` → **number** |
| PUT 更新 | `PUT /{id}`，**id 为 number** | - |
| POST 复检 | `POST /{id}/review`，**id 为 number** | - |
| POST 暂停 | `POST /{id}/pause`，**id 为 number** | - |
| POST 恢复 | `POST /{id}/resume`，**id 为 number** | - |
| POST 取消 | `POST /{id}/cancel`，**id 为 number** | - |
| DELETE 删除 | `DELETE /{id}`，**id 为 number** | - |

**前端整改**：所有路径中的 `{id}` 使用工单的数值 `id`；列表/展示用 `workOrderNo` 等。

---

### 11. 质检记录 `/api/qc/quality-records`

| 接口 | 路径参数 | 响应中 id |
|------|----------|-----------|
| GET 列表 | - | `data.list[].id` → **number** |
| GET 详情 | `GET /{id}` | `data.id` → **number** |

**前端整改**：详情请求与任何用 id 的地方均用数值 `id`。

---

### 12. 复检记录 `/api/qc/reinspection-records`

| 接口 | 响应说明 |
|------|----------|
| GET 列表 | `data.list[].id` → **number** |

当前仅有列表接口，无 `/{id}` 操作；若后续有详情/操作接口，路径 `id` 用数值。

---

### 13. 质检统计 `/api/qc/statistics`

| 说明 |
|------|
| 统计接口的 Query 参数（如 `workstationId`、`stationId`）仍可能为字符串（ID 或编码），按现有约定传即可，**无新增 id 类型要求**。 |

---

## 三、不需要改的模块

| 模块 | 说明 |
|------|------|
| **用户管理** | 仍用 **code**（字符串）：`PUT /{code}`、`DELETE /{code}` 等。 |
| **角色管理** | 仍用 **code**（字符串）：`PUT /{code}`、`DELETE /{code}` 等。 |
| **菜单管理** | 菜单主键为 **id（Long）**，若前端此前已按数值处理则无需改。 |
| **车间配置** | 使用 **code**，见上文。 |

---

## 四、TypeScript / 类型建议

1. **接口类型定义**  
   将下列 VO 的 `id` 定义为 **number**（若为 bigint 可考虑 `number` 或 `string` 仅用于展示，请求仍用 number）：
   - 设备、机器人、工作站（配置+业务）、工位、线束类型、终端、工单、质检记录、复检记录、工位列表（QcStationPositionVO）等。

2. **请求路径**  
   拼接 URL 时不要将 `id` 转为字符串类型（如 `String(id)`），直接使用数字即可，例如：
   - `PUT /api/qc/config/workstations/${row.id}`（`row.id` 为 number）
   - 避免：`/api/qc/config/workstations/${String(row.id)}` 若导致科学计数法等问题再按需处理。

3. **列表/表格**  
   展示列用 `code`、`workOrderNo`、`reinspectionNo`、`sn` 等业务字段；行操作（编辑、删除、详情）用接口返回的 `id`（number）调对应接口。

---

## 五、汇总：必须用数值 id 的接口清单

| 模块 | 方法 | 路径示例 |
|------|------|-----------|
| 设备管理 | GET/PUT/DELETE | `/api/deploy/devices/{id}` |
| 机器人 | GET/PUT/DELETE | `/api/deploy/robots/{id}` |
| 工作站配置 | PUT/DELETE | `/api/qc/config/workstations/{id}` |
| 工位配置 | PUT/DELETE | `/api/qc/config/stations/{id}` |
| 线束类型 | PUT/DELETE | `/api/qc/config/wire-harness-types/{id}` |
| 终端配置 | PUT/DELETE | `/api/qc/config/terminals/{id}` |
| 工作站（业务） | PUT/DELETE | `/api/qc/workstations/{id}` |
| 工单 | GET/PUT/DELETE + review/pause/resume/cancel | `/api/qc/work-orders/{id}` 及子路径 |
| 质检记录 | GET | `/api/qc/quality-records/{id}` |

以上路径中的 **{id}** 一律传 **数值类型**（number），响应里对应实体的 **id** 也为 **number**。

---

**文档版本**：与后端 Snowflake 主键改造一致，若有新增接口以 Swagger 与后端实际为准。
