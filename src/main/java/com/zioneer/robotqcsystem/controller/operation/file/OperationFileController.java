package com.zioneer.robotqcsystem.controller.operation.file;

import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.common.result.Result;
import com.zioneer.robotqcsystem.domain.dto.OperationFileQuery;
import com.zioneer.robotqcsystem.domain.vo.OperationFileVO;
import com.zioneer.robotqcsystem.service.operation.OperationFileService;
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
 * 运营-文件管理（4.4.1）：文件列表、上传、删除
 */
@Tag(name = "文件管理", description = "运营-文件列表、上传、删除")
@RestController
@RequestMapping("/api/operation/files")
@RequiredArgsConstructor
public class OperationFileController {

    private final OperationFileService operationFileService;

    @Operation(summary = "文件列表", description = "分页/筛选：keyword、type、tags")
    @GetMapping
    public Result<PageResult<OperationFileVO>> page(@Valid OperationFileQuery query) {
        return Result.ok(operationFileService.page(query));
    }

    @Operation(summary = "上传文件")
    @PostMapping
    public Result<OperationFileVO> upload(
            @Parameter(description = "文件") @RequestParam("file") MultipartFile file,
            @Parameter(description = "显示名称（可选，默认取文件名）") @RequestParam(required = false) String name,
            @Parameter(description = "类型/分类（可选）") @RequestParam(required = false) String type,
            @Parameter(description = "标签，逗号分隔（可选）") @RequestParam(required = false) String tags) {
        return Result.ok(operationFileService.upload(file, name, type, tags));
    }

    @Operation(summary = "删除文件")
    @DeleteMapping("/{id}")
    public Result<Void> delete(
            @Parameter(description = "文件ID") @PathVariable Long id) {
        operationFileService.deleteById(id);
        return Result.ok();
    }

    @Operation(summary = "预览/下载文件")
    @GetMapping("/{id}/preview")
    public ResponseEntity<Resource> preview(
            @Parameter(description = "文件ID") @PathVariable Long id) {
        Optional<Path> pathOpt = operationFileService.getPathForPreview(id);
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
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
