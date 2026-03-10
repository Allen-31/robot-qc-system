package com.zioneer.robotqcsystem.mapper;

import com.zioneer.robotqcsystem.domain.entity.RolePermission;
import com.zioneer.robotqcsystem.domain.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统角色 Mapper
 */
@Mapper
public interface SysRoleMapper {

    SysRole selectByCode(@Param("code") String code);

    List<SysRole> selectList(@Param("keyword") String keyword);

    int insert(SysRole role);

    int updateByCode(SysRole role);

    int deleteByCode(@Param("code") String code);

    int countUsersByRole(@Param("roleCode") String roleCode);

    List<RolePermission> selectPermissionsByRole(@Param("roleCode") String roleCode);

    int deletePermissionsByRole(@Param("roleCode") String roleCode);

    int insertPermission(@Param("rp") RolePermission rp);
}
