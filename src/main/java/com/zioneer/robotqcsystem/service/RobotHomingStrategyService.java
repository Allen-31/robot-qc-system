package com.zioneer.robotqcsystem.service;

import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.domain.dto.RobotHomingStrategyCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.RobotHomingStrategyQuery;
import com.zioneer.robotqcsystem.domain.dto.RobotHomingStrategyUpdateDTO;
import com.zioneer.robotqcsystem.domain.vo.RobotHomingStrategyVO;

/**
 * 归位策略业务接口。
 */
public interface RobotHomingStrategyService {

    /**
     * 分页查询归位策略。
     *
     * @param query 查询条件
     * @return 分页结果
     */
    PageResult<RobotHomingStrategyVO> page(RobotHomingStrategyQuery query);

    /**
     * 根据编码查询归位策略详情。
     *
     * @param code 策略编码
     * @return 详情
     */
    RobotHomingStrategyVO getByCode(String code);

    /**
     * 创建归位策略。
     *
     * @param dto 创建请求
     */
    void create(RobotHomingStrategyCreateDTO dto);

    /**
     * 更新归位策略。
     *
     * @param code 策略编码
     * @param dto 更新请求
     */
    void update(String code, RobotHomingStrategyUpdateDTO dto);

    /**
     * 删除归位策略。
     *
     * @param code 策略编码
     */
    void deleteByCode(String code);
}
