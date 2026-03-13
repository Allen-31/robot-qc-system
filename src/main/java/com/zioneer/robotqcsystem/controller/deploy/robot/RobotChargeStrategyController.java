package com.zioneer.robotqcsystem.controller.deploy.robot;

import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.common.result.Result;
import com.zioneer.robotqcsystem.domain.dto.RobotChargeStrategyCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.RobotChargeStrategyQuery;
import com.zioneer.robotqcsystem.domain.dto.RobotChargeStrategyUpdateDTO;
import com.zioneer.robotqcsystem.domain.vo.RobotChargeStrategyVO;
import com.zioneer.robotqcsystem.service.deploy.robot.RobotChargeStrategyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 充电策略管理接口。
 */
@Tag(name = "充电策略管理", description = "机器人充电策略配置")
@RestController
@RequestMapping("/api/deploy/robot-charge-strategies")
@RequiredArgsConstructor
public class RobotChargeStrategyController {

    private final RobotChargeStrategyService robotChargeStrategyService;

    @Operation(summary = "分页查询充电策略")
    @GetMapping
    public Result<PageResult<RobotChargeStrategyVO>> page(@Valid RobotChargeStrategyQuery query) {
        return Result.ok(robotChargeStrategyService.page(query));
    }

    @Operation(summary = "查询充电策略详情")
    @GetMapping("/{code}")
    public Result<RobotChargeStrategyVO> getByCode(@Parameter(description = "策略编码") @PathVariable String code) {
        return Result.ok(robotChargeStrategyService.getByCode(code));
    }

    @Operation(summary = "新增充电策略")
    @PostMapping
    public Result<Void> create(@RequestBody @Valid RobotChargeStrategyCreateDTO dto) {
        robotChargeStrategyService.create(dto);
        return Result.ok();
    }

    @Operation(summary = "更新充电策略")
    @PutMapping("/{code}")
    public Result<Void> update(
            @Parameter(description = "策略编码") @PathVariable String code,
            @RequestBody @Valid RobotChargeStrategyUpdateDTO dto) {
        robotChargeStrategyService.update(code, dto);
        return Result.ok();
    }

    @Operation(summary = "删除充电策略")
    @DeleteMapping("/{code}")
    public Result<Void> delete(@Parameter(description = "策略编码") @PathVariable String code) {
        robotChargeStrategyService.deleteByCode(code);
        return Result.ok();
    }
}
