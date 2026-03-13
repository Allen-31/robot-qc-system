package com.zioneer.robotqcsystem.controller.deploy.robot;

import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.common.result.Result;
import com.zioneer.robotqcsystem.domain.dto.RobotGroupCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.RobotGroupQuery;
import com.zioneer.robotqcsystem.domain.dto.RobotGroupUpdateDTO;
import com.zioneer.robotqcsystem.domain.vo.RobotGroupVO;
import com.zioneer.robotqcsystem.service.RobotGroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 机器人分组管理接口。
 */
@Tag(name = "机器人分组管理", description = "机器人分组的增删改查")
@RestController
@RequestMapping("/api/deploy/robot-groups")
@RequiredArgsConstructor
public class RobotGroupController {

    private final RobotGroupService robotGroupService;

    @Operation(summary = "分页查询分组")
    @GetMapping
    public Result<PageResult<RobotGroupVO>> page(@Valid RobotGroupQuery query) {
        return Result.ok(robotGroupService.page(query));
    }

    @Operation(summary = "查询分组详情")
    @GetMapping("/{id}")
    public Result<RobotGroupVO> getById(@Parameter(description = "分组ID") @PathVariable Long id) {
        return Result.ok(robotGroupService.getById(id));
    }

    @Operation(summary = "新增分组")
    @PostMapping
    public Result<Long> create(@RequestBody @Valid RobotGroupCreateDTO dto) {
        return Result.ok(robotGroupService.create(dto));
    }

    @Operation(summary = "更新分组")
    @PutMapping("/{id}")
    public Result<Void> update(
            @Parameter(description = "分组ID") @PathVariable Long id,
            @RequestBody @Valid RobotGroupUpdateDTO dto) {
        robotGroupService.update(id, dto);
        return Result.ok();
    }

    @Operation(summary = "删除分组")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@Parameter(description = "分组ID") @PathVariable Long id) {
        robotGroupService.deleteById(id);
        return Result.ok();
    }
}
