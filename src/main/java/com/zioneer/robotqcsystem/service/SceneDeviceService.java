package com.zioneer.robotqcsystem.service;

import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.domain.dto.SceneDeviceCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.SceneDeviceQuery;
import com.zioneer.robotqcsystem.domain.dto.SceneDeviceUpdateDTO;
import com.zioneer.robotqcsystem.domain.vo.SceneDeviceVO;

/**
 * 场景设备业务接口。
 */
public interface SceneDeviceService {

    /**
     * 分页查询场景设备。
     *
     * @param query 查询条件
     * @return 分页结果
     */
    PageResult<SceneDeviceVO> page(SceneDeviceQuery query);

    /**
     * 根据编码查询场景设备详情。
     *
     * @param code 设备编码
     * @return 详情
     */
    SceneDeviceVO getByCode(String code);

    /**
     * 创建场景设备。
     *
     * @param dto 创建请求
     */
    void create(SceneDeviceCreateDTO dto);

    /**
     * 更新场景设备。
     *
     * @param code 设备编码
     * @param dto 更新请求
     */
    void update(String code, SceneDeviceUpdateDTO dto);

    /**
     * 删除场景设备。
     *
     * @param code 设备编码
     */
    void deleteByCode(String code);
}
