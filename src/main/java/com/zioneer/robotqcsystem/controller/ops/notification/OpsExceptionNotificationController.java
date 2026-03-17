package com.zioneer.robotqcsystem.controller.ops.notification;

import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.common.result.Result;
import com.zioneer.robotqcsystem.domain.dto.OpsExceptionNotificationCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.OpsExceptionNotificationQuery;
import com.zioneer.robotqcsystem.domain.dto.OpsExceptionNotificationStatusDTO;
import com.zioneer.robotqcsystem.domain.vo.OpsExceptionNotificationVO;
import com.zioneer.robotqcsystem.service.ops.OpsExceptionNotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * Exception notification controller.
 */
@Tag(name = "运营-异常通知", description = "异常通知管理")
@RestController
@RequestMapping("/api/ops/exception-notifications")
@RequiredArgsConstructor
public class OpsExceptionNotificationController {

    private final OpsExceptionNotificationService notificationService;

    @Operation(summary = "异常通知分页")
    @GetMapping
    public Result<PageResult<OpsExceptionNotificationVO>> page(@Valid OpsExceptionNotificationQuery query) {
        return Result.ok(notificationService.page(query));
    }

    @Operation(summary = "创建异常通知")
    @PostMapping
    public Result<Long> create(@RequestBody @Valid OpsExceptionNotificationCreateDTO dto) {
        return Result.ok(notificationService.create(dto));
    }

    @Operation(summary = "更新异常通知状态")
    @PutMapping("/{id}/status")
    public Result<Void> updateStatus(@PathVariable Long id,
                                     @RequestBody @Valid OpsExceptionNotificationStatusDTO dto) {
        notificationService.updateStatus(id, dto.getStatus());
        return Result.ok();
    }
}
