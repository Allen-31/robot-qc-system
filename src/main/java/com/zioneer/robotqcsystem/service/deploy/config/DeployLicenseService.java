package com.zioneer.robotqcsystem.service.deploy.config;

import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.domain.dto.DeployLicenseQuery;
import com.zioneer.robotqcsystem.domain.vo.DeployLicenseVO;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.Optional;

/**
 * 许可证服务。
 */
public interface DeployLicenseService {

    PageResult<DeployLicenseVO> page(DeployLicenseQuery query);

    DeployLicenseVO importLicense(MultipartFile file, String name, String applicant);

    void deleteById(Long id);

    Optional<Path> getPathForDownload(Long id);
}