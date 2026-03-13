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

    /** 若 code 不存在则插入，用于 Keycloak 角色在本地 sys_role 占位以满足 sys_role_permission 外键 */
    int insertIfNotExists(SysRole role);

    int updateByCode(SysRole role);

    int deleteByCode(@Param("code") String code);

    int countUsersByRole(@Param("roleCode") String roleCode);

    List<RolePermission> selectPermissionsByRole(@Param("roleCode") String roleCode);

    int deletePermissionsByRole(@Param("roleCode") String roleCode);

    int insertPermission(@Param("rp") RolePermission rp);
}
