package com.zioneer.robotqcsystem.service.deploy.config.impl;

import com.zioneer.robotqcsystem.common.exception.BusinessException;
import com.zioneer.robotqcsystem.common.id.SnowflakeIdGenerator;
import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.common.result.ResultCode;
import com.zioneer.robotqcsystem.domain.dto.DeployLicenseQuery;
import com.zioneer.robotqcsystem.domain.entity.DeployLicense;
import com.zioneer.robotqcsystem.domain.vo.DeployLicenseVO;
import com.zioneer.robotqcsystem.mapper.DeployLicenseMapper;
import com.zioneer.robotqcsystem.service.deploy.config.DeployLicenseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 许可证服务实现。
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DeployLicenseServiceImpl implements DeployLicenseService {

    @Value("${app.deploy.license.upload-dir:./uploads/deploy-licenses}")
    private String uploadDir;

    private final DeployLicenseMapper deployLicenseMapper;
    private final SnowflakeIdGenerator idGenerator;

    @Override
    public PageResult<DeployLicenseVO> page(DeployLicenseQuery query) {
        String keyword = StringUtils.hasText(query.getKeyword()) ? query.getKeyword().trim() : null;
        String status = StringUtils.hasText(query.getStatus()) ? query.getStatus().trim() : null;
        boolean expiredQuery = "expired".equalsIgnoreCase(status);
        boolean activeQuery = "active".equalsIgnoreCase(status);
        if (expiredQuery) {
            status = null;
        }
        LocalDateTime now = LocalDateTime.now();

        long total = deployLicenseMapper.countList(keyword, status, expiredQuery, activeQuery, now);
        if (total == 0) {
            return PageResult.empty(query);
        }
        List<DeployLicenseVO> list = deployLicenseMapper
                .selectList(keyword, status, expiredQuery, activeQuery, now, query.getOffset(), query.getPageSize())
                .stream()
                .map(this::toVO)
                .collect(Collectors.toList());
        return PageResult.of(list, total, query.getPageNum(), query.getPageSize());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DeployLicenseVO importLicense(MultipartFile file, String name, String applicant) {
        if (file == null || file.isEmpty()) {
            throw new BusinessException("请选择许可证文件");
        }
        String originalFilename = file.getOriginalFilename();
        if (!StringUtils.hasText(originalFilename)) {
            throw new BusinessException("许可证文件名不能为空");
        }
        originalFilename = Paths.get(originalFilename).getFileName().toString();

        Long id = idGenerator.nextId();
        String safeFileName = originalFilename.replaceAll("[^a-zA-Z0-9._-]", "_");
        if (!StringUtils.hasText(safeFileName)) {
            safeFileName = "license";
        }
        String licenseName = StringUtils.hasText(name) ? name.trim() : safeFileName;
        String finalFileName = id + "_" + safeFileName;

        Path dir = Paths.get(uploadDir).toAbsolutePath().normalize();
        Path tempFile = dir.resolve(id + "_upload.tmp");
        String md5 = saveAndCalcMd5(file, dir, tempFile);

        if (deployLicenseMapper.existsByName(licenseName) > 0) {
            deleteQuietly(tempFile);
            throw new BusinessException("许可证名称已存在");
        }
        if (deployLicenseMapper.existsByMd5(md5) > 0) {
            deleteQuietly(tempFile);
            throw new BusinessException("许可证文件已存在");
        }

        Path targetFile = dir.resolve(finalFileName);
        moveFile(tempFile, targetFile);

        LocalDateTime now = LocalDateTime.now();
        DeployLicense license = DeployLicense.builder()
                .id(id)
                .name(licenseName)
                .fileName(safeFileName)
                .storagePath(targetFile.toString())
                .sizeBytes(file.getSize())
                .md5(md5)
                .effectiveAt(now)
                .expireAt(now.plusYears(1))
                .applicant(StringUtils.hasText(applicant) ? applicant.trim() : "admin")
                .status("active")
                .importedAt(now)
                .build();
        try {
            deployLicenseMapper.insert(license);
        } catch (Exception e) {
            deleteQuietly(targetFile);
            throw e;
        }

        log.info("导入许可证成功 id={}, name={}", id, license.getName());
        return toVO(license);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        DeployLicense existing = deployLicenseMapper.selectById(id);
        if (existing == null) {
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "许可证不存在");
        }
        deployLicenseMapper.deleteById(id);
        deleteStorageFile(existing.getStoragePath(), id);
    }

    @Override
    public Optional<Path> getPathForDownload(Long id) {
        DeployLicense existing = deployLicenseMapper.selectById(id);
        if (existing == null || !StringUtils.hasText(existing.getStoragePath())) {
            return Optional.empty();
        }
        Path dir = Paths.get(uploadDir).toAbsolutePath().normalize();
        Path path = Paths.get(existing.getStoragePath()).toAbsolutePath().normalize();
        if (!path.startsWith(dir)) {
            log.warn("download path rejected: id={}, path={}", id, existing.getStoragePath());
            return Optional.empty();
        }
        return Files.exists(path) ? Optional.of(path) : Optional.empty();
    }

    private String saveAndCalcMd5(MultipartFile file, Path dir, Path targetFile) {
        try {
            Files.createDirectories(dir);
            MessageDigest md = MessageDigest.getInstance("MD5");
            try (InputStream in = new BufferedInputStream(file.getInputStream());
                 DigestInputStream dis = new DigestInputStream(in, md)) {
                Files.copy(dis, targetFile, StandardCopyOption.REPLACE_EXISTING);
            }
            return toHex(md.digest());
        } catch (Exception e) {
            log.error("保存许可证文件失败 target={}", targetFile, e);
            deleteQuietly(targetFile);
            throw new BusinessException("保存许可证文件失败");
        }
    }

    private void moveFile(Path source, Path target) {
        try {
            Files.createDirectories(target.getParent());
            Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            log.error("保存许可证文件失败 target={}", target, e);
            deleteQuietly(source);
            throw new BusinessException("保存许可证文件失败");
        }
    }

    private void deleteStorageFile(String storagePath, Long id) {
        if (!StringUtils.hasText(storagePath)) {
            return;
        }
        try {
            Path dir = Paths.get(uploadDir).toAbsolutePath().normalize();
            Path path = Paths.get(storagePath).toAbsolutePath().normalize();
            if (!path.startsWith(dir)) {
                log.warn("delete path rejected: id={}, path={}", id, storagePath);
                return;
            }
            if (Files.exists(path)) {
                Files.delete(path);
            }
        } catch (Exception e) {
            log.warn("删除许可证文件失败 id={}, path={}", id, storagePath, e);
        }
    }

    private void deleteQuietly(Path path) {
        try {
            if (Files.exists(path)) {
                Files.delete(path);
            }
        } catch (Exception ignored) {
        }
    }

    private DeployLicenseVO toVO(DeployLicense license) {
        String status = license.getStatus();
        LocalDateTime now = LocalDateTime.now();
        if ("active".equalsIgnoreCase(status)
                && license.getExpireAt() != null
                && license.getExpireAt().isBefore(now)) {
            status = "expired";
        }
        return DeployLicenseVO.builder()
                .id(license.getId())
                .name(license.getName())
                .fileName(license.getFileName())
                .effectiveAt(license.getEffectiveAt())
                .expireAt(license.getExpireAt())
                .applicant(license.getApplicant())
                .status(status)
                .importedAt(license.getImportedAt())
                .downloadUrl("/api/deploy/licenses/" + license.getId() + "/download")
                .build();
    }

    private static String toHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}