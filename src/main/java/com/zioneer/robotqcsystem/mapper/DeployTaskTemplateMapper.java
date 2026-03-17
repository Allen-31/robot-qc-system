package com.zioneer.robotqcsystem.mapper;

import com.zioneer.robotqcsystem.domain.entity.DeployTaskTemplate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 任务模板Mapper。
 */
@Mapper
public interface DeployTaskTemplateMapper {

    DeployTaskTemplate selectByCode(@Param("code") String code);

    long countList(@Param("keyword") String keyword,
                   @Param("enabled") Boolean enabled);

    List<DeployTaskTemplate> selectList(@Param("keyword") String keyword,
                                        @Param("enabled") Boolean enabled,
                                        @Param("offset") int offset,
                                        @Param("pageSize") int pageSize);

    int insert(DeployTaskTemplate template);

    int updateByCode(DeployTaskTemplate template);

    int deleteByCode(@Param("code") String code);

    long countTaskReferences(@Param("code") String code);
}