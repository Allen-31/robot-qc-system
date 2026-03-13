package com.zioneer.robotqcsystem.controller.qc.business;

import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.common.result.Result;
import com.zioneer.robotqcsystem.domain.dto.QcReinspectionRecordQuery;
import com.zioneer.robotqcsystem.domain.vo.QcReinspectionRecordVO;
import com.zioneer.robotqcsystem.service.qc.QcReinspectionRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 质检业务-复检记录（2.1.5，仅列表）
 */
@Tag(name = "质检-复检记录", description = "复检记录列表")
@RestController
@RequestMapping("/api/qc/reinspection-records")
@RequiredArgsConstructor
public class QcReinspectionRecordController {

    private final QcReinspectionRecordService service;

    @Operation(summary = "复检记录列表（分页/筛选）")
    @GetMapping
    public Result<PageResult<QcReinspectionRecordVO>> page(@Valid QcReinspectionRecordQuery query) {
        return Result.ok(service.page(query));
    }
}
