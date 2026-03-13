package com.zioneer.robotqcsystem.service.qc.impl;

import com.zioneer.robotqcsystem.common.exception.BusinessException;
import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.common.result.ResultCode;
import com.zioneer.robotqcsystem.domain.dto.QcWorkOrderQuery;
import com.zioneer.robotqcsystem.domain.dto.QcWorkOrderReviewDTO;
import com.zioneer.robotqcsystem.domain.dto.QcWorkOrderUpdateDTO;
import com.zioneer.robotqcsystem.domain.entity.QcWorkOrder;
import com.zioneer.robotqcsystem.domain.vo.QcWorkOrderVO;
import com.zioneer.robotqcsystem.mapper.QcWorkOrderMapper;
import com.zioneer.robotqcsystem.service.qc.QcWorkOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class QcWorkOrderServiceImpl implements QcWorkOrderService {

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final QcWorkOrderMapper mapper;

    @Override
    public PageResult<QcWorkOrderVO> page(QcWorkOrderQuery query) {
        List<String> statusIn = parseStatusIn(query.getStatus());
        long total = mapper.countList(query.getKeyword(), statusIn, query.getQualityResult(),
                query.getStationCode(), query.getHarnessType(), query.getDateFrom(), query.getDateTo());
        if (total == 0) return PageResult.empty(query);
        List<QcWorkOrder> list = mapper.selectList(query.getKeyword(), statusIn, query.getQualityResult(),
                query.getStationCode(), query.getHarnessType(), query.getDateFrom(), query.getDateTo(),
                query.getOrderBy(), query.getOffset(), query.getPageSize());
        List<QcWorkOrderVO> voList = list.stream().map(this::toVO).collect(Collectors.toList());
        return PageResult.of(voList, total, query.getPageNum(), query.getPageSize());
    }

    @Override
    public QcWorkOrderVO getById(Long id) {
        QcWorkOrder e = mapper.selectById(id);
        if (e == null) throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "工单不存在");
        return toVO(e);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Long id, QcWorkOrderUpdateDTO dto) {
        QcWorkOrder e = mapper.selectById(id);
        if (e == null) throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "工单不存在");
        if (dto.getHarnessType() != null) e.setHarnessType(dto.getHarnessType());
        if (dto.getDefectType() != null) e.setDefectType(dto.getDefectType());
        if (dto.getDefectDescription() != null) e.setDefectDescription(dto.getDefectDescription());
        if (dto.getQualityResult() != null) e.setQualityResult(dto.getQualityResult());
        mapper.updateById(e);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public QcWorkOrderVO review(Long id, QcWorkOrderReviewDTO dto) {
        QcWorkOrder e = mapper.selectById(id);
        if (e == null) throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "工单不存在");
        e.setQualityResult(dto.getQualityResult());
        e.setDefectType(dto.getDefectType());
        e.setDefectDescription(dto.getDefectDescription());
        e.setStatus("finished");
        mapper.updateById(e);
        return toVO(mapper.selectById(id));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public QcWorkOrderVO pause(Long id) {
        QcWorkOrder e = mapper.selectById(id);
        if (e == null) throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "工单不存在");
        if (!"running".equals(e.getStatus())) throw new BusinessException("仅执行中状态可暂停");
        e.setStatus("paused");
        mapper.updateById(e);
        return toVO(mapper.selectById(id));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public QcWorkOrderVO resume(Long id) {
        QcWorkOrder e = mapper.selectById(id);
        if (e == null) throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "工单不存在");
        if (!"paused".equals(e.getStatus())) throw new BusinessException("仅已暂停状态可恢复");
        e.setStatus("running");
        mapper.updateById(e);
        return toVO(mapper.selectById(id));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public QcWorkOrderVO cancel(Long id) {
        QcWorkOrder e = mapper.selectById(id);
        if (e == null) throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "工单不存在");
        e.setStatus("cancelled");
        mapper.updateById(e);
        return toVO(mapper.selectById(id));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        QcWorkOrder e = mapper.selectById(id);
        if (e == null) throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "工单不存在");
        mapper.deleteById(id);
    }

    private static List<String> parseStatusIn(String status) {
        if (status == null || status.isEmpty()) return null;
        return Arrays.stream(status.split(",")).map(String::trim).filter(s -> !s.isEmpty()).collect(Collectors.toList());
    }

    private QcWorkOrderVO toVO(QcWorkOrder e) {
        return QcWorkOrderVO.builder()
                .id(e.getId())
                .workOrderNo(e.getWorkOrderNo())
                .harnessCode(e.getHarnessCode())
                .movingDuration(e.getMovingDuration())
                .harnessType(e.getHarnessType())
                .stationCode(e.getStationCode())
                .status(e.getStatus())
                .qualityResult(e.getQualityResult())
                .taskIds(e.getTaskIds() != null ? e.getTaskIds() : List.of())
                .detectionDuration(e.getDetectionDuration())
                .createdAt(e.getCreatedAt() != null ? e.getCreatedAt().format(FMT) : "-")
                .startedAt(e.getStartedAt() != null ? e.getStartedAt().format(FMT) : "-")
                .endedAt(e.getEndedAt() != null ? e.getEndedAt().format(FMT) : "-")
                .defectType(e.getDefectType() != null ? e.getDefectType() : "-")
                .defectDescription(e.getDefectDescription() != null ? e.getDefectDescription() : "-")
                .videoUrl(e.getVideoUrl())
                .imageUrl(e.getImageUrl())
                .build();
    }
}
