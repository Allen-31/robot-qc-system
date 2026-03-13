package com.zioneer.robotqcsystem.service.qc.impl;

import com.zioneer.robotqcsystem.common.exception.BusinessException;
import com.zioneer.robotqcsystem.common.id.SnowflakeIdGenerator;
import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.common.result.ResultCode;
import com.zioneer.robotqcsystem.domain.dto.QcStationConfigCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.QcStationConfigUpdateDTO;
import com.zioneer.robotqcsystem.domain.entity.QcStation;
import com.zioneer.robotqcsystem.domain.vo.*;
import com.zioneer.robotqcsystem.mapper.QcStationMapper;
import com.zioneer.robotqcsystem.service.qc.QcStationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class QcStationServiceImpl implements QcStationService {

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final QcStationMapper mapper;
    private final SnowflakeIdGenerator idGenerator;

    @Override
    public PageResult<QcStationPositionVO> listPositions(String workstationId) {
        Long wsId = parseLongOrNull(workstationId);
        List<QcStation> list = mapper.selectList(wsId);
        List<QcStationPositionVO> voList = list.stream().map(this::toPositionVO).collect(Collectors.toList());
        return PageResult.of(voList, (long) voList.size(), 1, voList.size());
    }

    @Override
    public List<QcStationConfigVO> listConfig(String workstationId) {
        Long wsId = parseLongOrNull(workstationId);
        List<QcStation> list = mapper.selectList(wsId);
        return list.stream().map(this::toConfigVO).collect(Collectors.toList());
    }

    @Override
    public List<QcStationRobotVO> listRobotsByStationId(String stationId) {
        return Collections.emptyList();
    }

    @Override
    public QcStationSummaryVO getStationSummary(String stationId, String dimension, String date) {
        String d = date != null ? date : LocalDate.now().format(DATE_FMT);
        String dim = dimension != null ? dimension : "day";
        String periodLabel = "day".equals(dim) ? "今日" : "本周";
        return QcStationSummaryVO.builder()
                .stationId(stationId)
                .stationName(stationId)
                .dimension(dim)
                .date(d)
                .periodLabel(periodLabel)
                .inspectionCount(0)
                .robotEfficiencyRate(0.0)
                .passRate(0.0)
                .rank(0)
                .defectCount(0)
                .reinspectionCount(0)
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public QcStationConfigVO createConfig(QcStationConfigCreateDTO dto) {
        Long wsId = parseLongOrNull(dto.getWorkstationId());
        if (wsId == null) throw new BusinessException("工作站ID无效");
        if (mapper.selectByWorkstationAndStationId(wsId, dto.getStationId()) != null)
            throw new BusinessException("该工作站下工位编码已存在");
        long id = idGenerator.nextId();
        LocalDateTime now = LocalDateTime.now();
        QcStation e = QcStation.builder()
                .id(id)
                .stationId(dto.getStationId())
                .workstationId(wsId)
                .name(dto.getStationId())
                .mapPoint(dto.getMapPoint())
                .status("running")
                .callBoxCode(dto.getCallBoxCode())
                .wireHarnessType(dto.getWireHarnessType())
                .detectionEnabled(dto.getDetectionEnabled() != null ? dto.getDetectionEnabled() : true)
                .enabled(dto.getEnabled() != null ? dto.getEnabled() : true)
                .createdAt(now)
                .updatedAt(now)
                .build();
        mapper.insert(e);
        return toConfigVO(e);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateConfig(Long id, QcStationConfigUpdateDTO dto) {
        QcStation e = mapper.selectById(id);
        if (e == null) throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "工位不存在");
        e.setMapPoint(dto.getMapPoint());
        e.setCallBoxCode(dto.getCallBoxCode());
        e.setWireHarnessType(dto.getWireHarnessType());
        e.setDetectionEnabled(dto.getDetectionEnabled());
        e.setEnabled(dto.getEnabled());
        e.setUpdatedAt(LocalDateTime.now());
        mapper.updateById(e);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteConfigById(Long id) {
        if (mapper.selectById(id) == null)
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "工位不存在");
        mapper.deleteById(id);
    }

    private static Long parseLongOrNull(String s) {
        if (s == null || s.isEmpty()) return null;
        try {
            return Long.parseLong(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private QcStationPositionVO toPositionVO(QcStation e) {
        return QcStationPositionVO.builder()
                .id(e.getId())
                .stationId(e.getStationId())
                .workstationId(String.valueOf(e.getWorkstationId()))
                .name(e.getName())
                .mapPoint(e.getMapPoint())
                .status(e.getStatus())
                .build();
    }

    private QcStationConfigVO toConfigVO(QcStation e) {
        return QcStationConfigVO.builder()
                .id(e.getId())
                .workstationId(String.valueOf(e.getWorkstationId()))
                .stationId(e.getStationId())
                .mapPoint(e.getMapPoint())
                .callBoxCode(e.getCallBoxCode())
                .wireHarnessType(e.getWireHarnessType())
                .detectionEnabled(e.getDetectionEnabled())
                .enabled(e.getEnabled())
                .build();
    }
}
