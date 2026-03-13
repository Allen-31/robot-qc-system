package com.zioneer.robotqcsystem.controller.qc.business;

import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.common.result.Result;
import com.zioneer.robotqcsystem.domain.vo.QcStationPositionVO;
import com.zioneer.robotqcsystem.domain.vo.QcStationRobotVO;
import com.zioneer.robotqcsystem.domain.vo.QcStationSummaryVO;
import com.zioneer.robotqcsystem.service.qc.QcStationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 质检业务-工位管理（2.1.2）：station-positions、stations/{stationId}/robots、stations/{stationId}/statistics
 */
@Tag(name = "质检-工位管理", description = "工位列表、质检台机器人、质检台统计")
@RestController
@RequestMapping("/api/qc")
@RequiredArgsConstructor
public class QcStationPositionController {

    private final QcStationService service;

    @Operation(summary = "工位列表")
    @GetMapping("/station-positions")
    public Result<PageResult<QcStationPositionVO>> listPositions(
            @Parameter(description = "工作站ID") @RequestParam(required = false) String workstationId) {
        return Result.ok(service.listPositions(workstationId));
    }

    @Operation(summary = "当前质检台关联机器人")
    @GetMapping("/stations/{stationId}/robots")
    public Result<List<QcStationRobotVO>> listRobots(
            @Parameter(description = "质检台/工位编号") @PathVariable String stationId) {
        return Result.ok(service.listRobotsByStationId(stationId));
    }

    @Operation(summary = "当前质检台统计")
    @GetMapping("/stations/{stationId}/statistics")
    public Result<QcStationSummaryVO> getStationSummary(
            @Parameter(description = "质检台编号") @PathVariable String stationId,
            @Parameter(description = "时间维度 day/week") @RequestParam(required = false) String dimension,
            @Parameter(description = "基准日期 yyyy-MM-dd") @RequestParam(required = false) String date) {
        return Result.ok(service.getStationSummary(stationId, dimension, date));
    }
}
