package com.zioneer.robotqcsystem.controller.operation.task;

import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.common.result.Result;
import com.zioneer.robotqcsystem.domain.dto.OperationTaskQuery;
import com.zioneer.robotqcsystem.domain.vo.OperationTaskActionResultVO;
import com.zioneer.robotqcsystem.domain.vo.OperationTaskVO;
import com.zioneer.robotqcsystem.service.operation.OperationTaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 运营-任务流管理（4.1.1）
 */
@Tag(name = "任务流管理", description = "运营-任务流管理")
@RestController
@RequestMapping("/api/operation/tasks")
@RequiredArgsConstructor
public class OperationTaskController {

    private final OperationTaskService operationTaskService;

    @Operation(summary = "调度列表", description = "分页/筛选：status/robot/keyword")
    @GetMapping
    public Result<PageResult<OperationTaskVO>> page(@Valid OperationTaskQuery query) {
        return Result.ok(operationTaskService.page(query));
    }

    @Operation(summary = "暂停调度")
    @PostMapping("/{id}/pause")
    public Result<OperationTaskActionResultVO> pause(
            @Parameter(description = "调度ID") @PathVariable Long id) {
        return Result.ok(operationTaskService.pause(id));
    }

    @Operation(summary = "恢复调度")
    @PostMapping("/{id}/resume")
    public Result<OperationTaskActionResultVO> resume(
            @Parameter(description = "调度ID") @PathVariable Long id) {
        return Result.ok(operationTaskService.resume(id));
    }

    @Operation(summary = "取消调度")
    @PostMapping("/{id}/cancel")
    public Result<OperationTaskActionResultVO> cancel(
            @Parameter(description = "调度ID") @PathVariable Long id) {
        return Result.ok(operationTaskService.cancel(id));
    }
}