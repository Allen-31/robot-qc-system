package com.zioneer.robotqcsystem.controller.deploy.config;

import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.common.result.Result;
import com.zioneer.robotqcsystem.domain.dto.SceneMapCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.SceneMapQuery;
import com.zioneer.robotqcsystem.domain.dto.SceneMapUpdateDTO;
import com.zioneer.robotqcsystem.domain.vo.SceneMapVO;
import com.zioneer.robotqcsystem.service.SceneMapService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 场景地图管理接口。
 */
@Tag(name = "场景地图管理", description = "地图管理与发布")
@RestController
@RequestMapping("/api/deploy/maps")
@RequiredArgsConstructor
public class SceneMapController {

    private final SceneMapService sceneMapService;

    @Operation(summary = "分页查询地图")
    @GetMapping
    public Result<PageResult<SceneMapVO>> page(@Valid SceneMapQuery query) {
        return Result.ok(sceneMapService.page(query));
    }

    @Operation(summary = "查询地图详情")
    @GetMapping("/{code}")
    public Result<SceneMapVO> getByCode(@Parameter(description = "地图编码") @PathVariable String code) {
        return Result.ok(sceneMapService.getByCode(code));
    }

    @Operation(summary = "新增地图")
    @PostMapping
    public Result<Void> create(@RequestBody @Valid SceneMapCreateDTO dto) {
        sceneMapService.create(dto);
        return Result.ok();
    }

    @Operation(summary = "更新地图")
    @PutMapping("/{code}")
    public Result<Void> update(
            @Parameter(description = "地图编码") @PathVariable String code,
            @RequestBody @Valid SceneMapUpdateDTO dto) {
        sceneMapService.update(code, dto);
        return Result.ok();
    }

    @Operation(summary = "删除地图")
    @DeleteMapping("/{code}")
    public Result<Void> delete(@Parameter(description = "地图编码") @PathVariable String code) {
        sceneMapService.deleteByCode(code);
        return Result.ok();
    }
}
