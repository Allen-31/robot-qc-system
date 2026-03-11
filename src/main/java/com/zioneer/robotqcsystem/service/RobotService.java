package com.zioneer.robotqcsystem.service;

import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.domain.dto.RobotCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.RobotQuery;
import com.zioneer.robotqcsystem.domain.dto.RobotUpdateDTO;
import com.zioneer.robotqcsystem.domain.vo.RobotVO;

/**
 * 机器人业务接口
 */
public interface RobotService {

    RobotVO getById(Long id);

    RobotVO getByRobotCode(String robotCode);

    PageResult<RobotVO> page(RobotQuery query);

    Long create(RobotCreateDTO dto);

    void update(Long id, RobotUpdateDTO dto);

    void deleteById(Long id);
}
