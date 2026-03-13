package com.zioneer.robotqcsystem.service;

import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.domain.dto.RobotChargeStrategyCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.RobotChargeStrategyQuery;
import com.zioneer.robotqcsystem.domain.dto.RobotChargeStrategyUpdateDTO;
import com.zioneer.robotqcsystem.domain.vo.RobotChargeStrategyVO;

/**
 * 充电策略业务接口。
 */
public interface RobotChargeStrategyService {

    /**
     * 分页查询充电策略。
     *
     * @param query 查询条件
     * @return 分页结果
     */
    PageResult<RobotChargeStrategyVO> page(RobotChargeStrategyQuery query);

    /**
     * 根据编码查询充电策略详情。
     *
     * @param code 策略编码
     * @return 详情
     */
    RobotChargeStrategyVO getByCode(String code);

    /**
     * 创建充电策略。
     *
     * @param dto 创建请求
     */
    void create(RobotChargeStrategyCreateDTO dto);

    /**
     * 更新充电策略。
     *
     * @param code 策略编码
     * @param dto 更新请求
     */
    void update(String code, RobotChargeStrategyUpdateDTO dto);

    /**
     * 删除充电策略。
     *
     * @param code 策略编码
     */
    void deleteByCode(String code);
}
