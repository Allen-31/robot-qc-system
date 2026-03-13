package com.zioneer.robotqcsystem.controller.qc.config;

import com.zioneer.robotqcsystem.common.result.Result;
import com.zioneer.robotqcsystem.domain.dto.QcWorkstationCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.QcWorkstationUpdateDTO;
import com.zioneer.robotqcsystem.domain.vo.QcWorkstationConfigVO;
import com.zioneer.robotqcsystem.service.qc.QcWorkstationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 质检配置-工作站配置（2.2.1）
 */
@Tag(name = "质检配置-工作站配置", description = "工作站配置列表与增删改")
@RestController
@RequestMapping("/api/qc/config/workstations")
@RequiredArgsConstructor
public class QcConfigWorkstationController {

    private final QcWorkstationService service;

    @Operation(summary = "工作站配置列表")
    @GetMapping
    public Result<List<QcWorkstationConfigVO>> list(
            @Parameter(description = "关键词") @RequestParam(required = false) String keyword,
            @Parameter(description = "车间编码") @RequestParam(required = false) String workshopCode) {
        return Result.ok(service.listConfig(keyword, workshopCode));
    }

    @Operation(summary = "新增工作站配置")
    @PostMapping
    public Result<QcWorkstationConfigVO> create(@RequestBody @Valid QcWorkstationCreateDTO dto) {
        return Result.ok(service.create(dto));
    }

    @Operation(summary = "更新工作站配置")
    @PutMapping("/{id}")
    public Result<Void> update(
            @Parameter(description = "配置ID") @PathVariable Long id,
            @RequestBody @Valid QcWorkstationUpdateDTO dto) {
        service.update(id, dto);
        return Result.ok();
    }

    @Operation(summary = "删除工作站配置")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@Parameter(description = "配置ID") @PathVariable Long id) {
        service.deleteById(id);
        return Result.ok();
    }
}
