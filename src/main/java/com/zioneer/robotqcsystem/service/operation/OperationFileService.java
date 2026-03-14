package com.zioneer.robotqcsystem.service.operation;

import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.domain.dto.OperationFileQuery;
import com.zioneer.robotqcsystem.domain.vo.OperationFileVO;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.Optional;

/**
 * 运营文件服务（4.4.1 文件管理）
 */
public interface OperationFileService {

    PageResult<OperationFileVO> page(OperationFileQuery query);

    OperationFileVO upload(MultipartFile file, String name, String type, String tags);

    void deleteById(Long id);

    /** 获取文件存储路径用于预览/下载，不存在返回 empty */
    Optional<Path> getPathForPreview(Long id);
}
