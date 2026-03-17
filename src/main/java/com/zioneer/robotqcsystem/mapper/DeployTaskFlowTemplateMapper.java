package com.zioneer.robotqcsystem.mapper;

import com.zioneer.robotqcsystem.domain.entity.DeployTaskFlowTemplate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 任务流模板Mapper。
 */
@Mapper
public interface DeployTaskFlowTemplateMapper {

    DeployTaskFlowTemplate selectByCode(@Param("code") String code);

    long countList(@Param("keyword") String keyword,
                   @Param("enabled") Boolean enabled);

    List<DeployTaskFlowTemplate> selectList(@Param("keyword") String keyword,
                                            @Param("enabled") Boolean enabled,
                                            @Param("offset") int offset,
                                            @Param("pageSize") int pageSize);

    int insert(DeployTaskFlowTemplate template);

    int updateByCode(DeployTaskFlowTemplate template);

    int deleteByCode(@Param("code") String code);

    long countTaskReferences(@Param("code") String code);
}