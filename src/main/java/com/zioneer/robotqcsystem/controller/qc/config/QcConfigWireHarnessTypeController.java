package com.zioneer.robotqcsystem.controller.qc.config;

import com.zioneer.robotqcsystem.common.result.Result;
import com.zioneer.robotqcsystem.domain.dto.QcWireHarnessTypeCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.QcWireHarnessTypeUpdateDTO;
import com.zioneer.robotqcsystem.domain.vo.QcWireHarnessTypeVO;
import com.zioneer.robotqcsystem.service.qc.QcWireHarnessTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 质检配置-线束类型（2.2.3）
 */
@Tag(name = "质检配置-线束类型", description = "线束类型列表与增删改")
@RestController
@RequestMapping("/api/qc/config/wire-harness-types")
@RequiredArgsConstructor
public class QcConfigWireHarnessTypeController {

    private final QcWireHarnessTypeService service;

    @Operation(summary = "线束类型列表")
    @GetMapping
    public Result<List<QcWireHarnessTypeVO>> list() {
        return Result.ok(service.list());
    }

    @Operation(summary = "新增线束类型")
    @PostMapping
    public Result<QcWireHarnessTypeVO> create(@RequestBody @Valid QcWireHarnessTypeCreateDTO dto) {
        return Result.ok(service.create(dto));
    }

    @Operation(summary = "更新线束类型")
    @PutMapping("/{id}")
    public Result<Void> update(
            @Parameter(description = "线束类型ID") @PathVariable Long id,
            @RequestBody @Valid QcWireHarnessTypeUpdateDTO dto) {
        service.update(id, dto);
        return Result.ok();
    }

    @Operation(summary = "删除线束类型")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@Parameter(description = "线束类型ID") @PathVariable Long id) {
        service.deleteById(id);
        return Result.ok();
    }
}
