package com.zioneer.robotqcsystem.controller.deploy.config;

import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.common.result.Result;
import com.zioneer.robotqcsystem.domain.dto.SceneDeviceCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.SceneDeviceQuery;
import com.zioneer.robotqcsystem.domain.dto.SceneDeviceUpdateDTO;
import com.zioneer.robotqcsystem.domain.vo.SceneDeviceVO;
import com.zioneer.robotqcsystem.service.deploy.scene.SceneDeviceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 场景设备管理接口。
 */
@Tag(name = "场景设备管理", description = "场景设备管理与状态查看")
@RestController
@RequestMapping("/api/deploy/scene-devices")
@RequiredArgsConstructor
public class SceneDeviceController {

    private final SceneDeviceService sceneDeviceService;

    @Operation(summary = "分页查询场景设备")
    @GetMapping
    public Result<PageResult<SceneDeviceVO>> page(@Valid SceneDeviceQuery query) {
        return Result.ok(sceneDeviceService.page(query));
    }

    @Operation(summary = "查询场景设备详情")
    @GetMapping("/{code}")
    public Result<SceneDeviceVO> getByCode(@Parameter(description = "设备编码") @PathVariable String code) {
        return Result.ok(sceneDeviceService.getByCode(code));
    }

    @Operation(summary = "新增场景设备")
    @PostMapping
    public Result<Void> create(@RequestBody @Valid SceneDeviceCreateDTO dto) {
        sceneDeviceService.create(dto);
        return Result.ok();
    }

    @Operation(summary = "更新场景设备")
    @PutMapping("/{code}")
    public Result<Void> update(
            @Parameter(description = "设备编码") @PathVariable String code,
            @RequestBody @Valid SceneDeviceUpdateDTO dto) {
        sceneDeviceService.update(code, dto);
        return Result.ok();
    }

    @Operation(summary = "删除场景设备")
    @DeleteMapping("/{code}")
    public Result<Void> delete(@Parameter(description = "设备编码") @PathVariable String code) {
        sceneDeviceService.deleteByCode(code);
        return Result.ok();
    }
}
