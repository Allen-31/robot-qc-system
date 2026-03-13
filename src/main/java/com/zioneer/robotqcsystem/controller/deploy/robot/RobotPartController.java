package com.zioneer.robotqcsystem.controller.deploy.robot;

import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.common.result.Result;
import com.zioneer.robotqcsystem.domain.dto.RobotPartCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.RobotPartQuery;
import com.zioneer.robotqcsystem.domain.dto.RobotPartUpdateDTO;
import com.zioneer.robotqcsystem.domain.vo.RobotPartVO;
import com.zioneer.robotqcsystem.service.RobotPartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 机器人零部件管理接口。
 */
@Tag(name = "机器人零部件管理", description = "机器人零部件与技术参数管理")
@RestController
@RequestMapping("/api/deploy/robot-parts")
@RequiredArgsConstructor
public class RobotPartController {

    private final RobotPartService robotPartService;

    @Operation(summary = "分页查询零部件")
    @GetMapping
    public Result<PageResult<RobotPartVO>> page(@Valid RobotPartQuery query) {
        return Result.ok(robotPartService.page(query));
    }

    @Operation(summary = "查询零部件详情")
    @GetMapping("/{id}")
    public Result<RobotPartVO> getById(@Parameter(description = "零部件ID") @PathVariable Long id) {
        return Result.ok(robotPartService.getById(id));
    }

    @Operation(summary = "新增零部件")
    @PostMapping
    public Result<Long> create(@RequestBody @Valid RobotPartCreateDTO dto) {
        return Result.ok(robotPartService.create(dto));
    }

    @Operation(summary = "更新零部件")
    @PutMapping("/{id}")
    public Result<Void> update(
            @Parameter(description = "零部件ID") @PathVariable Long id,
            @RequestBody @Valid RobotPartUpdateDTO dto) {
        robotPartService.update(id, dto);
        return Result.ok();
    }

    @Operation(summary = "删除零部件")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@Parameter(description = "零部件ID") @PathVariable Long id) {
        robotPartService.deleteById(id);
        return Result.ok();
    }
}
