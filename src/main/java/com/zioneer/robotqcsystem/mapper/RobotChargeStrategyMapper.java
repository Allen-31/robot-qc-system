package com.zioneer.robotqcsystem.mapper;

import com.zioneer.robotqcsystem.domain.entity.RobotChargeStrategy;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 充电策略 Mapper。
 */
@Mapper
public interface RobotChargeStrategyMapper {

    RobotChargeStrategy selectByCode(@Param("code") String code);

    List<RobotChargeStrategy> selectList(@Param("keyword") String keyword,
                                         @Param("status") String status,
                                         @Param("offset") int offset,
                                         @Param("limit") int limit);

    long countList(@Param("keyword") String keyword, @Param("status") String status);

    int insert(RobotChargeStrategy strategy);

    int updateByCode(RobotChargeStrategy strategy);

    int deleteByCode(@Param("code") String code);
}
