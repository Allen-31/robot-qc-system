package com.zioneer.robotqcsystem.controller.qc.config;

import com.zioneer.robotqcsystem.common.result.Result;
import com.zioneer.robotqcsystem.domain.dto.QcTerminalCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.QcTerminalUpdateDTO;
import com.zioneer.robotqcsystem.domain.vo.QcTerminalVO;
import com.zioneer.robotqcsystem.service.qc.QcTerminalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 质检配置-终端配置（2.2.4）
 */
@Tag(name = "质检配置-终端配置", description = "终端配置列表与增删改")
@RestController
@RequestMapping("/api/qc/config/terminals")
@RequiredArgsConstructor
public class QcConfigTerminalController {

    private final QcTerminalService service;

    @Operation(summary = "终端配置列表")
    @GetMapping
    public Result<List<QcTerminalVO>> list(
            @Parameter(description = "工作站ID") @RequestParam(required = false) String workstationId) {
        return Result.ok(service.list(workstationId));
    }

    @Operation(summary = "新增终端配置")
    @PostMapping
    public Result<QcTerminalVO> create(@RequestBody @Valid QcTerminalCreateDTO dto) {
        return Result.ok(service.create(dto));
    }

    @Operation(summary = "更新终端配置")
    @PutMapping("/{id}")
    public Result<Void> update(
            @Parameter(description = "终端ID") @PathVariable Long id,
            @RequestBody @Valid QcTerminalUpdateDTO dto) {
        service.update(id, dto);
        return Result.ok();
    }

    @Operation(summary = "删除终端配置")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@Parameter(description = "终端ID") @PathVariable Long id) {
        service.deleteById(id);
        return Result.ok();
    }

    @Operation(summary = "按编码更新终端配置（避免前端 Long 精度丢失）")
    @PutMapping("/by-code/{code}")
    public Result<Void> updateByCode(
            @Parameter(description = "终端编码") @PathVariable String code,
            @RequestBody @Valid QcTerminalUpdateDTO dto) {
        service.updateByCode(code, dto);
        return Result.ok();
    }

    @Operation(summary = "按编码删除终端配置（避免前端 Long 精度丢失）")
    @DeleteMapping("/by-code/{code}")
    public Result<Void> deleteByCode(@Parameter(description = "终端编码") @PathVariable String code) {
        service.deleteByCode(code);
        return Result.ok();
    }
}
