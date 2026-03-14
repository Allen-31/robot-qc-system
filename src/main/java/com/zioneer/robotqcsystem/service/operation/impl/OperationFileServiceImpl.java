package com.zioneer.robotqcsystem.service.operation.impl;

import com.zioneer.robotqcsystem.common.exception.BusinessException;
import com.zioneer.robotqcsystem.common.id.SnowflakeIdGenerator;
import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.common.result.ResultCode;
import com.zioneer.robotqcsystem.domain.dto.OperationFileQuery;
import com.zioneer.robotqcsystem.domain.entity.OperationFile;
import com.zioneer.robotqcsystem.domain.vo.OperationFileVO;
import com.zioneer.robotqcsystem.mapper.OperationFileMapper;
import com.zioneer.robotqcsystem.service.operation.OperationFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 运营文件服务实现（4.4.1 文件管理）
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OperationFileServiceImpl implements OperationFileService {

    @Value("${app.operation.file.upload-dir:./uploads/operation-files}")
    private String uploadDir;

    private final OperationFileMapper operationFileMapper;
    private final SnowflakeIdGenerator idGenerator;

    @Override
    public PageResult<OperationFileVO> page(OperationFileQuery query) {
        List<String> tagList = parseTags(query.getTags());
        long total = operationFileMapper.countList(
                StringUtils.hasText(query.getKeyword()) ? query.getKeyword().trim() : null,
                StringUtils.hasText(query.getType()) ? query.getType().trim() : null,
                tagList);
        if (total == 0) {
            return PageResult.empty(query);
        }
        List<OperationFile> list = operationFileMapper.selectList(
                StringUtils.hasText(query.getKeyword()) ? query.getKeyword().trim() : null,
                StringUtils.hasText(query.getType()) ? query.getType().trim() : null,
                tagList,
                query.getOffset(),
                query.getPageSize());
        List<OperationFileVO> voList = list.stream()
                .map(e -> toVO(e, false))
                .collect(Collectors.toList());
        return PageResult.of(voList, total, query.getPageNum(), query.getPageSize());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OperationFileVO upload(MultipartFile file, String name, String type, String tags) {
        if (file == null || file.isEmpty()) {
            throw new BusinessException("请选择要上传的文件");
        }
        long id = idGenerator.nextId();
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.isBlank()) {
            originalFilename = "file";
        }
        String safeName = StringUtils.hasText(name) ? name.trim() : originalFilename;
        String safeType = StringUtils.hasText(type) ? type.trim() : "其他";
        String tagsStr = StringUtils.hasText(tags) ? tags.trim() : "";

        Path dir = Paths.get(uploadDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(dir);
        } catch (IOException e) {
            log.error("create upload dir failed: {}", dir, e);
            throw new BusinessException("创建上传目录失败");
        }
        String storedFileName = id + "_" + originalFilename.replaceAll("[^a-zA-Z0-9._-]", "_");
        Path targetFile = dir.resolve(storedFileName);
        try {
            file.transferTo(targetFile.toFile());
        } catch (IOException e) {
            log.error("save file failed: {}", targetFile, e);
            throw new BusinessException("保存文件失败");
        }
        String storagePath = targetFile.toString();
        LocalDateTime now = LocalDateTime.now();
        OperationFile entity = OperationFile.builder()
                .id(id)
                .name(safeName)
                .type(safeType)
                .sizeBytes(file.getSize())
                .tags(tagsStr)
                .storagePath(storagePath)
                .previewContent(null)
                .createdAt(now)
                .build();
        operationFileMapper.insert(entity);
        log.info("upload operation file, id={}, name={}", id, safeName);
        return toVO(entity, true);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        OperationFile exist = operationFileMapper.selectById(id);
        if (exist == null) {
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "文件不存在");
        }
        operationFileMapper.deleteById(id);
        try {
            Path path = Paths.get(exist.getStoragePath());
            if (Files.exists(path)) {
                Files.delete(path);
            }
        } catch (IOException e) {
            log.warn("delete file from disk failed: id={}, path={}", id, exist.getStoragePath(), e);
        }
        log.info("delete operation file, id={}", id);
    }

    @Override
    public Optional<Path> getPathForPreview(Long id) {
        OperationFile file = operationFileMapper.selectById(id);
        if (file == null || file.getStoragePath() == null) {
            return Optional.empty();
        }
        Path path = Paths.get(file.getStoragePath());
        return Files.exists(path) ? Optional.of(path) : Optional.empty();
    }

    private static List<String> parseTags(String tags) {
        if (tags == null || tags.isBlank()) {
            return Collections.emptyList();
        }
        return Arrays.stream(tags.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

    private OperationFileVO toVO(OperationFile e, boolean includePreviewContent) {
        List<String> tagList = e.getTags() != null && !e.getTags().isEmpty()
                ? Arrays.stream(e.getTags().split(",")).map(String::trim).filter(s -> !s.isEmpty()).collect(Collectors.toList())
                : new ArrayList<>();
        String previewUrl = "/api/operation/files/" + e.getId() + "/preview";
        return OperationFileVO.builder()
                .id(e.getId())
                .name(e.getName())
                .type(e.getType())
                .size(formatSize(e.getSizeBytes()))
                .tags(tagList)
                .createdAt(e.getCreatedAt())
                .previewUrl(previewUrl)
                .previewContent(includePreviewContent ? e.getPreviewContent() : null)
                .build();
    }

    private static String formatSize(long sizeBytes) {
        if (sizeBytes < 1024) {
            return sizeBytes + " B";
        }
        if (sizeBytes < 1024 * 1024) {
            return String.format("%.1f KB", sizeBytes / 1024.0);
        }
        if (sizeBytes < 1024L * 1024 * 1024) {
            return String.format("%.1f MB", sizeBytes / (1024.0 * 1024));
        }
        return String.format("%.1f GB", sizeBytes / (1024.0 * 1024 * 1024));
    }
}
