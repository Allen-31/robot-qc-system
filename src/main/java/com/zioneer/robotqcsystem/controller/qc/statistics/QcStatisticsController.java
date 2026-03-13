package com.zioneer.robotqcsystem.controller.qc.statistics;

import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.common.result.Result;
import com.zioneer.robotqcsystem.domain.dto.QcStatisticsQuery;
import com.zioneer.robotqcsystem.domain.vo.QcStationSummaryVO;
import com.zioneer.robotqcsystem.domain.vo.QcStatisticsRowVO;
import com.zioneer.robotqcsystem.domain.vo.QcStatisticsSummaryVO;
import com.zioneer.robotqcsystem.service.qc.QcStationService;
import com.zioneer.robotqcsystem.service.qc.QcStatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 质检统计（2.3.1）、质检报表（2.3.2 可与 2.3.1 共用）
 */
@Tag(name = "质检统计", description = "质检统计数据、质检区指标汇总、质检台汇总")
@RestController
@RequestMapping("/api/qc/statistics")
@RequiredArgsConstructor
public class QcStatisticsController {

    private final QcStatisticsService statisticsService;
    private final QcStationService stationService;

    @Operation(summary = "质检统计列表（分页）")
    @GetMapping
    public Result<PageResult<QcStatisticsRowVO>> page(@Valid QcStatisticsQuery query) {
        return Result.ok(statisticsService.page(query));
    }

    @Operation(summary = "质检区指标汇总（按日/周）")
    @GetMapping("/summary")
    public Result<QcStatisticsSummaryVO> summary(
            @Parameter(description = "质检区ID/编码") @RequestParam(required = false) String workstationId,
            @Parameter(description = "时间维度 day/week") @RequestParam(defaultValue = "day") String dimension,
            @Parameter(description = "基准日期 yyyy-MM-dd") @RequestParam(required = false) String date) {
        return Result.ok(statisticsService.summary(workstationId, dimension, date));
    }

    @Operation(summary = "质检台统计汇总（与 stations/{stationId}/statistics 同义）")
    @GetMapping("/station-summary")
    public Result<QcStationSummaryVO> stationSummary(
            @Parameter(description = "质检台编号") @RequestParam String stationId,
            @Parameter(description = "时间维度 day/week") @RequestParam(required = false) String dimension,
            @Parameter(description = "基准日期 yyyy-MM-dd") @RequestParam(required = false) String date) {
        return Result.ok(stationService.getStationSummary(stationId, dimension, date));
    }
}
