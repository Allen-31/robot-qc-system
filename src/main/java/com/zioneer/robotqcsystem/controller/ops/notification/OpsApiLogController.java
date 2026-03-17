package com.zioneer.robotqcsystem.controller.ops.notification;

import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.common.result.Result;
import com.zioneer.robotqcsystem.domain.dto.OpsApiLogQuery;
import com.zioneer.robotqcsystem.domain.vo.OpsApiLogVO;
import com.zioneer.robotqcsystem.service.ops.OpsApiLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * API log controller.
 */
@Tag(name = "运营-接口日志", description = "接口日志查询")
@RestController
@RequestMapping("/api/ops/logs/api")
@RequiredArgsConstructor
public class OpsApiLogController {

    private final OpsApiLogService apiLogService;

    @Operation(summary = "接口日志分页")
    @GetMapping
    public Result<PageResult<OpsApiLogVO>> page(@Valid OpsApiLogQuery query) {
        return Result.ok(apiLogService.page(query));
    }
}
