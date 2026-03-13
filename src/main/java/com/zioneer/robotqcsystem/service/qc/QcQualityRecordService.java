package com.zioneer.robotqcsystem.service.qc;

import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.domain.dto.QcQualityRecordQuery;
import com.zioneer.robotqcsystem.domain.vo.QcQualityRecordVO;

/**
 * 质检记录（2.1.4），与工单数据源一致，含 dispatchCode 等展示字段
 */
public interface QcQualityRecordService {

    PageResult<QcQualityRecordVO> page(QcQualityRecordQuery query);

    QcQualityRecordVO getById(Long id);
}
