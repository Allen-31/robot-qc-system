package com.zioneer.robotqcsystem.controller.deploy.robot;

import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.common.result.Result;
import com.zioneer.robotqcsystem.domain.dto.RobotCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.RobotQuery;
import com.zioneer.robotqcsystem.domain.dto.RobotUpdateDTO;
import com.zioneer.robotqcsystem.domain.vo.RobotVO;
import com.zioneer.robotqcsystem.service.RobotService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 机器人管理（deploy-robot 模块：机器人类型/基础数据）
 */
@Tag(name = "机器人管理", description = "机器人信息的增删改查")
@RestController
@RequestMapping("/api/deploy/robots")
@RequiredArgsConstructor
public class RobotController {

    private final RobotService robotService;

    @Operation(summary = "分页查询机器人列表")
    @GetMapping
    public Result<PageResult<RobotVO>> page(@Valid RobotQuery query) {
        return Result.ok(robotService.page(query));
    }

    @Operation(summary = "根据ID查询机器人")
    @GetMapping("/{id}")
    public Result<RobotVO> getById(
            @Parameter(description = "机器人ID") @PathVariable Long id) {
        return Result.ok(robotService.getById(id));
    }

    @Operation(summary = "新增机器人")
    @PostMapping
    public Result<Long> create(@RequestBody @Valid RobotCreateDTO dto) {
        return Result.ok(robotService.create(dto));
    }

    @Operation(summary = "更新机器人")
    @PutMapping("/{id}")
    public Result<Void> update(
            @Parameter(description = "机器人ID") @PathVariable Long id,
            @RequestBody @Valid RobotUpdateDTO dto) {
        robotService.update(id, dto);
        return Result.ok();
    }

    @Operation(summary = "删除机器人")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@Parameter(description = "机器人ID") @PathVariable Long id) {
        robotService.deleteById(id);
        return Result.ok();
    }
}
