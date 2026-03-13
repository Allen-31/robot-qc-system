package com.zioneer.robotqcsystem.controller.deploy.robot;

import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.common.result.Result;
import com.zioneer.robotqcsystem.domain.dto.RobotHomingStrategyCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.RobotHomingStrategyQuery;
import com.zioneer.robotqcsystem.domain.dto.RobotHomingStrategyUpdateDTO;
import com.zioneer.robotqcsystem.domain.vo.RobotHomingStrategyVO;
import com.zioneer.robotqcsystem.service.deploy.robot.RobotHomingStrategyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 归位策略管理接口。
 */
@Tag(name = "归位策略管理", description = "机器人归位策略配置")
@RestController
@RequestMapping("/api/deploy/robot-homing-strategies")
@RequiredArgsConstructor
public class RobotHomingStrategyController {

    private final RobotHomingStrategyService robotHomingStrategyService;

    @Operation(summary = "分页查询归位策略")
    @GetMapping
    public Result<PageResult<RobotHomingStrategyVO>> page(@Valid RobotHomingStrategyQuery query) {
        return Result.ok(robotHomingStrategyService.page(query));
    }

    @Operation(summary = "查询归位策略详情")
    @GetMapping("/{code}")
    public Result<RobotHomingStrategyVO> getByCode(@Parameter(description = "策略编码") @PathVariable String code) {
        return Result.ok(robotHomingStrategyService.getByCode(code));
    }

    @Operation(summary = "新增归位策略")
    @PostMapping
    public Result<Void> create(@RequestBody @Valid RobotHomingStrategyCreateDTO dto) {
        robotHomingStrategyService.create(dto);
        return Result.ok();
    }

    @Operation(summary = "更新归位策略")
    @PutMapping("/{code}")
    public Result<Void> update(
            @Parameter(description = "策略编码") @PathVariable String code,
            @RequestBody @Valid RobotHomingStrategyUpdateDTO dto) {
        robotHomingStrategyService.update(code, dto);
        return Result.ok();
    }

    @Operation(summary = "删除归位策略")
    @DeleteMapping("/{code}")
    public Result<Void> delete(@Parameter(description = "策略编码") @PathVariable String code) {
        robotHomingStrategyService.deleteByCode(code);
        return Result.ok();
    }
}
