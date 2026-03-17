package com.zioneer.robotqcsystem.controller.ops.notification;

import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.common.result.Result;
import com.zioneer.robotqcsystem.domain.dto.OpsOperationLogQuery;
import com.zioneer.robotqcsystem.domain.vo.OpsOperationLogVO;
import com.zioneer.robotqcsystem.service.ops.OpsOperationLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Operation log controller.
 */
@Tag(name = "运营-操作日志", description = "操作日志查询")
@RestController
@RequestMapping("/api/ops/logs/operation")
@RequiredArgsConstructor
public class OpsOperationLogController {

    private final OpsOperationLogService operationLogService;

    @Operation(summary = "操作日志分页")
    @GetMapping
    public Result<PageResult<OpsOperationLogVO>> page(@Valid OpsOperationLogQuery query) {
        return Result.ok(operationLogService.page(query));
    }
}
