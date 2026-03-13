package com.zioneer.robotqcsystem.service.deploy.robot;

import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.domain.dto.RobotTypeCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.RobotTypeQuery;
import com.zioneer.robotqcsystem.domain.dto.RobotTypeUpdateDTO;
import com.zioneer.robotqcsystem.domain.vo.RobotTypeVO;

/**
 * 机器人类型业务接口。
 */
public interface RobotTypeService {

    PageResult<RobotTypeVO> page(RobotTypeQuery query);

    RobotTypeVO getById(Long id);

    Long create(RobotTypeCreateDTO dto);

    void update(Long id, RobotTypeUpdateDTO dto);

    void deleteById(Long id);
}
