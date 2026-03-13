package com.zioneer.robotqcsystem.controller.deploy.user;

import com.zioneer.robotqcsystem.common.result.Result;
import com.zioneer.robotqcsystem.domain.dto.MenuCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.MenuOrderDTO;
import com.zioneer.robotqcsystem.domain.dto.MenuUpdateDTO;
import com.zioneer.robotqcsystem.domain.vo.MenuTreeNodeVO;
import com.zioneer.robotqcsystem.service.deploy.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单/权限管理（deploy-user 模块）
 */
@Tag(name = "菜单管理", description = "菜单树增删改查与排序")
@RestController
@RequestMapping("/api/deploy/menus")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @Operation(summary = "获取菜单树或扁平列表")
    @GetMapping
    public Result<List<MenuTreeNodeVO>> getMenus(
            @Parameter(description = "enabled / disabled / 不传为全部") @RequestParam(required = false) String status,
            @Parameter(description = "true 树形，false 扁平，默认 true") @RequestParam(required = false, defaultValue = "true") Boolean tree) {
        return Result.ok(menuService.getMenus(status, tree == null || tree));
    }

    @Operation(summary = "新增菜单节点")
    @PostMapping
    public Result<Long> create(@RequestBody @Valid MenuCreateDTO dto) {
        return Result.ok(menuService.create(dto));
    }

    @Operation(summary = "更新菜单节点")
    @PutMapping("/{id}")
    public Result<Void> update(
            @Parameter(description = "菜单主键") @PathVariable Long id,
            @RequestBody @Valid MenuUpdateDTO dto) {
        menuService.update(id, dto);
        return Result.ok();
    }

    @Operation(summary = "删除菜单节点")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@Parameter(description = "菜单主键") @PathVariable Long id) {
        menuService.deleteById(id);
        return Result.ok();
    }

    @Operation(summary = "批量更新同层排序")
    @PutMapping("/order")
    public Result<Void> updateOrder(@RequestBody @Valid MenuOrderDTO dto) {
        menuService.updateOrder(dto);
        return Result.ok();
    }
}
