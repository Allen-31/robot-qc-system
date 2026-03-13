package com.zioneer.robotqcsystem.service.deploy.robot;

import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.domain.dto.RobotPartCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.RobotPartQuery;
import com.zioneer.robotqcsystem.domain.dto.RobotPartUpdateDTO;
import com.zioneer.robotqcsystem.domain.vo.RobotPartVO;

/**
 * 机器人零部件业务接口。
 */
public interface RobotPartService {

    PageResult<RobotPartVO> page(RobotPartQuery query);

    RobotPartVO getById(Long id);

    Long create(RobotPartCreateDTO dto);

    void update(Long id, RobotPartUpdateDTO dto);

    void deleteById(Long id);
}
