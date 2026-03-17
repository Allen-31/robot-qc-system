package com.zioneer.robotqcsystem.mapper;

import com.zioneer.robotqcsystem.domain.entity.OpsExceptionNotification;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Exception notification mapper.
 */
@Mapper
public interface OpsExceptionNotificationMapper {

    OpsExceptionNotification selectById(@Param("id") Long id);

    OpsExceptionNotification selectByCode(@Param("code") String code);

    long countList(@Param("keyword") String keyword,
                   @Param("level") String level,
                   @Param("status") String status);

    List<OpsExceptionNotification> selectList(@Param("keyword") String keyword,
                                                    @Param("level") String level,
                                                    @Param("status") String status,
                                                    @Param("offset") int offset,
                                                    @Param("pageSize") int pageSize);

    int insert(OpsExceptionNotification notification);

    int updateStatus(@Param("id") Long id,
                     @Param("status") String status,
                     @Param("updatedAt") LocalDateTime updatedAt);
}
