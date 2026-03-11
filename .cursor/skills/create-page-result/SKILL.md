---
name: create-page-result
description: 创建或复用分页返回对象（list/total/pageNum/pageSize），本仓库已使用 PageResult<T>。
---

# 分页返回对象

当用户需要分页列表的统一返回结构时，按本技能生成或对齐现有实现。

## 何时使用

- 用户说「创建分页结果」「建 PageResult」「分页返回结构」等。
- 新项目从零搭建时。

## 标准字段（企业常见）

- **list**：当前页数据列表，`List<T>`。
- **total**：总记录数，long。
- **page** / **pageNum**：当前页码，从 1 开始。
- **pageSize**：每页条数。
- **pages**（可选）：总页数。

## 本仓库已有实现

本项目已使用 **`PageResult<T>`**（`com.zioneer.robotqcsystem.common.page.PageResult`），无需再建。

- 字段：`list`、`total`、`pageNum`、`pageSize`、`pages`。
- 工厂方法：`PageResult.empty(PageQuery query)`、`PageResult.of(list, total, pageNum, pageSize)`。
- 分页入参：`PageQuery`（含 pageNum、pageSize）。

若用户要求「新建 PageResult」，可说明「项目中已有 PageResult，建议直接使用以保持一致」。

## 若需从零生成（新项目）

```java
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> {
    private List<T> list;
    private Long total;
    private Integer pageNum;   // 或 page
    private Integer pageSize;
    private Integer pages;    // 可选，总页数
}
```

与列表查询参数（如 `pageNum`、`pageSize`）命名保持一致。
