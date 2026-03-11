package com.zioneer.robotqcsystem.service.impl;

import com.zioneer.robotqcsystem.common.exception.BusinessException;
import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.common.result.ResultCode;
import com.zioneer.robotqcsystem.domain.dto.RobotCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.RobotQuery;
import com.zioneer.robotqcsystem.domain.dto.RobotUpdateDTO;
import com.zioneer.robotqcsystem.domain.entity.Robot;
import com.zioneer.robotqcsystem.domain.vo.RobotVO;
import com.zioneer.robotqcsystem.mapper.RobotMapper;
import com.zioneer.robotqcsystem.service.RobotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 机器人业务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RobotServiceImpl implements RobotService {

    private final RobotMapper robotMapper;

    @Override
    public RobotVO getById(Long id) {
        Robot robot = robotMapper.selectById(id);
        if (robot == null) {
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "机器人不存在");
        }
        return toVO(robot);
    }

    @Override
    public RobotVO getByRobotCode(String robotCode) {
        Robot robot = robotMapper.selectByRobotCode(robotCode);
        return robot == null ? null : toVO(robot);
    }

    @Override
    public PageResult<RobotVO> page(RobotQuery query) {
        long total = robotMapper.countList(query.getRobotCode(), query.getStatus());
        if (total == 0) {
            return PageResult.empty(query);
        }
        List<Robot> list = robotMapper.selectList(query.getRobotCode(), query.getStatus(),
                query.getOffset(), query.getPageSize());
        List<RobotVO> voList = list.stream().map(this::toVO).collect(Collectors.toList());
        return PageResult.of(voList, total, query.getPageNum(), query.getPageSize());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long create(RobotCreateDTO dto) {
        if (robotMapper.selectByRobotCode(dto.getRobotCode()) != null) {
            log.warn("create robot failed, robotCode already exists: {}", dto.getRobotCode());
            throw new BusinessException("机器人编码已存在: " + dto.getRobotCode());
        }
        Robot robot = toEntity(dto);
        robotMapper.insert(robot);
        log.info("create robot, robotCode={}, id={}", robot.getRobotCode(), robot.getId());
        return robot.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Long id, RobotUpdateDTO dto) {
        Robot exist = robotMapper.selectById(id);
        if (exist == null) {
            log.warn("update robot failed, robot not found: id={}", id);
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "机器人不存在");
        }
        Robot robot = Robot.builder()
                .id(id)
                .robotCode(exist.getRobotCode())
                .robotName(dto.getRobotName())
                .model(dto.getModel())
                .status(dto.getStatus())
                .location(dto.getLocation())
                .lastInspectionAt(dto.getLastInspectionAt())
                .build();
        robotMapper.updateById(robot);
        log.info("update robot, id={}, robotCode={}", id, exist.getRobotCode());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        Robot exist = robotMapper.selectById(id);
        if (exist == null) {
            log.warn("delete robot failed, robot not found: id={}", id);
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "机器人不存在");
        }
        robotMapper.deleteById(id);
        log.info("delete robot, id={}, robotCode={}", id, exist.getRobotCode());
    }

    private Robot toEntity(RobotCreateDTO dto) {
        return Robot.builder()
                .robotCode(dto.getRobotCode())
                .robotName(dto.getRobotName())
                .model(dto.getModel())
                .status(dto.getStatus())
                .location(dto.getLocation())
                .lastInspectionAt(dto.getLastInspectionAt())
                .build();
    }

    private RobotVO toVO(Robot robot) {
        return RobotVO.builder()
                .id(robot.getId())
                .robotCode(robot.getRobotCode())
                .robotName(robot.getRobotName())
                .model(robot.getModel())
                .status(robot.getStatus())
                .location(robot.getLocation())
                .lastInspectionAt(robot.getLastInspectionAt())
                .build();
    }
}
