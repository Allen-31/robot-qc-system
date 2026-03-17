package com.zioneer.robotqcsystem.service.ops.impl;

import com.zioneer.robotqcsystem.common.exception.BusinessException;
import com.zioneer.robotqcsystem.common.result.ResultCode;
import com.zioneer.robotqcsystem.domain.dto.OpsRobotDispatchModeDTO;
import com.zioneer.robotqcsystem.domain.dto.OpsRobotLiftDTO;
import com.zioneer.robotqcsystem.domain.dto.OpsRobotMapSwitchDTO;
import com.zioneer.robotqcsystem.domain.dto.OpsRobotModeSwitchDTO;
import com.zioneer.robotqcsystem.domain.entity.Robot;
import com.zioneer.robotqcsystem.domain.vo.RobotVO;
import com.zioneer.robotqcsystem.mapper.RobotMapper;
import com.zioneer.robotqcsystem.service.deploy.robot.RobotService;
import com.zioneer.robotqcsystem.service.ops.OpsOperationLogService;
import com.zioneer.robotqcsystem.service.ops.OpsRobotService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Ops robot control service implementation.
 */
@Service
@RequiredArgsConstructor
public class OpsRobotServiceImpl implements OpsRobotService {

    private final RobotMapper robotMapper;
    private final RobotService robotService;
    private final OpsOperationLogService operationLogService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RobotVO switchDispatchMode(Long id, OpsRobotDispatchModeDTO dto) {
        Robot robot = getRequired(id);
        robotMapper.updateById(Robot.builder().id(id).dispatchMode(dto.getDispatchMode()).build());
        recordOperation(robot, "switch-dispatch-mode", "dispatchMode=" + dto.getDispatchMode());
        return robotService.getById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RobotVO pause(Long id) {
        Robot robot = getRequired(id);
        robotMapper.updateById(Robot.builder().id(id).controlStatus("paused").build());
        recordOperation(robot, "pause", null);
        return robotService.getById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RobotVO resume(Long id) {
        Robot robot = getRequired(id);
        robotMapper.updateById(Robot.builder().id(id).controlStatus("running").build());
        recordOperation(robot, "resume", null);
        return robotService.getById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RobotVO reset(Long id) {
        Robot robot = getRequired(id);
        recordOperation(robot, "reset", null);
        return robotService.getById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RobotVO startCharge(Long id) {
        Robot robot = getRequired(id);
        robotMapper.updateById(Robot.builder().id(id).isCharging(true).build());
        recordOperation(robot, "charge-start", null);
        return robotService.getById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RobotVO cancelCharge(Long id) {
        Robot robot = getRequired(id);
        robotMapper.updateById(Robot.builder().id(id).isCharging(false).build());
        recordOperation(robot, "charge-cancel", null);
        return robotService.getById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RobotVO startHoming(Long id) {
        Robot robot = getRequired(id);
        robotMapper.updateById(Robot.builder().id(id).isHoming(true).build());
        recordOperation(robot, "homing-start", null);
        return robotService.getById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RobotVO cancelHoming(Long id) {
        Robot robot = getRequired(id);
        robotMapper.updateById(Robot.builder().id(id).isHoming(false).build());
        recordOperation(robot, "homing-cancel", null);
        return robotService.getById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RobotVO switchMap(Long id, OpsRobotMapSwitchDTO dto) {
        Robot robot = getRequired(id);
        robotMapper.updateById(Robot.builder()
                .id(id)
                .currentMapCode(dto.getMapCode())
                .currentMapName(dto.getMapName())
                .build());
        recordOperation(robot, "switch-map", "mapCode=" + dto.getMapCode());
        return robotService.getById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RobotVO switchChassisMode(Long id, OpsRobotModeSwitchDTO dto) {
        Robot robot = getRequired(id);
        robotMapper.updateById(Robot.builder().id(id).chassisMode(dto.getMode()).build());
        recordOperation(robot, "switch-chassis-mode", "mode=" + dto.getMode());
        return robotService.getById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RobotVO switchArmMode(Long id, OpsRobotModeSwitchDTO dto) {
        Robot robot = getRequired(id);
        robotMapper.updateById(Robot.builder().id(id).armMode(dto.getMode()).build());
        recordOperation(robot, "switch-arm-mode", "mode=" + dto.getMode());
        return robotService.getById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RobotVO remoteControl(Long id) {
        Robot robot = getRequired(id);
        recordOperation(robot, "remote-control", null);
        return robotService.getById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RobotVO lift(Long id, OpsRobotLiftDTO dto) {
        Robot robot = getRequired(id);
        robotMapper.updateById(Robot.builder().id(id).isLifted(dto.getEnabled()).build());
        recordOperation(robot, "lift", "enabled=" + dto.getEnabled());
        return robotService.getById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RobotVO backToOrigin(Long id) {
        Robot robot = getRequired(id);
        recordOperation(robot, "arm-back-to-origin", null);
        return robotService.getById(id);
    }

    private Robot getRequired(Long id) {
        Robot robot = robotMapper.selectById(id);
        if (robot == null) {
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "Robot not found");
        }
        return robot;
    }

    private void recordOperation(Robot robot, String action, String detail) {
        try {
            String user = getCurrentUser();
            String ip = getClientIp();
            String requestInfo = "robotCode=" + robot.getRobotCode();
            if (StringUtils.hasText(detail)) {
                requestInfo = requestInfo + "; " + detail;
            }
            operationLogService.record(user, action, "success", null, 0, ip, requestInfo, "ok");
        } catch (Exception ignored) {
            // avoid breaking main flow
        }
    }

    private String getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return "anonymous";
        }
        String name = authentication.getName();
        return name != null ? name : "anonymous";
    }

    private String getClientIp() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return null;
        }
        return attributes.getRequest().getRemoteAddr();
    }
}
