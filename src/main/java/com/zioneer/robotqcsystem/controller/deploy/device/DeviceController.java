package com.zioneer.robotqcsystem.controller.deploy.device;

import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.common.result.Result;
import com.zioneer.robotqcsystem.domain.dto.DeployDeviceCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.DeployDeviceUpdateDTO;
import com.zioneer.robotqcsystem.domain.vo.DeployDeviceVO;
import com.zioneer.robotqcsystem.service.deploy.DeviceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 设备管理（3.3.2）：呼叫盒等设备列表与增删改
 */
@Tag(name = "设备管理", description = "部署-设备列表、新增、更新、删除")
@RestController
@RequestMapping("/api/deploy/devices")
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceService deviceService;

    @Operation(summary = "设备列表", description = "按 mapCode 筛选，返回 list 与 total")
    @GetMapping
    public Result<PageResult<DeployDeviceVO>> list(
            @Parameter(description = "地图编码") @RequestParam(required = false) String mapCode) {
        return Result.ok(deviceService.list(mapCode));
    }

    @Operation(summary = "新增设备")
    @PostMapping
    public Result<DeployDeviceVO> create(@RequestBody @Valid DeployDeviceCreateDTO dto) {
        return Result.ok(deviceService.create(dto));
    }

    @Operation(summary = "更新设备")
    @PutMapping("/{id}")
    public Result<Void> update(
            @Parameter(description = "设备主键ID（Snowflake）") @PathVariable Long id,
            @RequestBody @Valid DeployDeviceUpdateDTO dto) {
        deviceService.update(id, dto);
        return Result.ok();
    }

    @Operation(summary = "删除设备")
    @DeleteMapping("/{id}")
    public Result<Void> delete(
            @Parameter(description = "设备主键ID（Snowflake）") @PathVariable Long id) {
        deviceService.deleteById(id);
        return Result.ok();
    }
}
