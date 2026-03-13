package com.zioneer.robotqcsystem.mapper;

import com.zioneer.robotqcsystem.domain.entity.RobotGroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 机器人分组 Mapper。
 */
@Mapper
public interface RobotGroupMapper {

    RobotGroup selectById(@Param("id") Long id);

    RobotGroup selectByGroupNo(@Param("groupNo") String groupNo);

    List<RobotGroup> selectList(@Param("keyword") String keyword,
                                @Param("offset") int offset,
                                @Param("limit") int limit);

    long countList(@Param("keyword") String keyword);

    int insert(RobotGroup group);

    int updateById(RobotGroup group);

    int deleteById(@Param("id") Long id);
}
