package com.zioneer.robotqcsystem.mapper;

import com.zioneer.robotqcsystem.domain.entity.QcWorkshop;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface QcWorkshopMapper {

    List<QcWorkshop> selectList();

    QcWorkshop selectByCode(@Param("code") String code);

    int insert(QcWorkshop entity);

    int updateByCode(QcWorkshop entity);

    int deleteByCode(@Param("code") String code);
}
