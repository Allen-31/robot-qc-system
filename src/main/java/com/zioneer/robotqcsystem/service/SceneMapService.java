package com.zioneer.robotqcsystem.service;

import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.domain.dto.SceneMapCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.SceneMapQuery;
import com.zioneer.robotqcsystem.domain.dto.SceneMapUpdateDTO;
import com.zioneer.robotqcsystem.domain.vo.SceneMapVO;

/**
 * 场景地图业务接口。
 */
public interface SceneMapService {

    /**
     * 分页查询场景地图。
     *
     * @param query 查询条件
     * @return 分页结果
     */
    PageResult<SceneMapVO> page(SceneMapQuery query);

    /**
     * 根据编码查询地图详情。
     *
     * @param code 地图编码
     * @return 详情
     */
    SceneMapVO getByCode(String code);

    /**
     * 创建地图。
     *
     * @param dto 创建请求
     */
    void create(SceneMapCreateDTO dto);

    /**
     * 更新地图。
     *
     * @param code 地图编码
     * @param dto 更新请求
     */
    void update(String code, SceneMapUpdateDTO dto);

    /**
     * 删除地图。
     *
     * @param code 地图编码
     */
    void deleteByCode(String code);
}
