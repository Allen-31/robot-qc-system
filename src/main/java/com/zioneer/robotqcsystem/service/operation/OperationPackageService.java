package com.zioneer.robotqcsystem.service.operation;

import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.domain.dto.OperationPackageQuery;
import com.zioneer.robotqcsystem.domain.dto.OperationPackageUpdateDTO;
import com.zioneer.robotqcsystem.domain.vo.OperationPackageVO;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.Optional;

/**
 * 安装包管理服务。
 */
public interface OperationPackageService {

    /**
     * 分页查询安装包列表。
     *
     * @param query 查询参数
     * @return 分页结果
     */
    PageResult<OperationPackageVO> page(OperationPackageQuery query);

    /**
     * 上传安装包。
     *
     * @param file 安装包文件
     * @param name 安装包名称（可选）
     * @param type 安装包类型
     * @param description 描述
     * @param uploader 上传者
     * @return 安装包信息
     */
    OperationPackageVO upload(MultipartFile file, String name, String type, String description, String uploader);

    /**
     * 更新安装包信息。
     *
     * @param id 安装包ID
     * @param dto 更新请求
     */
    void update(Long id, OperationPackageUpdateDTO dto);

    /**
     * 删除安装包。
     *
     * @param id 安装包ID
     */
    void deleteById(Long id);

    /**
     * 替换安装包文件（重新上传）。
     *
     * @param id 安装包ID
     * @param file 新文件
     * @return 更新后的安装包信息
     */
    OperationPackageVO replaceFile(Long id, MultipartFile file);

    /**
     * 获取下载文件路径。
     *
     * @param id 安装包ID
     * @return 文件路径
     */
    Optional<Path> getPathForDownload(Long id);
}
