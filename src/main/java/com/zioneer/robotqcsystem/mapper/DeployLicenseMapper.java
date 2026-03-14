package com.zioneer.robotqcsystem.mapper;

import com.zioneer.robotqcsystem.domain.entity.DeployLicense;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 许可证Mapper。
 */
@Mapper
public interface DeployLicenseMapper {

    DeployLicense selectById(@Param("id") Long id);

    long countList(@Param("keyword") String keyword,
                   @Param("status") String status,
                   @Param("expired") Boolean expired,
                   @Param("excludeExpired") Boolean excludeExpired,
                   @Param("now") LocalDateTime now);

    List<DeployLicense> selectList(@Param("keyword") String keyword,
                                   @Param("status") String status,
                                   @Param("expired") Boolean expired,
                                   @Param("excludeExpired") Boolean excludeExpired,
                                   @Param("now") LocalDateTime now,
                                   @Param("offset") int offset,
                                   @Param("pageSize") int pageSize);

    int existsByName(@Param("name") String name);

    int existsByMd5(@Param("md5") String md5);

    int insert(DeployLicense license);

    int deleteById(@Param("id") Long id);
}