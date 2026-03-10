package com.zioneer.robotqcsystem.service.impl;

import com.zioneer.robotqcsystem.common.exception.BusinessException;
import com.zioneer.robotqcsystem.common.page.PageQuery;
import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.common.result.ResultCode;
import com.zioneer.robotqcsystem.domain.entity.Robot;
import com.zioneer.robotqcsystem.mapper.RobotMapper;
import com.zioneer.robotqcsystem.service.RobotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 机器人业务实现
 */
@Service
@RequiredArgsConstructor
public class RobotServiceImpl implements RobotService {

    private final RobotMapper robotMapper;

    @Override
    public Robot getById(Long id) {
        Robot robot = robotMapper.selectById(id);
        if (robot == null) {
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "机器人不存在");
        }
        return robot;
    }

    @Override
    public Robot getByRobotCode(String robotCode) {
        return robotMapper.selectByRobotCode(robotCode);
    }

    @Override
    public PageResult<Robot> page(String robotCode, String status, PageQuery pageQuery) {
        long total = robotMapper.countList(robotCode, status);
        if (total == 0) {
            return PageResult.empty(pageQuery);
        }
        List<Robot> list = robotMapper.selectList(robotCode, status,
                pageQuery.getOffset(), pageQuery.getPageSize());
        return PageResult.of(list, total, pageQuery.getPageNum(), pageQuery.getPageSize());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long create(Robot robot) {
        if (robotMapper.selectByRobotCode(robot.getRobotCode()) != null) {
            throw new BusinessException("机器人编码已存在: " + robot.getRobotCode());
        }
        robotMapper.insert(robot);
        return robot.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Robot robot) {
        if (robot.getId() == null) {
            throw new BusinessException("更新时 id 不能为空");
        }
        if (robotMapper.selectById(robot.getId()) == null) {
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "机器人不存在");
        }
        robotMapper.updateById(robot);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        if (robotMapper.selectById(id) == null) {
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "机器人不存在");
        }
        robotMapper.deleteById(id);
    }
}
