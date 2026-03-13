package com.zioneer.robotqcsystem.service.deploy.robot;

import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.domain.dto.RobotGroupCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.RobotGroupQuery;
import com.zioneer.robotqcsystem.domain.dto.RobotGroupUpdateDTO;
import com.zioneer.robotqcsystem.domain.vo.RobotGroupVO;

/**
 * 机器人分组业务接口。
 */
public interface RobotGroupService {

    PageResult<RobotGroupVO> page(RobotGroupQuery query);

    RobotGroupVO getById(Long id);

    Long create(RobotGroupCreateDTO dto);

    void update(Long id, RobotGroupUpdateDTO dto);

    void deleteById(Long id);
}
