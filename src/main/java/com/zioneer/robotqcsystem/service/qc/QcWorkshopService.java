package com.zioneer.robotqcsystem.service.qc;

import com.zioneer.robotqcsystem.domain.dto.QcWorkshopCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.QcWorkshopUpdateDTO;
import com.zioneer.robotqcsystem.domain.vo.QcWorkshopVO;

import java.util.List;

/**
 * 车间配置（2.2.5）
 */
public interface QcWorkshopService {

    List<QcWorkshopVO> list();

    QcWorkshopVO create(QcWorkshopCreateDTO dto);

    void update(String code, QcWorkshopUpdateDTO dto);

    void deleteByCode(String code);
}
