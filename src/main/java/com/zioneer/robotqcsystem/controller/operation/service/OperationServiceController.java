package com.zioneer.robotqcsystem.controller.operation.service;

import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.common.result.Result;
import com.zioneer.robotqcsystem.domain.dto.OperationServiceLogQuery;
import com.zioneer.robotqcsystem.domain.dto.OperationServiceQuery;
import com.zioneer.robotqcsystem.domain.vo.OperationServiceLogVO;
import com.zioneer.robotqcsystem.domain.vo.OperationServiceVO;
import com.zioneer.robotqcsystem.service.operation.OperationServiceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * Operation service management APIs.
 */
@Tag(name = "服务管理", description = "运营-服务管理")
@RestController
@RequestMapping("/api/operation/services")
@RequiredArgsConstructor
public class OperationServiceController {

    private final OperationServiceService operationServiceService;

    @Operation(summary = "服务列表", description = "分页/筛选：keyword/type/version/ip/status")
    @GetMapping
    public Result<PageResult<OperationServiceVO>> page(@Valid OperationServiceQuery query) {
        return Result.ok(operationServiceService.page(query));
    }

    @Operation(summary = "启动服务")
    @PostMapping("/{id}/start")
    public Result<OperationServiceVO> start(@Parameter(description = "服务ID") @PathVariable Long id) {
        return Result.ok(operationServiceService.start(id));
    }

    @Operation(summary = "停止服务")
    @PostMapping("/{id}/stop")
    public Result<OperationServiceVO> stop(@Parameter(description = "服务ID") @PathVariable Long id) {
        return Result.ok(operationServiceService.stop(id));
    }

    @Operation(summary = "重启服务")
    @PostMapping("/{id}/restart")
    public Result<OperationServiceVO> restart(@Parameter(description = "服务ID") @PathVariable Long id) {
        return Result.ok(operationServiceService.restart(id));
    }

    @Operation(summary = "服务日志")
    @GetMapping("/{id}/logs")
    public Result<PageResult<OperationServiceLogVO>> logs(
            @Parameter(description = "服务ID") @PathVariable Long id,
            @Valid OperationServiceLogQuery query) {
        return Result.ok(operationServiceService.logPage(id, query));
    }
}
