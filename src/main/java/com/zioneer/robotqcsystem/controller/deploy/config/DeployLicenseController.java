package com.zioneer.robotqcsystem.controller.deploy.config;

import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.common.result.Result;
import com.zioneer.robotqcsystem.domain.dto.DeployLicenseQuery;
import com.zioneer.robotqcsystem.domain.vo.DeployLicenseVO;
import com.zioneer.robotqcsystem.service.deploy.config.DeployLicenseService;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.Optional;

/**
 * 部署配置-设定-许可证管理接口。
 */
@Tag(name = "许可证管理", description = "部署配置-设定-许可证管理")
@RestController
@RequestMapping("/api/deploy/licenses")
@RequiredArgsConstructor
public class DeployLicenseController {

    private final DeployLicenseService deployLicenseService;

    @Operation(summary = "许可证列表", description = "分页/筛选：keyword/status")
    @GetMapping
    public Result<PageResult<DeployLicenseVO>> page(@Valid DeployLicenseQuery query) {
        return Result.ok(deployLicenseService.page(query));
    }

    @Operation(summary = "导入许可证")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result<DeployLicenseVO> importLicense(
            @Parameter(description = "许可证文件") @RequestParam("file") MultipartFile file,
            @Parameter(description = "许可证名称（可选）") @RequestParam(required = false) String name,
            @Parameter(description = "申请人（可选）") @RequestParam(required = false) String applicant) {
        return Result.ok(deployLicenseService.importLicense(file, name, applicant));
    }

    @Operation(summary = "删除许可证")
    @DeleteMapping("/{id}")
    public Result<Void> delete(
            @Parameter(description = "许可证ID") @PathVariable Long id) {
        deployLicenseService.deleteById(id);
        return Result.ok();
    }

    @Operation(summary = "下载许可证")
    @GetMapping("/{id}/download")
    public ResponseEntity<Resource> download(
            @Parameter(description = "许可证ID") @PathVariable Long id) {
        Optional<Path> pathOpt = deployLicenseService.getPathForDownload(id);
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
            if (underscore > 0 && underscore < filename.length() - 1) {
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