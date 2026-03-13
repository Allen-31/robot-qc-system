package com.zioneer.robotqcsystem.service.impl;

import com.zioneer.robotqcsystem.common.exception.BusinessException;
import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.common.result.ResultCode;
import com.zioneer.robotqcsystem.domain.dto.RobotCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.RobotQuery;
import com.zioneer.robotqcsystem.domain.dto.RobotUpdateDTO;
import com.zioneer.robotqcsystem.domain.entity.Robot;
import com.zioneer.robotqcsystem.domain.vo.RobotVO;
import com.zioneer.robotqcsystem.mapper.RobotGroupMapper;
import com.zioneer.robotqcsystem.mapper.RobotMapper;
import com.zioneer.robotqcsystem.mapper.RobotTypeMapper;
import com.zioneer.robotqcsystem.service.RobotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Robot service implementation.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RobotServiceImpl implements RobotService {

    private final RobotMapper robotMapper;
    private final RobotTypeMapper robotTypeMapper;
    private final RobotGroupMapper robotGroupMapper;

    @Override
    public RobotVO getById(Long id) {
        Robot robot = robotMapper.selectById(id);
        if (robot == null) {
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "Robot not found");
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
            throw new BusinessException("Robot code already exists: " + dto.getRobotCode());
        }
        validateTypeAndGroup(dto.getRobotTypeNo(), dto.getGroupNo());
        Robot robot = toEntity(dto);
        if (robot.getRegisteredAt() == null) {
            robot.setRegisteredAt(LocalDateTime.now());
        }
        if (robot.getOnlineStatus() == null) {
            robot.setOnlineStatus("offline");
        }
        if (robot.getExceptionStatus() == null) {
            robot.setExceptionStatus("normal");
        }
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
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "Robot not found");
        }
        validateTypeAndGroup(dto.getRobotTypeNo(), dto.getGroupNo());
        Robot robot = Robot.builder()
                .id(id)
                .robotCode(exist.getRobotCode())
                .robotName(dto.getRobotName())
                .serialNo(dto.getSerialNo())
                .ip(dto.getIp())
                .model(dto.getModel())
                .firmwareVersion(dto.getFirmwareVersion())
                .robotTypeNo(dto.getRobotTypeNo())
                .robotTypeName(dto.getRobotTypeName())
                .groupNo(dto.getGroupNo())
                .groupName(dto.getGroupName())
                .status(dto.getStatus())
                .onlineStatus(dto.getOnlineStatus())
                .battery(dto.getBattery())
                .mileageKm(dto.getMileageKm())
                .currentMapCode(dto.getCurrentMapCode())
                .currentMapName(dto.getCurrentMapName())
                .dispatchMode(dto.getDispatchMode())
                .controlStatus(dto.getControlStatus())
                .exceptionStatus(dto.getExceptionStatus())
                .videoUrl(dto.getVideoUrl())
                .location(dto.getLocation())
                .lastInspectionAt(dto.getLastInspectionAt())
                .registeredAt(dto.getRegisteredAt())
                .lastOnlineAt(dto.getLastOnlineAt())
                .lastHeartbeatAt(dto.getLastHeartbeatAt())
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
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "Robot not found");
        }
        robotMapper.deleteById(id);
        log.info("delete robot, id={}, robotCode={}", id, exist.getRobotCode());
    }

    private Robot toEntity(RobotCreateDTO dto) {
        return Robot.builder()
                .robotCode(dto.getRobotCode())
                .robotName(dto.getRobotName())
                .serialNo(dto.getSerialNo())
                .ip(dto.getIp())
                .model(dto.getModel())
                .firmwareVersion(dto.getFirmwareVersion())
                .robotTypeNo(dto.getRobotTypeNo())
                .robotTypeName(dto.getRobotTypeName())
                .groupNo(dto.getGroupNo())
                .groupName(dto.getGroupName())
                .status(dto.getStatus())
                .onlineStatus(dto.getOnlineStatus())
                .battery(dto.getBattery())
                .mileageKm(dto.getMileageKm())
                .currentMapCode(dto.getCurrentMapCode())
                .currentMapName(dto.getCurrentMapName())
                .dispatchMode(dto.getDispatchMode())
                .controlStatus(dto.getControlStatus())
                .exceptionStatus(dto.getExceptionStatus())
                .videoUrl(dto.getVideoUrl())
                .location(dto.getLocation())
                .lastInspectionAt(dto.getLastInspectionAt())
                .registeredAt(dto.getRegisteredAt())
                .lastOnlineAt(dto.getLastOnlineAt())
                .lastHeartbeatAt(dto.getLastHeartbeatAt())
                .build();
    }

    private void validateTypeAndGroup(String robotTypeNo, String groupNo) {
        if (StringUtils.hasText(robotTypeNo) && robotTypeMapper.selectByTypeNo(robotTypeNo) == null) {
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "Robot type not found: " + robotTypeNo);
        }
        if (StringUtils.hasText(groupNo) && robotGroupMapper.selectByGroupNo(groupNo) == null) {
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "Robot group not found: " + groupNo);
        }
    }

    private RobotVO toVO(Robot robot) {
        return RobotVO.builder()
                .id(robot.getId())
                .robotCode(robot.getRobotCode())
                .robotName(robot.getRobotName())
                .serialNo(robot.getSerialNo())
                .ip(robot.getIp())
                .model(robot.getModel())
                .firmwareVersion(robot.getFirmwareVersion())
                .robotTypeNo(robot.getRobotTypeNo())
                .robotTypeName(robot.getRobotTypeName())
                .groupNo(robot.getGroupNo())
                .groupName(robot.getGroupName())
                .status(robot.getStatus())
                .onlineStatus(robot.getOnlineStatus())
                .battery(robot.getBattery())
                .mileageKm(robot.getMileageKm())
                .currentMapCode(robot.getCurrentMapCode())
                .currentMapName(robot.getCurrentMapName())
                .dispatchMode(robot.getDispatchMode())
                .controlStatus(robot.getControlStatus())
                .exceptionStatus(robot.getExceptionStatus())
                .videoUrl(robot.getVideoUrl())
                .location(robot.getLocation())
                .lastInspectionAt(robot.getLastInspectionAt())
                .registeredAt(robot.getRegisteredAt())
                .lastOnlineAt(robot.getLastOnlineAt())
                .lastHeartbeatAt(robot.getLastHeartbeatAt())
                .build();
    }
}