package com.zioneer.robotqcsystem.service.deploy.scene;

import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.domain.dto.SceneMapCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.SceneMapQuery;
import com.zioneer.robotqcsystem.domain.dto.SceneMapUpdateDTO;
import com.zioneer.robotqcsystem.domain.vo.SceneMapVO;

/**
 * 场景地图业务接口。
 */
public interface SceneMapService {

    PageResult<SceneMapVO> page(SceneMapQuery query);

    SceneMapVO getByCode(String code);

    void create(SceneMapCreateDTO dto);

    void update(String code, SceneMapUpdateDTO dto);

    void deleteByCode(String code);
}
