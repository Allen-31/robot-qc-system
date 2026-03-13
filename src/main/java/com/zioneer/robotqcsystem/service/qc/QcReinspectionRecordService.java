package com.zioneer.robotqcsystem.service.qc;

import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.domain.dto.QcReinspectionRecordQuery;
import com.zioneer.robotqcsystem.domain.vo.QcReinspectionRecordVO;

/**
 * 复检记录（2.1.5，仅列表）
 */
public interface QcReinspectionRecordService {

    PageResult<QcReinspectionRecordVO> page(QcReinspectionRecordQuery query);
}
