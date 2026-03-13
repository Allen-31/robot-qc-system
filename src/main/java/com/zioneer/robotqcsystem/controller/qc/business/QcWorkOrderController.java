package com.zioneer.robotqcsystem.controller.qc.business;

import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.common.result.Result;
import com.zioneer.robotqcsystem.domain.dto.QcWorkOrderQuery;
import com.zioneer.robotqcsystem.domain.dto.QcWorkOrderReviewDTO;
import com.zioneer.robotqcsystem.domain.dto.QcWorkOrderUpdateDTO;
import com.zioneer.robotqcsystem.domain.vo.QcWorkOrderVO;
import com.zioneer.robotqcsystem.service.qc.QcWorkOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 质检业务-工单管理（2.1.3）
 */
@Tag(name = "质检-工单管理", description = "工单列表、详情、更新、复检、暂停/恢复/取消/删除")
@RestController
@RequestMapping("/api/qc/work-orders")
@RequiredArgsConstructor
public class QcWorkOrderController {

    private final QcWorkOrderService service;

    @Operation(summary = "工单列表（分页/筛选）")
    @GetMapping
    public Result<PageResult<QcWorkOrderVO>> page(@Valid QcWorkOrderQuery query) {
        return Result.ok(service.page(query));
    }

    @Operation(summary = "工单详情")
    @GetMapping("/{id}")
    public Result<QcWorkOrderVO> getById(
            @Parameter(description = "工单ID") @PathVariable Long id) {
        return Result.ok(service.getById(id));
    }

    @Operation(summary = "更新工单")
    @PutMapping("/{id}")
    public Result<Void> update(
            @Parameter(description = "工单ID") @PathVariable Long id,
            @RequestBody @Valid QcWorkOrderUpdateDTO dto) {
        service.update(id, dto);
        return Result.ok();
    }

    @Operation(summary = "工单复检（提交复检结果）")
    @PostMapping("/{id}/review")
    public Result<QcWorkOrderVO> review(
            @Parameter(description = "工单ID") @PathVariable Long id,
            @RequestBody @Valid QcWorkOrderReviewDTO dto) {
        return Result.ok(service.review(id, dto));
    }

    @Operation(summary = "暂停工单")
    @PostMapping("/{id}/pause")
    public Result<QcWorkOrderVO> pause(@Parameter(description = "工单ID") @PathVariable Long id) {
        return Result.ok(service.pause(id));
    }

    @Operation(summary = "恢复工单")
    @PostMapping("/{id}/resume")
    public Result<QcWorkOrderVO> resume(@Parameter(description = "工单ID") @PathVariable Long id) {
        return Result.ok(service.resume(id));
    }

    @Operation(summary = "取消工单")
    @PostMapping("/{id}/cancel")
    public Result<QcWorkOrderVO> cancel(@Parameter(description = "工单ID") @PathVariable Long id) {
        return Result.ok(service.cancel(id));
    }

    @Operation(summary = "删除工单")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@Parameter(description = "工单ID") @PathVariable Long id) {
        service.deleteById(id);
        return Result.ok();
    }
}
