package com.zioneer.robotqcsystem.mapper;

import com.zioneer.robotqcsystem.domain.entity.QcStation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface QcStationMapper {

    List<QcStation> selectList(@Param("workstationId") Long workstationId);

    QcStation selectById(@Param("id") Long id);

    QcStation selectByWorkstationAndStationId(@Param("workstationId") Long workstationId, @Param("stationId") String stationId);

    int insert(QcStation entity);

    int updateById(QcStation entity);

    int deleteById(@Param("id") Long id);
}
