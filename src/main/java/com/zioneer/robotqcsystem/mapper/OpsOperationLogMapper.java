package com.zioneer.robotqcsystem.mapper;

import com.zioneer.robotqcsystem.domain.entity.OpsOperationLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Operation log mapper.
 */
@Mapper
public interface OpsOperationLogMapper {

    long countList(@Param("keyword") String keyword,
                   @Param("result") String result);

    List<OpsOperationLog> selectList(@Param("keyword") String keyword,
                                           @Param("result") String result,
                                           @Param("offset") int offset,
                                           @Param("pageSize") int pageSize);

    int insert(OpsOperationLog log);
}
