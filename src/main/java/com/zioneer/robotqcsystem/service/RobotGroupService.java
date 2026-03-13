package com.zioneer.robotqcsystem.service;

import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.domain.dto.RobotGroupCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.RobotGroupQuery;
import com.zioneer.robotqcsystem.domain.dto.RobotGroupUpdateDTO;
import com.zioneer.robotqcsystem.domain.vo.RobotGroupVO;

/**
 * 机器人分组业务接口。
 */
public interface RobotGroupService {

    /**
     * 分页查询分组。
     *
     * @param query 查询条件
     * @return 分页结果
     */
    PageResult<RobotGroupVO> page(RobotGroupQuery query);

    /**
     * 根据ID查询分组详情。
     *
     * @param id 主键
     * @return 详情
     */
    RobotGroupVO getById(Long id);

    /**
     * 创建分组。
     *
     * @param dto 创建请求
     * @return 主键
     */
    Long create(RobotGroupCreateDTO dto);

    /**
     * 更新分组。
     *
     * @param id 主键
     * @param dto 更新请求
     */
    void update(Long id, RobotGroupUpdateDTO dto);

    /**
     * 删除分组。
     *
     * @param id 主键
     */
    void deleteById(Long id);
}
