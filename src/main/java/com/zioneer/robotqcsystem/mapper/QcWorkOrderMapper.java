package com.zioneer.robotqcsystem.mapper;

import com.zioneer.robotqcsystem.domain.entity.QcWorkOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface QcWorkOrderMapper {

    QcWorkOrder selectById(@Param("id") Long id);

    List<QcWorkOrder> selectList(@Param("keyword") String keyword,
                                 @Param("statusIn") List<String> statusIn,
                                 @Param("qualityResult") String qualityResult,
                                 @Param("stationCode") String stationCode,
                                 @Param("harnessType") String harnessType,
                                 @Param("dateFrom") String dateFrom,
                                 @Param("dateTo") String dateTo,
                                 @Param("orderBy") String orderBy,
                                 @Param("offset") int offset,
                                 @Param("limit") int limit);

    long countList(@Param("keyword") String keyword,
                   @Param("statusIn") List<String> statusIn,
                   @Param("qualityResult") String qualityResult,
                   @Param("stationCode") String stationCode,
                   @Param("harnessType") String harnessType,
                   @Param("dateFrom") String dateFrom,
                   @Param("dateTo") String dateTo);

    int updateById(QcWorkOrder entity);

    int deleteById(@Param("id") Long id);
}
