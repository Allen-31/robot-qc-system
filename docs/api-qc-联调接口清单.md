# 质检业务后台接口清单（前端联调用）

**基础说明**

- **Base URL**：与后端约定（如 `http://localhost:8080`）
- **统一响应**：`Result<T>` 结构为 `{ code: number, message: string, data?: T }`，成功时 `code === 200`
- **分页结果**：`PageResult<T>` 结构为 `{ list: T[], total: number, pageNum: number, pageSize: number, pages: number }`
- **分页入参**：列表类接口支持 `pageNum`（默认 1）、`pageSize`（默认见后端常量，最大 500）；部分接口也支持 `page` 会映射到 `pageNum`
- **鉴权**：需登录后携带 Token（具体以项目配置为准）

---

## 一、质检配置（/api/qc/config）

### 1.1 车间配置 `/api/qc/config/workshops`

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/api/qc/config/workshops` | 车间配置列表，无参，返回 `List<QcWorkshopVO>` |
| POST | `/api/qc/config/workshops` | 新增车间，Body: `QcWorkshopCreateDTO` |
| PUT | `/api/qc/config/workshops/{code}` | 更新车间，路径参数 `code`，Body: `QcWorkshopUpdateDTO` |
| DELETE | `/api/qc/config/workshops/{code}` | 删除车间，路径参数 `code` |

### 1.2 工作站配置 `/api/qc/config/workstations`

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/api/qc/config/workstations` | 工作站配置列表，Query: `keyword`(可选), `workshopCode`(可选) |
| POST | `/api/qc/config/workstations` | 新增工作站，Body: `QcWorkstationCreateDTO` |
| PUT | `/api/qc/config/workstations/{id}` | 更新工作站，路径参数 `id`(Long)，Body: `QcWorkstationUpdateDTO` |
| DELETE | `/api/qc/config/workstations/{id}` | 删除工作站，路径参数 `id`(Long) |

### 1.3 工位配置 `/api/qc/config/stations`

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/api/qc/config/stations` | 工位配置列表，Query: `workstationId`(可选) |
| POST | `/api/qc/config/stations` | 新增工位，Body: `QcStationConfigCreateDTO` |
| PUT | `/api/qc/config/stations/{id}` | 更新工位，路径参数 `id`(Long)，Body: `QcStationConfigUpdateDTO` |
| DELETE | `/api/qc/config/stations/{id}` | 删除工位，路径参数 `id`(Long) |

### 1.4 线束类型 `/api/qc/config/wire-harness-types`

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/api/qc/config/wire-harness-types` | 线束类型列表，无参 |
| POST | `/api/qc/config/wire-harness-types` | 新增线束类型，Body: `QcWireHarnessTypeCreateDTO` |
| PUT | `/api/qc/config/wire-harness-types/{id}` | 更新线束类型，路径参数 `id`(Long)，Body: `QcWireHarnessTypeUpdateDTO` |
| DELETE | `/api/qc/config/wire-harness-types/{id}` | 删除线束类型，路径参数 `id`(Long) |

### 1.5 终端配置 `/api/qc/config/terminals`

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/api/qc/config/terminals` | 终端配置列表，Query: `workstationId`(可选) |
| POST | `/api/qc/config/terminals` | 新增终端，Body: `QcTerminalCreateDTO` |
| PUT | `/api/qc/config/terminals/{id}` | 更新终端，路径参数 `id`(Long)，Body: `QcTerminalUpdateDTO` |
| DELETE | `/api/qc/config/terminals/{id}` | 删除终端，路径参数 `id`(Long) |

---

## 二、质检业务（/api/qc）

### 2.1 工作站管理（业务侧） `/api/qc/workstations`

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/api/qc/workstations` | 工作站列表（分页），Query: `keyword`(可选), `pageNum`, `pageSize`，返回 `PageResult<QcWorkstationVO>` |
| POST | `/api/qc/workstations` | 新增工作站，Body: `QcWorkstationCreateDTO` |
| PUT | `/api/qc/workstations/{id}` | 更新工作站，路径参数 `id`(Long)，Body: `QcWorkstationUpdateDTO` |
| DELETE | `/api/qc/workstations/{id}` | 删除工作站，路径参数 `id`(Long) |

### 2.2 工位/质检台相关 `/api/qc`

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/api/qc/station-positions` | 工位列表（分页），Query: `workstationId`(可选), `pageNum`, `pageSize`，返回 `PageResult<QcStationPositionVO>` |
| GET | `/api/qc/stations/{stationId}/robots` | 当前质检台关联机器人列表，路径参数 `stationId`，返回 `List<QcStationRobotVO>` |
| GET | `/api/qc/stations/{stationId}/statistics` | 当前质检台统计，路径参数 `stationId`，Query: `dimension`(day/week 可选), `date`(yyyy-MM-dd 可选)，返回 `QcStationSummaryVO` |

### 2.3 工单管理 `/api/qc/work-orders`

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/api/qc/work-orders` | 工单列表（分页/筛选），Query 见下表，返回 `PageResult<QcWorkOrderVO>` |
| GET | `/api/qc/work-orders/{id}` | 工单详情，路径参数 `id`(Long) |
| PUT | `/api/qc/work-orders/{id}` | 更新工单，路径参数 `id`，Body: `QcWorkOrderUpdateDTO` |
| POST | `/api/qc/work-orders/{id}/review` | 工单复检（提交复检结果），路径参数 `id`，Body: `QcWorkOrderReviewDTO` |
| POST | `/api/qc/work-orders/{id}/pause` | 暂停工单，路径参数 `id` |
| POST | `/api/qc/work-orders/{id}/resume` | 恢复工单，路径参数 `id` |
| POST | `/api/qc/work-orders/{id}/cancel` | 取消工单，路径参数 `id` |
| DELETE | `/api/qc/work-orders/{id}` | 删除工单，路径参数 `id` |

**工单列表 Query（QcWorkOrderQuery）**

- `pageNum`, `pageSize`：分页
- `keyword`：工单号/线束编码等
- `status`：pending / running / paused / finished / ng / cancelled
- `qualityResult`：ok / ng / pending
- `stationCode`：工位/质检台编码
- `harnessType`：线束类型
- `dateFrom`, `dateTo`：创建日期范围，yyyy-MM-dd
- `orderBy`：如 startedAt_desc、createdAt_desc

### 2.4 质检记录 `/api/qc/quality-records`

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/api/qc/quality-records` | 质检记录列表（分页），Query: `keyword`(可选), `onlyNg`(可选, 默认 true), `pageNum`, `pageSize`，返回 `PageResult<QcQualityRecordVO>` |
| GET | `/api/qc/quality-records/{id}` | 质检记录详情，路径参数 `id`(Long) |

### 2.5 复检记录 `/api/qc/reinspection-records`

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/api/qc/reinspection-records` | 复检记录列表（分页），Query: `keyword`, `status`(pending/completed/cancelled), `reinspectionResult`(ok/ng/pending), `dateFrom`, `dateTo`, `pageNum`, `pageSize`，返回 `PageResult<QcReinspectionRecordVO>` |

---

## 三、质检统计 `/api/qc/statistics`

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/api/qc/statistics` | 质检统计列表（分页），Query: `dateFrom`, `dateTo`(必填), `factory`, `workshop`, `workstation`, `station`(可选), `pageNum`, `pageSize`，返回 `PageResult<QcStatisticsRowVO>` |
| GET | `/api/qc/statistics/summary` | 质检区指标汇总（按日/周），Query: `workstationId`(可选), `dimension`(默认 day, 可选 week), `date`(yyyy-MM-dd 可选)，返回 `QcStatisticsSummaryVO` |
| GET | `/api/qc/statistics/station-summary` | 质检台统计汇总，Query: `stationId`(必填), `dimension`, `date`，返回 `QcStationSummaryVO` |

---

## 四、DTO/VO 字段说明

具体字段以后端 **Swagger/OpenAPI** 或 **domain.dto / domain.vo** 包下类为准。启动后访问 **Swagger UI**（如 `/swagger-ui.html` 或 `/swagger-ui/index.html`）可查看完整请求/响应结构。

常用 DTO 名称对应：

- 车间：QcWorkshopCreateDTO / QcWorkshopUpdateDTO，QcWorkshopVO  
- 工作站：QcWorkstationCreateDTO / QcWorkstationUpdateDTO，QcWorkstationConfigVO / QcWorkstationVO  
- 工位：QcStationConfigCreateDTO / QcStationConfigUpdateDTO，QcStationConfigVO  
- 线束类型：QcWireHarnessTypeCreateDTO / QcWireHarnessTypeUpdateDTO，QcWireHarnessTypeVO  
- 终端：QcTerminalCreateDTO / QcTerminalUpdateDTO，QcTerminalVO  
- 工单：QcWorkOrderQuery，QcWorkOrderUpdateDTO，QcWorkOrderReviewDTO，QcWorkOrderVO  
- 质检记录：QcQualityRecordQuery，QcQualityRecordVO  
- 复检记录：QcReinspectionRecordQuery，QcReinspectionRecordVO  
- 统计：QcStatisticsQuery，QcStatisticsRowVO，QcStatisticsSummaryVO，QcStationSummaryVO，QcStationPositionVO，QcStationRobotVO  

---

**文档版本**：根据当前 controller 与 DTO 整理，若有新增接口或字段以实际代码与 Swagger 为准。
