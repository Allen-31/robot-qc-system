package com.zioneer.robotqcsystem.mapper;

import com.zioneer.robotqcsystem.domain.entity.OperationFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 运营文件 Mapper（4.4.1 文件管理）
 */
@Mapper
public interface OperationFileMapper {

    OperationFile selectById(@Param("id") Long id);

    long countList(@Param("keyword") String keyword,
                   @Param("type") String type,
                   @Param("tagList") List<String> tagList);

    List<OperationFile> selectList(@Param("keyword") String keyword,
                                   @Param("type") String type,
                                   @Param("tagList") List<String> tagList,
                                   @Param("offset") int offset,
                                   @Param("pageSize") int pageSize);

    int insert(OperationFile file);

    int deleteById(@Param("id") Long id);
}
