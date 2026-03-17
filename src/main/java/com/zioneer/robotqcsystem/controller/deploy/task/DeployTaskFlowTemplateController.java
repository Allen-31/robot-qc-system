package com.zioneer.robotqcsystem.controller.deploy.task;

import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.common.result.Result;
import com.zioneer.robotqcsystem.domain.dto.DeployTaskFlowTemplateCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.DeployTaskFlowTemplateQuery;
import com.zioneer.robotqcsystem.domain.dto.DeployTaskFlowTemplateUpdateDTO;
import com.zioneer.robotqcsystem.domain.vo.DeployTaskFlowTemplateVO;
import com.zioneer.robotqcsystem.service.deploy.task.DeployTaskFlowTemplateService;
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
 * 部署配置-任务-任务流模板。
 */
@Tag(name = "任务流模板", description = "部署配置-任务-任务流模板")
@RestController
@RequestMapping("/api/deploy/task-flow-templates")
@RequiredArgsConstructor
public class DeployTaskFlowTemplateController {

    private final DeployTaskFlowTemplateService taskFlowTemplateService;

    @Operation(summary = "任务流模板列表")
    @GetMapping
    public Result<PageResult<DeployTaskFlowTemplateVO>> page(@Valid DeployTaskFlowTemplateQuery query) {
        return Result.ok(taskFlowTemplateService.page(query));
    }

    @Operation(summary = "新增任务流模板")
    @PostMapping
    public Result<DeployTaskFlowTemplateVO> create(@RequestBody @Valid DeployTaskFlowTemplateCreateDTO dto) {
        return Result.ok(taskFlowTemplateService.create(dto));
    }

    @Operation(summary = "更新任务流模板")
    @PutMapping("/{code}")
    public Result<Void> update(
            @Parameter(description = "模板编码") @PathVariable String code,
            @RequestBody @Valid DeployTaskFlowTemplateUpdateDTO dto) {
        taskFlowTemplateService.update(code, dto);
        return Result.ok();
    }

    @Operation(summary = "删除任务流模板")
    @DeleteMapping("/{code}")
    public Result<Void> delete(@Parameter(description = "模板编码") @PathVariable String code) {
        taskFlowTemplateService.deleteByCode(code);
        return Result.ok();
    }
}