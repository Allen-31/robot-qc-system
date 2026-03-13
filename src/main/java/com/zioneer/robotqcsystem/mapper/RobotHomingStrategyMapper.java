package com.zioneer.robotqcsystem.mapper;

import com.zioneer.robotqcsystem.domain.entity.RobotHomingStrategy;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 归位策略 Mapper。
 */
@Mapper
public interface RobotHomingStrategyMapper {

    RobotHomingStrategy selectByCode(@Param("code") String code);

    List<RobotHomingStrategy> selectList(@Param("keyword") String keyword,
                                         @Param("status") String status,
                                         @Param("offset") int offset,
                                         @Param("limit") int limit);

    long countList(@Param("keyword") String keyword, @Param("status") String status);

    int insert(RobotHomingStrategy strategy);

    int updateByCode(RobotHomingStrategy strategy);

    int deleteByCode(@Param("code") String code);
}
