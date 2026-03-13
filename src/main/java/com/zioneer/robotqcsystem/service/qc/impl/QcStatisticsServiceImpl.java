package com.zioneer.robotqcsystem.service.qc.impl;

import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.domain.dto.QcStatisticsQuery;
import com.zioneer.robotqcsystem.domain.vo.QcStatisticsRowVO;
import com.zioneer.robotqcsystem.domain.vo.QcStatisticsSummaryVO;
import com.zioneer.robotqcsystem.service.qc.QcStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class QcStatisticsServiceImpl implements QcStatisticsService {

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public PageResult<QcStatisticsRowVO> page(QcStatisticsQuery query) {
        return PageResult.empty(query);
    }

    @Override
    public QcStatisticsSummaryVO summary(String workstationId, String dimension, String date) {
        String d = date != null ? date : LocalDate.now().format(DATE_FMT);
        String dim = dimension != null ? dimension : "day";
        String periodLabel = "day".equals(dim) ? "今日" : "本周";
        return QcStatisticsSummaryVO.builder()
                .workstationId(workstationId)
                .workstationName(workstationId != null ? workstationId : "全厂")
                .dimension(dim)
                .date(d)
                .periodLabel(periodLabel)
                .totalCount(0)
                .avgDurationMinutes(0.0)
                .failCount(0)
                .defectCount(0)
                .reinspectionCount(0)
                .build();
    }
}
