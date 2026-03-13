package com.zioneer.robotqcsystem.mapper;

import com.zioneer.robotqcsystem.domain.entity.SceneMap;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 场景地图 Mapper。
 */
@Mapper
public interface SceneMapMapper {

    SceneMap selectByCode(@Param("code") String code);

    List<SceneMap> selectList(@Param("keyword") String keyword,
                              @Param("type") String type,
                              @Param("editStatus") String editStatus,
                              @Param("publishStatus") String publishStatus,
                              @Param("offset") int offset,
                              @Param("limit") int limit);

    long countList(@Param("keyword") String keyword,
                   @Param("type") String type,
                   @Param("editStatus") String editStatus,
                   @Param("publishStatus") String publishStatus);

    int insert(SceneMap map);

    int updateByCode(SceneMap map);

    int deleteByCode(@Param("code") String code);
}
