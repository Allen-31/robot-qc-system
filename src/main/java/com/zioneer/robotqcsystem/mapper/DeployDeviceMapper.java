package com.zioneer.robotqcsystem.mapper;

import com.zioneer.robotqcsystem.domain.entity.DeployDevice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 部署设备 Mapper（3.3.2 设备管理）
 */
@Mapper
public interface DeployDeviceMapper {

    DeployDevice selectById(@Param("id") Long id);

    DeployDevice selectByCode(@Param("code") String code);

    List<DeployDevice> selectListByMapCode(@Param("mapCode") String mapCode);

    int insert(DeployDevice device);

    int updateById(DeployDevice device);

    int deleteById(@Param("id") Long id);
}
