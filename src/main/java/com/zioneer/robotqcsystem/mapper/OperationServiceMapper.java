package com.zioneer.robotqcsystem.mapper;

import com.zioneer.robotqcsystem.domain.entity.OperationService;
import com.zioneer.robotqcsystem.domain.entity.OperationServiceLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Operation service mapper.
 */
@Mapper
public interface OperationServiceMapper {

    OperationService selectById(@Param("id") Long id);

    long countList(@Param("keyword") String keyword,
                   @Param("type") String type,
                   @Param("version") String version,
                   @Param("ip") String ip,
                   @Param("status") String status);

    List<OperationService> selectList(@Param("keyword") String keyword,
                                      @Param("type") String type,
                                      @Param("version") String version,
                                      @Param("ip") String ip,
                                      @Param("status") String status,
                                      @Param("offset") int offset,
                                      @Param("pageSize") int pageSize);

    int updateStatus(@Param("id") Long id,
                     @Param("status") String status,
                     @Param("updatedAt") LocalDateTime updatedAt);

    List<OperationServiceLog> selectLogsByServiceIds(@Param("serviceIds") List<Long> serviceIds);

    long countLogs(@Param("serviceId") Long serviceId,
                   @Param("type") String type);

    List<OperationServiceLog> selectLogList(@Param("serviceId") Long serviceId,
                                            @Param("type") String type,
                                            @Param("offset") int offset,
                                            @Param("pageSize") int pageSize);

    int insertLog(OperationServiceLog log);
}
