package com.zioneer.robotqcsystem.controller.deploy.task;

import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.common.result.Result;
import com.zioneer.robotqcsystem.domain.dto.DeployTaskTemplateCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.DeployTaskTemplateQuery;
import com.zioneer.robotqcsystem.domain.dto.DeployTaskTemplateUpdateDTO;
import com.zioneer.robotqcsystem.domain.vo.DeployTaskTemplateVO;
import com.zioneer.robotqcsystem.service.deploy.task.DeployTaskTemplateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 部署配置-任务-任务模板。
 */
@Tag(name = "任务模板", description = "部署配置-任务-任务模板")
@RestController
@RequestMapping("/api/deploy/task-templates")
@RequiredArgsConstructor
public class DeployTaskTemplateController {

    private final DeployTaskTemplateService taskTemplateService;

    @Operation(summary = "任务模板列表")
    @GetMapping
    public Result<PageResult<DeployTaskTemplateVO>> page(@Valid DeployTaskTemplateQuery query) {
        return Result.ok(taskTemplateService.page(query));
    }

    @Operation(summary = "新增任务模板")
    @PostMapping
    public Result<DeployTaskTemplateVO> create(@RequestBody @Valid DeployTaskTemplateCreateDTO dto) {
        return Result.ok(taskTemplateService.create(dto));
    }

    @Operation(summary = "更新任务模板")
    @PutMapping("/{code}")
    public Result<Void> update(
            @Parameter(description = "模板编码") @PathVariable String code,
            @RequestBody @Valid DeployTaskTemplateUpdateDTO dto) {
        taskTemplateService.update(code, dto);
        return Result.ok();
    }

    @Operation(summary = "删除任务模板")
    @DeleteMapping("/{code}")
    public Result<Void> delete(@Parameter(description = "模板编码") @PathVariable String code) {
        taskTemplateService.deleteByCode(code);
        return Result.ok();
    }
}