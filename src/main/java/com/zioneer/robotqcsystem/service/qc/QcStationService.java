package com.zioneer.robotqcsystem.service.qc;

import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.domain.dto.QcStationConfigCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.QcStationConfigUpdateDTO;
import com.zioneer.robotqcsystem.domain.vo.QcStationConfigVO;
import com.zioneer.robotqcsystem.domain.vo.QcStationPositionVO;
import com.zioneer.robotqcsystem.domain.vo.QcStationRobotVO;
import com.zioneer.robotqcsystem.domain.vo.QcStationSummaryVO;

import java.util.List;

/**
 * 工位（业务 2.1.2 + 配置 2.2.2）
 */
public interface QcStationService {

    /** 工位列表（station-positions）：workstationId，返回 list+total */
    PageResult<QcStationPositionVO> listPositions(String workstationId);

    /** 工位配置列表 */
    List<QcStationConfigVO> listConfig(String workstationId);

    /** 当前质检台关联机器人（2.1.2.2），无数据可返回空列表 */
    List<QcStationRobotVO> listRobotsByStationId(String stationId);

    /** 当前质检台统计（2.1.2.3） */
    QcStationSummaryVO getStationSummary(String stationId, String dimension, String date);

    QcStationConfigVO createConfig(QcStationConfigCreateDTO dto);

    void updateConfig(Long id, QcStationConfigUpdateDTO dto);

    void deleteConfigById(Long id);
}
