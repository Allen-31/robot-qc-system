package com.zioneer.robotqcsystem.controller.qc.config;

import com.zioneer.robotqcsystem.common.result.Result;
import com.zioneer.robotqcsystem.domain.dto.QcWorkshopCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.QcWorkshopUpdateDTO;
import com.zioneer.robotqcsystem.domain.vo.QcWorkshopVO;
import com.zioneer.robotqcsystem.service.qc.QcWorkshopService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 质检配置-车间配置（2.2.5）
 */
@Tag(name = "质检配置-车间配置", description = "车间配置列表与增删改")
@RestController
@RequestMapping("/api/qc/config/workshops")
@RequiredArgsConstructor
public class QcConfigWorkshopController {

    private final QcWorkshopService service;

    @Operation(summary = "车间配置列表")
    @GetMapping
    public Result<List<QcWorkshopVO>> list() {
        return Result.ok(service.list());
    }

    @Operation(summary = "新增车间配置")
    @PostMapping
    public Result<QcWorkshopVO> create(@RequestBody @Valid QcWorkshopCreateDTO dto) {
        return Result.ok(service.create(dto));
    }

    @Operation(summary = "更新车间配置")
    @PutMapping("/{code}")
    public Result<Void> update(
            @Parameter(description = "车间编码") @PathVariable String code,
            @RequestBody @Valid QcWorkshopUpdateDTO dto) {
        service.update(code, dto);
        return Result.ok();
    }

    @Operation(summary = "删除车间配置")
    @DeleteMapping("/{code}")
    public Result<Void> delete(@Parameter(description = "车间编码") @PathVariable String code) {
        service.deleteByCode(code);
        return Result.ok();
    }
}
