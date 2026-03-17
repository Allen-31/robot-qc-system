package com.zioneer.robotqcsystem.service.ops;

import com.zioneer.robotqcsystem.domain.dto.OpsRobotDispatchModeDTO;
import com.zioneer.robotqcsystem.domain.dto.OpsRobotLiftDTO;
import com.zioneer.robotqcsystem.domain.dto.OpsRobotMapSwitchDTO;
import com.zioneer.robotqcsystem.domain.dto.OpsRobotModeSwitchDTO;
import com.zioneer.robotqcsystem.domain.vo.RobotVO;

/**
 * Ops robot control service.
 */
public interface OpsRobotService {

    RobotVO switchDispatchMode(Long id, OpsRobotDispatchModeDTO dto);

    RobotVO pause(Long id);

    RobotVO resume(Long id);

    RobotVO reset(Long id);

    RobotVO startCharge(Long id);

    RobotVO cancelCharge(Long id);

    RobotVO startHoming(Long id);

    RobotVO cancelHoming(Long id);

    RobotVO switchMap(Long id, OpsRobotMapSwitchDTO dto);

    RobotVO switchChassisMode(Long id, OpsRobotModeSwitchDTO dto);

    RobotVO switchArmMode(Long id, OpsRobotModeSwitchDTO dto);

    RobotVO remoteControl(Long id);

    RobotVO lift(Long id, OpsRobotLiftDTO dto);

    RobotVO backToOrigin(Long id);
}
