package com.zioneer.robotqcsystem.service;

import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.domain.dto.RobotTypeCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.RobotTypeQuery;
import com.zioneer.robotqcsystem.domain.dto.RobotTypeUpdateDTO;
import com.zioneer.robotqcsystem.domain.vo.RobotTypeVO;

/**
 * 机器人类型业务接口。
 */
public interface RobotTypeService {

    /**
     * 分页查询机器人类型。
     *
     * @param query 查询条件
     * @return 分页结果
     */
    PageResult<RobotTypeVO> page(RobotTypeQuery query);

    /**
     * 根据ID查询机器人类型详情。
     *
     * @param id 主键
     * @return 详情
     */
    RobotTypeVO getById(Long id);

    /**
     * 创建机器人类型。
     *
     * @param dto 创建请求
     * @return 主键
     */
    Long create(RobotTypeCreateDTO dto);

    /**
     * 更新机器人类型。
     *
     * @param id 主键
     * @param dto 更新请求
     */
    void update(Long id, RobotTypeUpdateDTO dto);

    /**
     * 删除机器人类型。
     *
     * @param id 主键
     */
    void deleteById(Long id);
}
