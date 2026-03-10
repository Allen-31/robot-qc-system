package com.zioneer.robotqcsystem.controller;

import com.zioneer.robotqcsystem.common.page.PageQuery;
import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.common.result.Result;
import com.zioneer.robotqcsystem.domain.entity.Robot;
import com.zioneer.robotqcsystem.service.RobotService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 机器人管理接口
 */
@Tag(name = "机器人管理", description = "机器人信息的增删改查")
@RestController
@RequestMapping("/api/robot")
@RequiredArgsConstructor
public class RobotController {

    private final RobotService robotService;

    @Operation(summary = "根据ID查询机器人")
    @GetMapping("/{id}")
    public Result<Robot> getById(
            @Parameter(description = "机器人ID") @PathVariable Long id) {
        return Result.ok(robotService.getById(id));
    }

    @Operation(summary = "分页查询机器人列表")
    @GetMapping("/page")
    public Result<PageResult<Robot>> page(
            @Parameter(description = "机器人编码(模糊)") @RequestParam(required = false) String robotCode,
            @Parameter(description = "状态") @RequestParam(required = false) String status,
            @Valid PageQuery pageQuery) {
        return Result.ok(robotService.page(robotCode, status, pageQuery));
    }

    @Operation(summary = "新增机器人")
    @PostMapping
    public Result<Long> create(@RequestBody @Valid Robot robot) {
        return Result.ok(robotService.create(robot));
    }

    @Operation(summary = "更新机器人")
    @PutMapping
    public Result<Void> update(@RequestBody @Valid Robot robot) {
        robotService.update(robot);
        return Result.ok();
    }

    @Operation(summary = "删除机器人")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@Parameter(description = "机器人ID") @PathVariable Long id) {
        robotService.deleteById(id);
        return Result.ok();
    }
}
