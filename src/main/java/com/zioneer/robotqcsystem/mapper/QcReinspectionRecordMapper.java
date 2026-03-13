package com.zioneer.robotqcsystem.mapper;

import com.zioneer.robotqcsystem.domain.entity.QcReinspectionRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface QcReinspectionRecordMapper {

    QcReinspectionRecord selectById(@Param("id") Long id);

    List<QcReinspectionRecord> selectList(@Param("keyword") String keyword,
                                          @Param("status") String status,
                                          @Param("reinspectionResult") String reinspectionResult,
                                          @Param("dateFrom") String dateFrom,
                                          @Param("dateTo") String dateTo,
                                          @Param("offset") int offset,
                                          @Param("limit") int limit);

    long countList(@Param("keyword") String keyword,
                   @Param("status") String status,
                   @Param("reinspectionResult") String reinspectionResult,
                   @Param("dateFrom") String dateFrom,
                   @Param("dateTo") String dateTo);
}
