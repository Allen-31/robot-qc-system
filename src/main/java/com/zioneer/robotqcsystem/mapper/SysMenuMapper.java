package com.zioneer.robotqcsystem.mapper;

import com.zioneer.robotqcsystem.domain.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统菜单 Mapper
 */
@Mapper
public interface SysMenuMapper {

    SysMenu selectById(@Param("id") Long id);

    SysMenu selectByCode(@Param("code") String code);

    List<SysMenu> selectList(@Param("status") String status);

    List<SysMenu> selectByParentId(@Param("parentId") Long parentId, @Param("status") String status);

    int countByParentId(@Param("parentId") Long parentId);

    int insert(SysMenu menu);

    int updateById(SysMenu menu);

    int deleteById(@Param("id") Long id);
}
