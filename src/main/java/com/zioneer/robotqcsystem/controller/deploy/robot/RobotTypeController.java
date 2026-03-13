package com.zioneer.robotqcsystem.controller.deploy.robot;

import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.common.result.Result;
import com.zioneer.robotqcsystem.domain.dto.RobotTypeCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.RobotTypeQuery;
import com.zioneer.robotqcsystem.domain.dto.RobotTypeUpdateDTO;
import com.zioneer.robotqcsystem.domain.vo.RobotTypeVO;
import com.zioneer.robotqcsystem.service.deploy.robot.RobotTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 机器人类型管理接口。
 */
@Tag(name = "机器人类型管理", description = "机器人类型与结构图标注管理")
@RestController
@RequestMapping("/api/deploy/robot-types")
@RequiredArgsConstructor
public class RobotTypeController {

    private final RobotTypeService robotTypeService;

    @Operation(summary = "分页查询机器人类型")
    @GetMapping
    public Result<PageResult<RobotTypeVO>> page(@Valid RobotTypeQuery query) {
        return Result.ok(robotTypeService.page(query));
    }

    @Operation(summary = "查询机器人类型详情")
    @GetMapping("/{id}")
    public Result<RobotTypeVO> getById(@Parameter(description = "类型ID") @PathVariable Long id) {
        return Result.ok(robotTypeService.getById(id));
    }

    @Operation(summary = "新增机器人类型")
    @PostMapping
    public Result<Long> create(@RequestBody @Valid RobotTypeCreateDTO dto) {
        return Result.ok(robotTypeService.create(dto));
    }

    @Operation(summary = "更新机器人类型")
    @PutMapping("/{id}")
    public Result<Void> update(
            @Parameter(description = "类型ID") @PathVariable Long id,
            @RequestBody @Valid RobotTypeUpdateDTO dto) {
        robotTypeService.update(id, dto);
        return Result.ok();
    }

    @Operation(summary = "删除机器人类型")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@Parameter(description = "类型ID") @PathVariable Long id) {
        robotTypeService.deleteById(id);
        return Result.ok();
    }
}
