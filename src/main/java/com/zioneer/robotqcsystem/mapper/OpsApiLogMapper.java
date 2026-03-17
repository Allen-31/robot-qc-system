package com.zioneer.robotqcsystem.mapper;

import com.zioneer.robotqcsystem.domain.entity.OpsApiLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * API log mapper.
 */
@Mapper
public interface OpsApiLogMapper {

    long countList(@Param("keyword") String keyword,
                   @Param("callResult") String callResult);

    List<OpsApiLog> selectList(@Param("keyword") String keyword,
                                     @Param("callResult") String callResult,
                                     @Param("offset") int offset,
                                     @Param("pageSize") int pageSize);

    int insert(OpsApiLog log);
}
