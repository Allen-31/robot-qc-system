package com.zioneer.robotqcsystem.service.deploy.scene;

import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.domain.dto.DeployDeviceCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.DeployDeviceUpdateDTO;
import com.zioneer.robotqcsystem.domain.vo.DeployDeviceVO;

/**
 * 设备管理业务接口（3.3.2）
 */
public interface DeviceService {

    /**
     * 设备列表（按 mapCode 筛选，返回 list + total）
     */
    PageResult<DeployDeviceVO> list(String mapCode);

    /**
     * 新增设备，返回设备信息（含 id/code 供前端使用）
     */
    DeployDeviceVO create(DeployDeviceCreateDTO dto);

    /**
     * 更新设备（按主键ID）
     */
    void update(Long id, DeployDeviceUpdateDTO dto);

    /**
     * 删除设备（按主键ID）
     */
    void deleteById(Long id);
}
