package com.zioneer.robotqcsystem.service;

import com.zioneer.robotqcsystem.common.page.PageQuery;
import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.domain.entity.Robot;

/**
 * 机器人业务接口
 */
public interface RobotService {

    Robot getById(Long id);

    Robot getByRobotCode(String robotCode);

    PageResult<Robot> page(String robotCode, String status, PageQuery pageQuery);

    Long create(Robot robot);

    void update(Robot robot);

    void deleteById(Long id);
}
