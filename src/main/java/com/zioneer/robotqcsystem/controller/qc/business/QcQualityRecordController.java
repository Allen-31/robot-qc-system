package com.zioneer.robotqcsystem.controller.qc.business;

import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.common.result.Result;
import com.zioneer.robotqcsystem.domain.dto.QcQualityRecordQuery;
import com.zioneer.robotqcsystem.domain.vo.QcQualityRecordVO;
import com.zioneer.robotqcsystem.service.qc.QcQualityRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 质检业务-质检记录（2.1.4）
 */
@Tag(name = "质检-质检记录", description = "质检记录列表与详情")
@RestController
@RequestMapping("/api/qc/quality-records")
@RequiredArgsConstructor
public class QcQualityRecordController {

    private final QcQualityRecordService service;

    @Operation(summary = "质检记录列表（分页/筛选）")
    @GetMapping
    public Result<PageResult<QcQualityRecordVO>> page(@Valid QcQualityRecordQuery query) {
        return Result.ok(service.page(query));
    }

    @Operation(summary = "质检记录详情")
    @GetMapping("/{id}")
    public Result<QcQualityRecordVO> getById(
            @Parameter(description = "工单ID") @PathVariable Long id) {
        return Result.ok(service.getById(id));
    }
}
