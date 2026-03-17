package com.zioneer.robotqcsystem.controller.ops.notification;

import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.common.result.Result;
import com.zioneer.robotqcsystem.domain.dto.OpsLoginLogQuery;
import com.zioneer.robotqcsystem.domain.vo.OpsLoginLogVO;
import com.zioneer.robotqcsystem.service.ops.OpsLoginLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Login log controller.
 */
@Tag(name = "运营-登录日志", description = "登录日志查询")
@RestController
@RequestMapping("/api/ops/logs/login")
@RequiredArgsConstructor
public class OpsLoginLogController {

    private final OpsLoginLogService loginLogService;

    @Operation(summary = "登录日志分页")
    @GetMapping
    public Result<PageResult<OpsLoginLogVO>> page(@Valid OpsLoginLogQuery query) {
        return Result.ok(loginLogService.page(query));
    }
}
