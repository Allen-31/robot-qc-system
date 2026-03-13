package com.zioneer.robotqcsystem.mapper;

import com.zioneer.robotqcsystem.domain.entity.QcTerminal;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface QcTerminalMapper {

    List<QcTerminal> selectList(@Param("workstationId") String workstationId);

    QcTerminal selectById(@Param("id") Long id);

    QcTerminal selectByCode(@Param("code") String code);

    int insert(QcTerminal entity);

    int updateById(QcTerminal entity);

    int deleteById(@Param("id") Long id);
}
