package com.zioneer.robotqcsystem.mapper;

import com.zioneer.robotqcsystem.domain.entity.QcWorkstation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface QcWorkstationMapper {

    QcWorkstation selectById(@Param("id") Long id);

    List<QcWorkstation> selectList(@Param("keyword") String keyword, @Param("workshopCode") String workshopCode);

    int insert(QcWorkstation entity);

    int updateById(QcWorkstation entity);

    int deleteById(@Param("id") Long id);
}
