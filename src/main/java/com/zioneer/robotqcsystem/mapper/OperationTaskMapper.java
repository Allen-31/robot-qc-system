package com.zioneer.robotqcsystem.mapper;

import com.zioneer.robotqcsystem.domain.entity.OperationTask;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 任务流Mapper。
 */
@Mapper
public interface OperationTaskMapper {

    OperationTask selectById(@Param("id") Long id);

    long countList(@Param("keyword") String keyword,
                   @Param("status") String status,
                   @Param("robot") String robot);

    List<OperationTask> selectList(@Param("keyword") String keyword,
                                   @Param("status") String status,
                                   @Param("robot") String robot,
                                   @Param("offset") int offset,
                                   @Param("pageSize") int pageSize);

    int updateStatus(@Param("id") Long id,
                     @Param("status") String status,
                     @Param("expectedStatus") String expectedStatus,
                     @Param("updatedAt") LocalDateTime updatedAt,
                     @Param("endedAt") LocalDateTime endedAt);
}