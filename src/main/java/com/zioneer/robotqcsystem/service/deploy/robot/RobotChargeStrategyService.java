package com.zioneer.robotqcsystem.service.deploy.robot;

import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.domain.dto.RobotChargeStrategyCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.RobotChargeStrategyQuery;
import com.zioneer.robotqcsystem.domain.dto.RobotChargeStrategyUpdateDTO;
import com.zioneer.robotqcsystem.domain.vo.RobotChargeStrategyVO;

/**
 * 充电策略业务接口。
 */
public interface RobotChargeStrategyService {

    PageResult<RobotChargeStrategyVO> page(RobotChargeStrategyQuery query);

    RobotChargeStrategyVO getByCode(String code);

    void create(RobotChargeStrategyCreateDTO dto);

    void update(String code, RobotChargeStrategyUpdateDTO dto);

    void deleteByCode(String code);
}
