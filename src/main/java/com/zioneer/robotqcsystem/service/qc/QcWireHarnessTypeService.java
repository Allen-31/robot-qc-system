package com.zioneer.robotqcsystem.service.qc;

import com.zioneer.robotqcsystem.domain.dto.QcWireHarnessTypeCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.QcWireHarnessTypeUpdateDTO;
import com.zioneer.robotqcsystem.domain.vo.QcWireHarnessTypeVO;

import java.util.List;

/**
 * 线束类型（2.2.3）
 */
public interface QcWireHarnessTypeService {

    List<QcWireHarnessTypeVO> list();

    QcWireHarnessTypeVO create(QcWireHarnessTypeCreateDTO dto);

    void update(Long id, QcWireHarnessTypeUpdateDTO dto);

    void deleteById(Long id);
}
