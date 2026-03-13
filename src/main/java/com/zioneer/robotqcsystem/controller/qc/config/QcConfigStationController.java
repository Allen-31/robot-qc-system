package com.zioneer.robotqcsystem.controller.qc.config;

import com.zioneer.robotqcsystem.common.result.Result;
import com.zioneer.robotqcsystem.domain.dto.QcStationConfigCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.QcStationConfigUpdateDTO;
import com.zioneer.robotqcsystem.domain.vo.QcStationConfigVO;
import com.zioneer.robotqcsystem.service.qc.QcStationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 质检配置-工位配置（2.2.2）
 */
@Tag(name = "质检配置-工位配置", description = "工位配置列表与增删改")
@RestController
@RequestMapping("/api/qc/config/stations")
@RequiredArgsConstructor
public class QcConfigStationController {

    private final QcStationService service;

    @Operation(summary = "工位配置列表")
    @GetMapping
    public Result<List<QcStationConfigVO>> list(
            @Parameter(description = "工作站ID") @RequestParam(required = false) String workstationId) {
        return Result.ok(service.listConfig(workstationId));
    }

    @Operation(summary = "新增工位配置")
    @PostMapping
    public Result<QcStationConfigVO> create(@RequestBody @Valid QcStationConfigCreateDTO dto) {
        return Result.ok(service.createConfig(dto));
    }

    @Operation(summary = "更新工位配置")
    @PutMapping("/{id}")
    public Result<Void> update(
            @Parameter(description = "工位主键ID") @PathVariable Long id,
            @RequestBody @Valid QcStationConfigUpdateDTO dto) {
        service.updateConfig(id, dto);
        return Result.ok();
    }

    @Operation(summary = "删除工位配置")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@Parameter(description = "工位主键ID") @PathVariable Long id) {
        service.deleteConfigById(id);
        return Result.ok();
    }
}
