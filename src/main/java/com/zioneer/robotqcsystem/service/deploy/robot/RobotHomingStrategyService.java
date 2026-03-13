package com.zioneer.robotqcsystem.service.deploy.robot;

import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.domain.dto.RobotHomingStrategyCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.RobotHomingStrategyQuery;
import com.zioneer.robotqcsystem.domain.dto.RobotHomingStrategyUpdateDTO;
import com.zioneer.robotqcsystem.domain.vo.RobotHomingStrategyVO;

/**
 * 归位策略业务接口。
 */
public interface RobotHomingStrategyService {

    PageResult<RobotHomingStrategyVO> page(RobotHomingStrategyQuery query);

    RobotHomingStrategyVO getByCode(String code);

    void create(RobotHomingStrategyCreateDTO dto);

    void update(String code, RobotHomingStrategyUpdateDTO dto);

    void deleteByCode(String code);
}
