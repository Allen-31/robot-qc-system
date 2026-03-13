package com.zioneer.robotqcsystem.mapper;

import com.zioneer.robotqcsystem.domain.entity.RobotType;
import com.zioneer.robotqcsystem.domain.entity.RobotTypePoint;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 机器人类型 Mapper。
 */
@Mapper
public interface RobotTypeMapper {

    RobotType selectById(@Param("id") Long id);

    RobotType selectByTypeNo(@Param("typeNo") String typeNo);

    List<RobotType> selectList(@Param("keyword") String keyword,
                               @Param("status") String status,
                               @Param("offset") int offset,
                               @Param("limit") int limit);

    long countList(@Param("keyword") String keyword, @Param("status") String status);

    int insert(RobotType robotType);

    int updateById(RobotType robotType);

    int deleteById(@Param("id") Long id);

    List<RobotTypePoint> selectPointsByTypeId(@Param("robotTypeId") Long robotTypeId);

    int deletePointsByTypeId(@Param("robotTypeId") Long robotTypeId);

    int insertPoint(@Param("point") RobotTypePoint point);
}
