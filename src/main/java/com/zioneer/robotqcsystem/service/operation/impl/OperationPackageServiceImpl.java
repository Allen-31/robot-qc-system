package com.zioneer.robotqcsystem.service.operation.impl;

import com.zioneer.robotqcsystem.common.exception.BusinessException;
import com.zioneer.robotqcsystem.common.id.SnowflakeIdGenerator;
import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.common.result.ResultCode;
import com.zioneer.robotqcsystem.domain.dto.OperationPackageQuery;
import com.zioneer.robotqcsystem.domain.dto.OperationPackageUpdateDTO;
import com.zioneer.robotqcsystem.domain.entity.OperationPackage;
import com.zioneer.robotqcsystem.domain.entity.OperationPackagePart;
import com.zioneer.robotqcsystem.domain.vo.OperationPackageVO;
import com.zioneer.robotqcsystem.mapper.OperationPackageMapper;
import com.zioneer.robotqcsystem.service.operation.OperationPackageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * 安装包管理服务实现。
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OperationPackageServiceImpl implements OperationPackageService {

    private static final Pattern VERSION_PATTERN = Pattern.compile("(v?\\d+(?:\\.\\d+)+)", Pattern.CASE_INSENSITIVE);

    @Value("${app.operation.package.upload-dir:./uploads/operation-packages}")
    private String uploadDir;

    private final OperationPackageMapper operationPackageMapper;
    private final SnowflakeIdGenerator idGenerator;

    /** {@inheritDoc} */
    @Override
    public PageResult<OperationPackageVO> page(OperationPackageQuery query) {
        String keyword = StringUtils.hasText(query.getKeyword()) ? query.getKeyword().trim() : null;
        String type = StringUtils.hasText(query.getType()) ? query.getType().trim() : null;
        String part = StringUtils.hasText(query.getPart()) ? query.getPart().trim() : null;
        String md5 = StringUtils.hasText(query.getMd5()) ? query.getMd5().trim() : null;
        String uploader = StringUtils.hasText(query.getUploader()) ? query.getUploader().trim() : null;

        long total = operationPackageMapper.countList(keyword, type, part, md5, uploader);
        if (total == 0) {
            return PageResult.empty(query);
        }
        List<OperationPackage> list = operationPackageMapper.selectList(
                keyword, type, part, md5, uploader, query.getOffset(), query.getPageSize());
        List<OperationPackageVO> voList = new ArrayList<>();
        for (OperationPackage pkg : list) {
            voList.add(toVO(pkg));
        }
        return PageResult.of(voList, total, query.getPageNum(), query.getPageSize());
    }

    /** {@inheritDoc} */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OperationPackageVO upload(MultipartFile file, String name, String type, String description, String uploader) {
        if (file == null || file.isEmpty()) {
            throw new BusinessException("请选择要上传的安装包");
        }
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.isBlank()) {
            throw new BusinessException("安装包文件名不能为空");
        }
        if (!originalFilename.toLowerCase(Locale.ROOT).endsWith(".zip")) {
            throw new BusinessException("安装包必须是ZIP压缩包");
        }
        String safeName = StringUtils.hasText(name) ? name.trim() : originalFilename;
        String safeType = StringUtils.hasText(type) ? type.trim() : "unknown";
        String safeDescription = StringUtils.hasText(description) ? description.trim() : "";
        String safeUploader = StringUtils.hasText(uploader) ? uploader.trim() : "system";

        long id = idGenerator.nextId();
        Path dir = Paths.get(uploadDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(dir);
        } catch (IOException e) {
            log.error("创建安装包目录失败: {}", dir, e);
            throw new BusinessException("创建安装包目录失败");
        }
        String storedFileName = id + "_" + originalFilename.replaceAll("[^a-zA-Z0-9._-]", "_");
        Path targetFile = dir.resolve(storedFileName);

        String md5 = saveFileAndComputeMd5(file, targetFile);
        OperationPackage duplicate = operationPackageMapper.selectByMd5(md5);
        if (duplicate != null) {
            deleteStoredFileQuietly(targetFile);
            throw new BusinessException("安装包已存在（MD5重复）");
        }
        List<OperationPackagePart> parts = parseZipParts(targetFile);

        LocalDateTime now = LocalDateTime.now();
        OperationPackage pkg = OperationPackage.builder()
                .id(id)
                .name(safeName)
                .type(safeType)
                .targetParts(parts)
                .description(safeDescription)
                .sizeBytes(file.getSize())
                .md5(md5)
                .uploader(safeUploader)
                .uploadedAt(now)
                .storagePath(targetFile.toString())
                .build();
        operationPackageMapper.insert(pkg);
        log.info("上传安装包成功, id={}, name={}", id, safeName);
        return toVO(pkg);
    }

    /** {@inheritDoc} */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Long id, OperationPackageUpdateDTO dto) {
        OperationPackage exist = operationPackageMapper.selectById(id);
        if (exist == null) {
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "安装包不存在");
        }
        boolean hasName = StringUtils.hasText(dto.getName());
        boolean hasDesc = dto.getDescription() != null;
        boolean hasParts = dto.getTargetParts() != null;
        if (!hasName && !hasDesc && !hasParts) {
            throw new BusinessException("请至少填写一项要更新的内容（名称、描述或目标部件）");
        }
        OperationPackage pkg = OperationPackage.builder()
                .id(id)
                .name(hasName ? dto.getName().trim() : null)
                .description(hasDesc ? dto.getDescription() : null)
                .targetParts(hasParts ? dto.getTargetParts() : null)
                .build();
        operationPackageMapper.updateById(pkg);
        log.info("更新安装包, id={}", id);
    }

    /** {@inheritDoc} */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        OperationPackage exist = operationPackageMapper.selectById(id);
        if (exist == null) {
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "安装包不存在");
        }
        operationPackageMapper.deleteById(id);
        try {
            Path path = Paths.get(exist.getStoragePath());
            if (Files.exists(path)) {
                Files.delete(path);
            }
        } catch (IOException e) {
            log.warn("删除安装包文件失败: id={}, path={}", id, exist.getStoragePath(), e);
        }
        log.info("删除安装包, id={}", id);
    }

    /** {@inheritDoc} */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OperationPackageVO replaceFile(Long id, MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BusinessException("请选择要上传的安装包");
        }
        OperationPackage exist = operationPackageMapper.selectById(id);
        if (exist == null) {
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "安装包不存在");
        }
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.isBlank()) {
            throw new BusinessException("安装包文件名不能为空");
        }
        if (!originalFilename.toLowerCase(Locale.ROOT).endsWith(".zip")) {
            throw new BusinessException("安装包必须是ZIP压缩包");
        }

        Path dir = Paths.get(uploadDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(dir);
        } catch (IOException e) {
            log.error("创建安装包目录失败: {}", dir, e);
            throw new BusinessException("创建安装包目录失败");
        }
        String storedFileName = id + "_" + originalFilename.replaceAll("[^a-zA-Z0-9._-]", "_");
        Path targetFile = dir.resolve(storedFileName);

        String md5 = saveFileAndComputeMd5(file, targetFile);
        OperationPackage duplicate = operationPackageMapper.selectByMd5(md5);
        if (duplicate != null && !id.equals(duplicate.getId())) {
            deleteStoredFileQuietly(targetFile);
            throw new BusinessException("安装包已存在（MD5重复）");
        }
        List<OperationPackagePart> parts = parseZipParts(targetFile);

        if (exist.getStoragePath() != null && !exist.getStoragePath().equals(targetFile.toString())) {
            try {
                Path oldPath = Paths.get(exist.getStoragePath());
                if (Files.exists(oldPath)) {
                    Files.delete(oldPath);
                }
            } catch (IOException e) {
                log.warn("删除旧安装包文件失败: id={}, path={}", id, exist.getStoragePath(), e);
            }
        }

        OperationPackage pkg = OperationPackage.builder()
                .id(id)
                .name(exist.getName())
                .type(exist.getType())
                .targetParts(parts)
                .description(exist.getDescription())
                .sizeBytes(file.getSize())
                .md5(md5)
                .uploader(exist.getUploader())
                .uploadedAt(LocalDateTime.now())
                .storagePath(targetFile.toString())
                .build();
        operationPackageMapper.updateById(pkg);
        log.info("替换安装包文件, id={}", id);
        return toVO(pkg);
    }

    /** {@inheritDoc} */
    @Override
    public Optional<Path> getPathForDownload(Long id) {
        OperationPackage pkg = operationPackageMapper.selectById(id);
        if (pkg == null || pkg.getStoragePath() == null) {
            return Optional.empty();
        }
        Path path = Paths.get(pkg.getStoragePath());
        return Files.exists(path) ? Optional.of(path) : Optional.empty();
    }

    private String saveFileAndComputeMd5(MultipartFile file, Path targetFile) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            try (InputStream in = new BufferedInputStream(file.getInputStream());
                 DigestInputStream dis = new DigestInputStream(in, md)) {
                Files.copy(dis, targetFile, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            }
            return toHex(md.digest());
        } catch (Exception e) {
            log.error("保存安装包失败: {}", targetFile, e);
            throw new BusinessException("保存安装包失败");
        }
    }

    private void deleteStoredFileQuietly(Path path) {
        try {
            if (Files.exists(path)) {
                Files.delete(path);
            }
        } catch (IOException e) {
            log.warn("删除安装包文件失败: path={}", path, e);
        }
    }

    private List<OperationPackagePart> parseZipParts(Path zipPath) {
        Map<String, OperationPackagePart> parts = new LinkedHashMap<>();
        try (ZipFile zipFile = new ZipFile(zipPath.toFile())) {
            zipFile.stream()
                    .filter(entry -> !entry.isDirectory())
                    .forEach(entry -> parseEntry(entry, parts));
        } catch (IOException e) {
            log.warn("解析安装包失败: {}", zipPath, e);
        }
        return new ArrayList<>(parts.values());
    }

    private void parseEntry(ZipEntry entry, Map<String, OperationPackagePart> parts) {
        String name = entry.getName();
        if (name.startsWith("__MACOSX") || name.startsWith(".")) {
            return;
        }
        int slash = name.lastIndexOf('/');
        if (slash >= 0) {
            name = name.substring(slash + 1);
        }
        if (name.isBlank()) {
            return;
        }
        int dot = name.lastIndexOf('.');
        String base = dot > 0 ? name.substring(0, dot) : name;
        Matcher matcher = VERSION_PATTERN.matcher(base);
        String version = null;
        int versionIndex = -1;
        while (matcher.find()) {
            version = matcher.group(1);
            versionIndex = matcher.start(1);
        }
        if (version == null) {
            return;
        }
        String part = base.substring(0, versionIndex).trim();
        part = part.replaceAll("[@_\\-\\s]+$", "");
        if (part.isBlank()) {
            return;
        }
        String normalizedVersion = version.toLowerCase(Locale.ROOT).startsWith("v")
                ? version
                : "v" + version;
        String key = part + "@" + normalizedVersion;
        if (!parts.containsKey(key)) {
            parts.put(key, OperationPackagePart.builder()
                    .part(part)
                    .version(normalizedVersion)
                    .build());
        }
    }

    private OperationPackageVO toVO(OperationPackage pkg) {
        return OperationPackageVO.builder()
                .id(pkg.getId())
                .name(pkg.getName())
                .type(pkg.getType())
                .targetParts(pkg.getTargetParts() == null ? Collections.emptyList() : pkg.getTargetParts())
                .description(pkg.getDescription())
                .size(formatSize(pkg.getSizeBytes() == null ? 0L : pkg.getSizeBytes()))
                .md5(pkg.getMd5())
                .uploader(pkg.getUploader())
                .uploadedAt(pkg.getUploadedAt())
                .downloadUrl("/api/operation/packages/" + pkg.getId() + "/download")
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

    private static String toHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}



