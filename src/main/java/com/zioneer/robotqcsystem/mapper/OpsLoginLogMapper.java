package com.zioneer.robotqcsystem.mapper;

import com.zioneer.robotqcsystem.domain.entity.OpsLoginLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Login log mapper.
 */
@Mapper
public interface OpsLoginLogMapper {

    long countList(@Param("keyword") String keyword,
                   @Param("type") String type);

    List<OpsLoginLog> selectList(@Param("keyword") String keyword,
                                       @Param("type") String type,
                                       @Param("offset") int offset,
                                       @Param("pageSize") int pageSize);

    int insert(OpsLoginLog log);
}
