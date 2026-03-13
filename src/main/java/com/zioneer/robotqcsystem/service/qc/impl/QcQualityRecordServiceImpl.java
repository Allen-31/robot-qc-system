package com.zioneer.robotqcsystem.service.qc.impl;

import com.zioneer.robotqcsystem.common.exception.BusinessException;
import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.common.result.ResultCode;
import com.zioneer.robotqcsystem.domain.dto.QcQualityRecordQuery;
import com.zioneer.robotqcsystem.domain.entity.QcWorkOrder;
import com.zioneer.robotqcsystem.domain.vo.QcQualityRecordVO;
import com.zioneer.robotqcsystem.mapper.QcWorkOrderMapper;
import com.zioneer.robotqcsystem.service.qc.QcQualityRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QcQualityRecordServiceImpl implements QcQualityRecordService {

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final QcWorkOrderMapper mapper;

    @Override
    public PageResult<QcQualityRecordVO> page(QcQualityRecordQuery query) {
        String qualityResult = Boolean.TRUE.equals(query.getOnlyNg()) ? "ng" : null;
        long total = mapper.countList(query.getKeyword(), null, qualityResult, null, null, null, null);
        if (total == 0) return PageResult.empty(query);
        List<QcWorkOrder> list = mapper.selectList(query.getKeyword(), null, qualityResult, null, null, null, null,
                null, query.getOffset(), query.getPageSize());
        List<QcQualityRecordVO> voList = list.stream().map(this::toVO).collect(Collectors.toList());
        return PageResult.of(voList, total, query.getPageNum(), query.getPageSize());
    }

    @Override
    public QcQualityRecordVO getById(Long id) {
        QcWorkOrder e = mapper.selectById(id);
        if (e == null) throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "质检记录不存在");
        return toVO(e);
    }

    private QcQualityRecordVO toVO(QcWorkOrder e) {
        String dispatchCode = (e.getTaskIds() != null && !e.getTaskIds().isEmpty()) ? e.getTaskIds().get(0) : "-";
        return QcQualityRecordVO.builder()
                .id(e.getId())
                .workOrderNo(e.getWorkOrderNo())
                .harnessCode(e.getHarnessCode())
                .harnessType(e.getHarnessType())
                .stationCode(e.getStationCode())
                .status(e.getStatus())
                .qualityResult(e.getQualityResult())
                .dispatchCode(dispatchCode)
                .movingDuration(e.getMovingDuration())
                .detectionDuration(e.getDetectionDuration())
                .createdAt(e.getCreatedAt() != null ? e.getCreatedAt().format(FMT) : "-")
                .startedAt(e.getStartedAt() != null ? e.getStartedAt().format(FMT) : null)
                .endedAt(e.getEndedAt() != null ? e.getEndedAt().format(FMT) : null)
                .build();
    }
}
