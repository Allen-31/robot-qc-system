package com.zioneer.robotqcsystem.controller.deploy;

import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.common.result.Result;
import com.zioneer.robotqcsystem.domain.dto.PasswordUpdateDTO;
import com.zioneer.robotqcsystem.domain.dto.UserCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.UserQuery;
import com.zioneer.robotqcsystem.domain.dto.UserRolesUpdateDTO;
import com.zioneer.robotqcsystem.domain.dto.UserUpdateDTO;
import com.zioneer.robotqcsystem.domain.vo.PasswordUpdateResultVO;
import com.zioneer.robotqcsystem.domain.vo.UserListVO;
import com.zioneer.robotqcsystem.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 用户管理（部署配置 - 用户）
 */
@Tag(name = "用户管理", description = "用户增删改查、角色分配、修改密码")
@RestController
@RequestMapping("/api/deploy/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "用户分页列表")
    @GetMapping
    public Result<PageResult<UserListVO>> page(@Valid UserQuery query) {
        return Result.ok(userService.page(query));
    }

    @Operation(summary = "新增用户")
    @PostMapping
    public Result<Void> create(@RequestBody @Valid UserCreateDTO dto) {
        userService.create(dto);
        return Result.ok();
    }

    @Operation(summary = "更新用户")
    @PutMapping("/{code}")
    public Result<Void> update(
            @Parameter(description = "用户编码") @PathVariable String code,
            @RequestBody @Valid UserUpdateDTO dto) {
        userService.update(code, dto);
        return Result.ok();
    }

    @Operation(summary = "删除用户")
    @DeleteMapping("/{code}")
    public Result<Void> delete(@Parameter(description = "用户编码") @PathVariable String code) {
        userService.deleteByCode(code);
        return Result.ok();
    }

    @Operation(summary = "更新用户角色")
    @PutMapping("/{code}/roles")
    public Result<Void> updateRoles(
            @Parameter(description = "用户编码") @PathVariable String code,
            @RequestBody @Valid UserRolesUpdateDTO dto) {
        userService.updateRoles(code, dto);
        return Result.ok();
    }

    @Operation(summary = "修改密码")
    @PutMapping("/{code}/password")
    public Result<PasswordUpdateResultVO> updatePassword(
            @Parameter(description = "用户编码") @PathVariable String code,
            @RequestBody @Valid PasswordUpdateDTO dto) {
        PasswordUpdateResultVO vo = userService.updatePassword(code, dto);
        return Result.ok(vo);
    }
}
