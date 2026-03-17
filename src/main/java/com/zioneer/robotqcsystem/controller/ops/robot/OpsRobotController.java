package com.zioneer.robotqcsystem.controller.ops.robot;

import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.common.result.Result;
import com.zioneer.robotqcsystem.domain.dto.OpsRobotDispatchModeDTO;
import com.zioneer.robotqcsystem.domain.dto.OpsRobotLiftDTO;
import com.zioneer.robotqcsystem.domain.dto.OpsRobotMapSwitchDTO;
import com.zioneer.robotqcsystem.domain.dto.OpsRobotModeSwitchDTO;
import com.zioneer.robotqcsystem.domain.dto.RobotQuery;
import com.zioneer.robotqcsystem.domain.vo.RobotVO;
import com.zioneer.robotqcsystem.service.deploy.robot.RobotService;
import com.zioneer.robotqcsystem.service.ops.OpsRobotService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * Ops robot controller.
 */
@Tag(name = "运营-机器人", description = "机器人列表与控制操作")
@RestController
@RequestMapping("/api/ops/robots")
@RequiredArgsConstructor
public class OpsRobotController {

    private final RobotService robotService;
    private final OpsRobotService opsRobotService;

    @Operation(summary = "机器人列表（复用 RobotService）")
    @GetMapping
    public Result<PageResult<RobotVO>> page(@Valid RobotQuery query) {
        return Result.ok(robotService.page(query));
    }

    @Operation(summary = "机器人详情（复用 RobotService）")
    @GetMapping("/{id}")
    public Result<RobotVO> detail(@PathVariable Long id) {
        return Result.ok(robotService.getById(id));
    }

    @Operation(summary = "切换调度模式")
    @PostMapping("/{id}/dispatch-mode")
    public Result<RobotVO> switchDispatchMode(@PathVariable Long id,
                                              @RequestBody @Valid OpsRobotDispatchModeDTO dto) {
        return Result.ok(opsRobotService.switchDispatchMode(id, dto));
    }

    @Operation(summary = "暂停机器人")
    @PostMapping("/{id}/pause")
    public Result<RobotVO> pause(@PathVariable Long id) {
        return Result.ok(opsRobotService.pause(id));
    }

    @Operation(summary = "恢复机器人")
    @PostMapping("/{id}/resume")
    public Result<RobotVO> resume(@PathVariable Long id) {
        return Result.ok(opsRobotService.resume(id));
    }

    @Operation(summary = "重置机器人")
    @PostMapping("/{id}/reset")
    public Result<RobotVO> reset(@PathVariable Long id) {
        return Result.ok(opsRobotService.reset(id));
    }

    @Operation(summary = "开始充电")
    @PostMapping("/{id}/charge/start")
    public Result<RobotVO> startCharge(@PathVariable Long id) {
        return Result.ok(opsRobotService.startCharge(id));
    }

    @Operation(summary = "取消充电")
    @PostMapping("/{id}/charge/cancel")
    public Result<RobotVO> cancelCharge(@PathVariable Long id) {
        return Result.ok(opsRobotService.cancelCharge(id));
    }

    @Operation(summary = "开始归巢")
    @PostMapping("/{id}/homing/start")
    public Result<RobotVO> startHoming(@PathVariable Long id) {
        return Result.ok(opsRobotService.startHoming(id));
    }

    @Operation(summary = "取消归巢")
    @PostMapping("/{id}/homing/cancel")
    public Result<RobotVO> cancelHoming(@PathVariable Long id) {
        return Result.ok(opsRobotService.cancelHoming(id));
    }

    @Operation(summary = "切换地图")
    @PostMapping("/{id}/map/switch")
    public Result<RobotVO> switchMap(@PathVariable Long id,
                                     @RequestBody @Valid OpsRobotMapSwitchDTO dto) {
        return Result.ok(opsRobotService.switchMap(id, dto));
    }

    @Operation(summary = "切换底盘模式")
    @PostMapping("/{id}/mode/chassis")
    public Result<RobotVO> switchChassisMode(@PathVariable Long id,
                                             @RequestBody @Valid OpsRobotModeSwitchDTO dto) {
        return Result.ok(opsRobotService.switchChassisMode(id, dto));
    }

    @Operation(summary = "切换机械臂模式")
    @PostMapping("/{id}/mode/arm")
    public Result<RobotVO> switchArmMode(@PathVariable Long id,
                                         @RequestBody @Valid OpsRobotModeSwitchDTO dto) {
        return Result.ok(opsRobotService.switchArmMode(id, dto));
    }

    @Operation(summary = "远程控制")
    @PostMapping("/{id}/remote-control")
    public Result<RobotVO> remoteControl(@PathVariable Long id) {
        return Result.ok(opsRobotService.remoteControl(id));
    }

    @Operation(summary = "机械臂抬升/下降")
    @PostMapping("/{id}/arm/lift")
    public Result<RobotVO> lift(@PathVariable Long id,
                                @RequestBody @Valid OpsRobotLiftDTO dto) {
        return Result.ok(opsRobotService.lift(id, dto));
    }

    @Operation(summary = "机械臂回原点")
    @PostMapping("/{id}/arm/back-to-origin")
    public Result<RobotVO> backToOrigin(@PathVariable Long id) {
        return Result.ok(opsRobotService.backToOrigin(id));
    }
}
