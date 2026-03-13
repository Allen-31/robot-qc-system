package com.zioneer.robotqcsystem.service.deploy.scene;

import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.domain.dto.SceneDeviceCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.SceneDeviceQuery;
import com.zioneer.robotqcsystem.domain.dto.SceneDeviceUpdateDTO;
import com.zioneer.robotqcsystem.domain.vo.SceneDeviceVO;

/**
 * 场景设备业务接口。
 */
public interface SceneDeviceService {

    PageResult<SceneDeviceVO> page(SceneDeviceQuery query);

    SceneDeviceVO getByCode(String code);

    void create(SceneDeviceCreateDTO dto);

    void update(String code, SceneDeviceUpdateDTO dto);

    void deleteByCode(String code);
}
