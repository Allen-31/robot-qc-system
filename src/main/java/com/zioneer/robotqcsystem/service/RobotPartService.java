package com.zioneer.robotqcsystem.service;

import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.domain.dto.RobotPartCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.RobotPartQuery;
import com.zioneer.robotqcsystem.domain.dto.RobotPartUpdateDTO;
import com.zioneer.robotqcsystem.domain.vo.RobotPartVO;

/**
 * 机器人零部件业务接口。
 */
public interface RobotPartService {

    /**
     * 分页查询零部件。
     *
     * @param query 查询条件
     * @return 分页结果
     */
    PageResult<RobotPartVO> page(RobotPartQuery query);

    /**
     * 根据ID查询零部件详情。
     *
     * @param id 主键
     * @return 详情
     */
    RobotPartVO getById(Long id);

    /**
     * 创建零部件。
     *
     * @param dto 创建请求
     * @return 主键
     */
    Long create(RobotPartCreateDTO dto);

    /**
     * 更新零部件。
     *
     * @param id 主键
     * @param dto 更新请求
     */
    void update(Long id, RobotPartUpdateDTO dto);

    /**
     * 删除零部件。
     *
     * @param id 主键
     */
    void deleteById(Long id);
}
