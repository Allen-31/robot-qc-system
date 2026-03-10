package com.zioneer.robotqcsystem.mapper;

import com.zioneer.robotqcsystem.domain.entity.Robot;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 机器人 Mapper
 */
@Mapper
public interface RobotMapper {

    Robot selectById(@Param("id") Long id);

    Robot selectByRobotCode(@Param("robotCode") String robotCode);

    List<Robot> selectList(@Param("robotCode") String robotCode,
                           @Param("status") String status,
                           @Param("offset") int offset,
                           @Param("limit") int limit);

    long countList(@Param("robotCode") String robotCode, @Param("status") String status);

    int insert(Robot robot);

    int updateById(Robot robot);

    int deleteById(@Param("id") Long id);
}
