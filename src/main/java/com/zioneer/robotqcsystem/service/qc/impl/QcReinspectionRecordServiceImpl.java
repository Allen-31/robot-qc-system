package com.zioneer.robotqcsystem.service.qc.impl;

import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.domain.dto.QcReinspectionRecordQuery;
import com.zioneer.robotqcsystem.domain.entity.QcReinspectionRecord;
import com.zioneer.robotqcsystem.domain.entity.QcWorkOrder;
import com.zioneer.robotqcsystem.domain.vo.QcReinspectionRecordVO;
import com.zioneer.robotqcsystem.mapper.QcReinspectionRecordMapper;
import com.zioneer.robotqcsystem.mapper.QcWorkOrderMapper;
import com.zioneer.robotqcsystem.service.qc.QcReinspectionRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QcReinspectionRecordServiceImpl implements QcReinspectionRecordService {

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final QcReinspectionRecordMapper mapper;
    private final QcWorkOrderMapper workOrderMapper;

    @Override
    public PageResult<QcReinspectionRecordVO> page(QcReinspectionRecordQuery query) {
        long total = mapper.countList(query.getKeyword(), query.getStatus(), query.getReinspectionResult(),
                query.getDateFrom(), query.getDateTo());
        if (total == 0) return PageResult.empty(query);
        List<QcReinspectionRecord> list = mapper.selectList(query.getKeyword(), query.getStatus(), query.getReinspectionResult(),
                query.getDateFrom(), query.getDateTo(), query.getOffset(), query.getPageSize());
        List<QcReinspectionRecordVO> voList = list.stream().map(this::toVO).collect(Collectors.toList());
        return PageResult.of(voList, total, query.getPageNum(), query.getPageSize());
    }

    private QcReinspectionRecordVO toVO(QcReinspectionRecord e) {
        QcWorkOrder wo = workOrderMapper.selectById(e.getWorkOrderId());
        String workOrderNo = wo != null ? wo.getWorkOrderNo() : "-";
        String harnessCode = wo != null ? wo.getHarnessCode() : "-";
        String harnessType = wo != null ? wo.getHarnessType() : "-";
        String stationCode = wo != null ? wo.getStationCode() : "-";
        String qualityResult = wo != null ? wo.getQualityResult() : "-";
        return QcReinspectionRecordVO.builder()
                .id(e.getId())
                .reinspectionNo(e.getReinspectionNo())
                .workOrderNo(workOrderNo)
                .harnessCode(harnessCode)
                .harnessType(harnessType)
                .stationCode(stationCode)
                .qualityResult(qualityResult)
                .status(e.getStatus())
                .reinspectionResult(e.getReinspectionResult())
                .defectType(e.getDefectType())
                .reinspectionTime(e.getReinspectionTime() != null ? e.getReinspectionTime().format(FMT) : "-")
                .reviewer(e.getReviewer())
                .videoUrl(e.getVideoUrl())
                .imageUrl(e.getImageUrl())
                .build();
    }
}
