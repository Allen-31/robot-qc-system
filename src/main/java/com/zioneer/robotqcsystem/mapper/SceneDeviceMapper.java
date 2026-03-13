package com.zioneer.robotqcsystem.mapper;

import com.zioneer.robotqcsystem.domain.entity.SceneDevice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 场景设备 Mapper。
 */
@Mapper
public interface SceneDeviceMapper {

    SceneDevice selectByCode(@Param("code") String code);

    List<SceneDevice> selectList(@Param("keyword") String keyword,
                                 @Param("onlineStatus") String onlineStatus,
                                 @Param("isAbnormal") Integer isAbnormal,
                                 @Param("mapCode") String mapCode,
                                 @Param("offset") int offset,
                                 @Param("limit") int limit);

    long countList(@Param("keyword") String keyword,
                   @Param("onlineStatus") String onlineStatus,
                   @Param("isAbnormal") Integer isAbnormal,
                   @Param("mapCode") String mapCode);

    int insert(SceneDevice device);

    int updateByCode(SceneDevice device);

    int deleteByCode(@Param("code") String code);
}
