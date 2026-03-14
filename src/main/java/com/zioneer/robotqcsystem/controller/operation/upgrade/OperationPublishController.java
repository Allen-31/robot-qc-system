package com.zioneer.robotqcsystem.controller.operation.upgrade;

import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.common.result.Result;
import com.zioneer.robotqcsystem.domain.dto.OperationPublishCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.OperationPublishDeviceQuery;
import com.zioneer.robotqcsystem.domain.dto.OperationPublishQuery;
import com.zioneer.robotqcsystem.domain.dto.OperationPublishUpdateDTO;
import com.zioneer.robotqcsystem.domain.vo.OperationPublishCancelResultVO;
import com.zioneer.robotqcsystem.domain.vo.OperationPublishCreateResultVO;
import com.zioneer.robotqcsystem.domain.vo.OperationPublishDeviceCancelResultVO;
import com.zioneer.robotqcsystem.domain.vo.OperationPublishDeviceVO;
import com.zioneer.robotqcsystem.domain.vo.OperationPublishVO;
import com.zioneer.robotqcsystem.service.operation.OperationPublishService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 运营-升级-发布管理。
 */
@Tag(name = "发布管理", description = "运营-升级-发布管理")
@RestController
@RequestMapping("/api/operation/publish")
@RequiredArgsConstructor
public class OperationPublishController {

    private final OperationPublishService operationPublishService;

    /**
     * 发布任务列表。
     *
     * @param query 查询参数
     * @return 分页结果
     */
    @Operation(summary = "发布任务列表", description = "分页/筛选：keyword/status/strategy/packageName/creator")
    @GetMapping
    public Result<PageResult<OperationPublishVO>> page(@Valid OperationPublishQuery query) {
        return Result.ok(operationPublishService.page(query));
    }

    /**
     * 创建发布任务。
     *
     * @param dto 创建请求
     * @return 创建结果
     */
    @Operation(summary = "创建发布任务")
    @PostMapping
    public Result<OperationPublishCreateResultVO> create(@Valid @RequestBody OperationPublishCreateDTO dto) {
        return Result.ok(operationPublishService.create(dto));
    }

    /**
     * 更新发布任务。
     *
     * @param id 任务ID
     * @param dto 更新请求
     * @return 空响应
     */
    @Operation(summary = "更新发布任务")
    @PutMapping("/{id}")
    public Result<Void> update(
            @Parameter(description = "任务ID") @PathVariable Long id,
            @Valid @RequestBody OperationPublishUpdateDTO dto) {
        operationPublishService.update(id, dto);
        return Result.ok();
    }

    /**
     * 发布任务设备进度。
     *
     * @param id 任务ID
     * @param query 查询参数
     * @return 分页结果
     */
    @Operation(summary = "发布任务设备进度")
    @GetMapping("/{id}/progress")
    public Result<PageResult<OperationPublishDeviceVO>> progress(
            @Parameter(description = "任务ID") @PathVariable Long id,
            @Valid OperationPublishDeviceQuery query) {
        return Result.ok(operationPublishService.devicePage(id, query));
    }

    /**
     * 发布任务设备进度（别名）。
     *
     * @param id 任务ID
     * @param query 查询参数
     * @return 分页结果
     */
    @Operation(summary = "发布任务设备进度（devices）")
    @GetMapping("/{id}/devices")
    public Result<PageResult<OperationPublishDeviceVO>> devices(
            @Parameter(description = "任务ID") @PathVariable Long id,
            @Valid OperationPublishDeviceQuery query) {
        return Result.ok(operationPublishService.devicePage(id, query));
    }

    /**
     * 取消发布任务。
     *
     * @param id 任务ID
     * @return 取消结果
     */
    @Operation(summary = "取消发布任务")
    @PostMapping("/{id}/cancel")
    public Result<OperationPublishCancelResultVO> cancel(
            @Parameter(description = "任务ID") @PathVariable Long id) {
        return Result.ok(operationPublishService.cancel(id));
    }

    /**
     * 取消单个设备升级。
     *
     * @param publishId 任务ID
     * @param deviceId 设备进度ID
     * @return 取消结果
     */
    @Operation(summary = "取消单个设备升级")
    @PostMapping("/{publishId}/devices/{deviceId}/cancel")
    public Result<OperationPublishDeviceCancelResultVO> cancelDevice(
            @Parameter(description = "任务ID") @PathVariable Long publishId,
            @Parameter(description = "设备进度ID") @PathVariable Long deviceId) {
        return Result.ok(operationPublishService.cancelDevice(publishId, deviceId));
    }
}
