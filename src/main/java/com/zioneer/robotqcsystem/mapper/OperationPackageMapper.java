package com.zioneer.robotqcsystem.mapper;

import com.zioneer.robotqcsystem.domain.entity.OperationPackage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 安装包 Mapper。
 */
@Mapper
public interface OperationPackageMapper {

    /**
     * 根据ID查询安装包。
     *
     * @param id 安装包ID
     * @return 安装包实体
     */
    OperationPackage selectById(@Param("id") Long id);

    /**
     * 根据MD5查询安装包。
     *
     * @param md5 文件MD5
     * @return 安装包实体
     */
    OperationPackage selectByMd5(@Param("md5") String md5);

    /**
     * 统计安装包列表数量。
     *
     * @param keyword 关键字
     * @param type 类型
     * @param part 部件
     * @param md5 MD5
     * @param uploader 上传者
     * @return 总数
     */
    long countList(@Param("keyword") String keyword,
                   @Param("type") String type,
                   @Param("part") String part,
                   @Param("md5") String md5,
                   @Param("uploader") String uploader);

    /**
     * 查询安装包列表。
     *
     * @param keyword 关键字
     * @param type 类型
     * @param part 部件
     * @param md5 MD5
     * @param uploader 上传者
     * @param offset 偏移量
     * @param pageSize 页大小
     * @return 安装包列表
     */
    List<OperationPackage> selectList(@Param("keyword") String keyword,
                                      @Param("type") String type,
                                      @Param("part") String part,
                                      @Param("md5") String md5,
                                      @Param("uploader") String uploader,
                                      @Param("offset") int offset,
                                      @Param("pageSize") int pageSize);

    /**
     * 插入安装包记录。
     *
     * @param pkg 安装包实体
     * @return 影响行数
     */
    int insert(OperationPackage pkg);

    /**
     * 更新安装包记录。
     *
     * @param pkg 安装包实体
     * @return 影响行数
     */
    int updateById(OperationPackage pkg);

    /**
     * 删除安装包记录。
     *
     * @param id 安装包ID
     * @return 影响行数
     */
    int deleteById(@Param("id") Long id);
}
