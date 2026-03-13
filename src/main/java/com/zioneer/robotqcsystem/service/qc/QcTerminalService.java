package com.zioneer.robotqcsystem.service.qc;

import com.zioneer.robotqcsystem.domain.dto.QcTerminalCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.QcTerminalUpdateDTO;
import com.zioneer.robotqcsystem.domain.vo.QcTerminalVO;

import java.util.List;

/**
 * 终端配置（2.2.4）
 */
public interface QcTerminalService {

    List<QcTerminalVO> list(String workstationId);

    QcTerminalVO create(QcTerminalCreateDTO dto);

    void update(Long id, QcTerminalUpdateDTO dto);

    void deleteById(Long id);

    void updateByCode(String code, QcTerminalUpdateDTO dto);

    void deleteByCode(String code);
}
