package com.zioneer.robotqcsystem.service.qc;

import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.domain.dto.QcStatisticsQuery;
import com.zioneer.robotqcsystem.domain.vo.QcStatisticsRowVO;
import com.zioneer.robotqcsystem.domain.vo.QcStatisticsSummaryVO;

/**
 * 质检统计（2.3.1）
 */
public interface QcStatisticsService {

    PageResult<QcStatisticsRowVO> page(QcStatisticsQuery query);

    /** 质检区指标汇总（按日/周） */
    QcStatisticsSummaryVO summary(String workstationId, String dimension, String date);
}
