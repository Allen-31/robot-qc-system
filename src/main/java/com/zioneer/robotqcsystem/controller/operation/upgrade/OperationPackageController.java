package com.zioneer.robotqcsystem.controller.operation.upgrade;

import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.common.result.Result;
import com.zioneer.robotqcsystem.domain.dto.OperationPackageQuery;
import com.zioneer.robotqcsystem.domain.dto.OperationPackageUpdateDTO;
import com.zioneer.robotqcsystem.domain.vo.OperationPackageVO;
import com.zioneer.robotqcsystem.service.operation.OperationPackageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.Optional;

/**
 * 运营-升级-安装包管理。
 */
@Tag(name = "安装包管理", description = "运营-升级-安装包管理")
@RestController
@RequestMapping("/api/operation/packages")
@RequiredArgsConstructor
public class OperationPackageController {

    private final OperationPackageService operationPackageService;

    /**
     * 安装包列表。
     *
     * @param query 查询参数
     * @return 分页结果
     */
    @Operation(summary = "安装包列表", description = "分页/筛选：keyword/type/part/md5/uploader")
    @GetMapping
    public Result<PageResult<OperationPackageVO>> page(@Valid OperationPackageQuery query) {
        return Result.ok(operationPackageService.page(query));
    }

    /**
     * 上传安装包。
     *
     * @param file 安装包文件
     * @param name 安装包名称（可选）
     * @param type 类型
     * @param description 描述
     * @param uploader 上传者
     * @return 安装包信息
     */
    @Operation(summary = "上传安装包")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result<OperationPackageVO> upload(
            @Parameter(description = "安装包文件(zip)") @RequestParam("file") MultipartFile file,
            @Parameter(description = "安装包名称") @RequestParam(required = false) String name,
            @Parameter(description = "类型") @RequestParam(required = false) String type,
            @Parameter(description = "描述") @RequestParam(required = false) String description,
            @Parameter(description = "上传者") @RequestParam(required = false) String uploader) {
        return Result.ok(operationPackageService.upload(file, name, type, description, uploader));
    }

    /**
     * 更新安装包信息。
     *
     * @param id 安装包ID
     * @param dto 更新请求
     * @return 空响应
     */
    @Operation(summary = "更新安装包", description = "编辑名称/描述/目标部件")
    @PutMapping("/{id}")
    public Result<Void> update(
            @Parameter(description = "安装包ID") @PathVariable Long id,
            @Valid @RequestBody OperationPackageUpdateDTO dto) {
        operationPackageService.update(id, dto);
        return Result.ok();
    }

    /**
     * 删除安装包。
     *
     * @param id 安装包ID
     * @return 空响应
     */
    @Operation(summary = "删除安装包")
    @DeleteMapping("/{id}")
    public Result<Void> delete(
            @Parameter(description = "安装包ID") @PathVariable Long id) {
        operationPackageService.deleteById(id);
        return Result.ok();
    }

    /**
     * 重新上传安装包文件。
     *
     * @param id 安装包ID
     * @param file 新文件
     * @return 更新后的安装包信息
     */
    @Operation(summary = "重新上传安装包")
    @PostMapping(value = "/{id}/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result<OperationPackageVO> replaceFile(
            @Parameter(description = "安装包ID") @PathVariable Long id,
            @Parameter(description = "安装包文件(zip)") @RequestParam("file") MultipartFile file) {
        return Result.ok(operationPackageService.replaceFile(id, file));
    }

    /**
     * 下载安装包。
     *
     * @param id 安装包ID
     * @return 文件响应
     */
    @Operation(summary = "下载安装包")
    @GetMapping("/{id}/download")
    public ResponseEntity<Resource> download(
            @Parameter(description = "安装包ID") @PathVariable Long id) {
        Optional<Path> pathOpt = operationPackageService.getPathForDownload(id);
        if (pathOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        try {
            Resource resource = new UrlResource(pathOpt.get().toUri());
            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.notFound().build();
            }
            String filename = pathOpt.get().getFileName().toString();
            int underscore = filename.indexOf('_');
            if (underscore > 0) {
                filename = filename.substring(underscore + 1);
            }
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
