package com.zioneer.robotqcsystem.mapper;

import com.zioneer.robotqcsystem.domain.entity.QcWireHarnessType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface QcWireHarnessTypeMapper {

    List<QcWireHarnessType> selectList();

    QcWireHarnessType selectById(@Param("id") Long id);

    int insert(QcWireHarnessType entity);

    int updateById(QcWireHarnessType entity);

    int deleteById(@Param("id") Long id);
}
