package com.zioneer.robotqcsystem.controller.deploy.user;

import com.zioneer.robotqcsystem.common.result.Result;
import com.zioneer.robotqcsystem.domain.dto.RoleCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.RolePermissionUpdateDTO;
import com.zioneer.robotqcsystem.domain.dto.RoleUpdateDTO;
import com.zioneer.robotqcsystem.domain.vo.RolePermissionVO;
import com.zioneer.robotqcsystem.domain.vo.RoleVO;
import com.zioneer.robotqcsystem.service.auth.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色管理（deploy-user 模块）
 */
@Tag(name = "角色管理", description = "角色增删改查、权限配置")
@RestController
@RequestMapping("/api/deploy/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @Operation(summary = "角色列表")
    @GetMapping
    public Result<List<RoleVO>> list(
            @Parameter(description = "关键词：编码/名称/描述") @RequestParam(required = false) String keyword) {
        return Result.ok(roleService.list(keyword));
    }

    @Operation(summary = "新增角色")
    @PostMapping
    public Result<Void> create(@RequestBody @Valid RoleCreateDTO dto) {
        roleService.create(dto);
        return Result.ok();
    }

    @Operation(summary = "更新角色")
    @PutMapping("/{code}")
    public Result<Void> update(
            @Parameter(description = "角色编码") @PathVariable String code,
            @RequestBody @Valid RoleUpdateDTO dto) {
        roleService.update(code, dto);
        return Result.ok();
    }

    @Operation(summary = "删除角色")
    @DeleteMapping("/{code}")
    public Result<Void> delete(@Parameter(description = "角色编码") @PathVariable String code) {
        roleService.deleteByCode(code);
        return Result.ok();
    }

    @Operation(summary = "获取角色权限配置")
    @GetMapping("/{code}/permissions")
    public Result<List<RolePermissionVO>> getPermissions(
            @Parameter(description = "角色编码") @PathVariable String code) {
        return Result.ok(roleService.getPermissions(code));
    }

    @Operation(summary = "保存角色权限配置")
    @PutMapping("/{code}/permissions")
    public Result<Void> savePermissions(
            @Parameter(description = "角色编码") @PathVariable String code,
            @RequestBody RolePermissionUpdateDTO dto) {
        roleService.savePermissions(code, dto);
        return Result.ok();
    }
}
