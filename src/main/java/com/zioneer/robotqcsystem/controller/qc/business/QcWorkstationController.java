package com.zioneer.robotqcsystem.controller.qc.business;

import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.common.result.Result;
import com.zioneer.robotqcsystem.domain.dto.QcWorkstationCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.QcWorkstationUpdateDTO;
import com.zioneer.robotqcsystem.domain.vo.QcWorkstationConfigVO;
import com.zioneer.robotqcsystem.domain.vo.QcWorkstationVO;
import com.zioneer.robotqcsystem.service.qc.QcWorkstationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 质检业务-工作站管理（2.1.1）：GET/POST/PUT/DELETE /api/qc/workstations
 */
@Tag(name = "质检-工作站管理", description = "业务侧工作站列表与增删改")
@RestController
@RequestMapping("/api/qc/workstations")
@RequiredArgsConstructor
public class QcWorkstationController {

    private final QcWorkstationService service;

    @Operation(summary = "工作站列表")
    @GetMapping
    public Result<PageResult<QcWorkstationVO>> list(
            @Parameter(description = "关键词") @RequestParam(required = false) String keyword) {
        return Result.ok(service.listBusiness(keyword));
    }

    @Operation(summary = "新增工作站")
    @PostMapping
    public Result<QcWorkstationConfigVO> create(@RequestBody @Valid QcWorkstationCreateDTO dto) {
        return Result.ok(service.create(dto));
    }

    @Operation(summary = "更新工作站")
    @PutMapping("/{id}")
    public Result<Void> update(
            @Parameter(description = "工作站ID") @PathVariable Long id,
            @RequestBody @Valid QcWorkstationUpdateDTO dto) {
        service.update(id, dto);
        return Result.ok();
    }

    @Operation(summary = "删除工作站")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@Parameter(description = "工作站ID") @PathVariable Long id) {
        service.deleteById(id);
        return Result.ok();
    }
}
