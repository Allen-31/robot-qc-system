package com.zioneer.robotqcsystem.mapper;

import com.zioneer.robotqcsystem.domain.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统用户 Mapper
 */
@Mapper
public interface SysUserMapper {

    SysUser selectByCode(@Param("code") String code);

    SysUser selectByCodeWithRoles(@Param("code") String code);

    List<SysUser> selectList(@Param("keyword") String keyword,
                             @Param("role") String role,
                             @Param("status") String status,
                             @Param("offset") int offset,
                             @Param("limit") int limit);

    long countList(@Param("keyword") String keyword,
                   @Param("role") String role,
                   @Param("status") String status);

    int insert(SysUser user);

    int updateByCode(SysUser user);

    int updateLastLoginAt(@Param("code") String code, @Param("lastLoginAt") java.time.LocalDateTime lastLoginAt);

    int updatePasswordHash(@Param("code") String code, @Param("passwordHash") String passwordHash, @Param("updatedAt") java.time.LocalDateTime updatedAt);

    int deleteByCode(@Param("code") String code);

    int deleteUserRoles(@Param("userCode") String userCode);

    int insertUserRole(@Param("userCode") String userCode, @Param("roleCode") String roleCode);
}
