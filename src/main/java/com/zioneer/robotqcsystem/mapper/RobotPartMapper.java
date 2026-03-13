package com.zioneer.robotqcsystem.mapper;

import com.zioneer.robotqcsystem.domain.entity.RobotPart;
import com.zioneer.robotqcsystem.domain.entity.RobotPartParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 机器人零部件 Mapper。
 */
@Mapper
public interface RobotPartMapper {

    RobotPart selectById(@Param("id") Long id);

    RobotPart selectByPartNo(@Param("partNo") String partNo);

    List<RobotPart> selectList(@Param("keyword") String keyword,
                               @Param("type") String type,
                               @Param("status") String status,
                               @Param("offset") int offset,
                               @Param("limit") int limit);

    long countList(@Param("keyword") String keyword,
                   @Param("type") String type,
                   @Param("status") String status);

    int insert(RobotPart part);

    int updateById(RobotPart part);

    int deleteById(@Param("id") Long id);

    List<RobotPartParam> selectParamsByPartId(@Param("partId") Long partId);

    int deleteParamsByPartId(@Param("partId") Long partId);

    int insertParam(@Param("param") RobotPartParam param);
}
