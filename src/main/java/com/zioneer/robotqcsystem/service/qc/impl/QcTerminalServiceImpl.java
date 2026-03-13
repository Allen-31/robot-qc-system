package com.zioneer.robotqcsystem.service.qc.impl;

import com.zioneer.robotqcsystem.common.exception.BusinessException;
import com.zioneer.robotqcsystem.common.id.SnowflakeIdGenerator;
import com.zioneer.robotqcsystem.common.result.ResultCode;
import com.zioneer.robotqcsystem.domain.dto.QcTerminalCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.QcTerminalUpdateDTO;
import com.zioneer.robotqcsystem.domain.entity.QcTerminal;
import com.zioneer.robotqcsystem.domain.vo.QcTerminalVO;
import com.zioneer.robotqcsystem.mapper.QcTerminalMapper;
import com.zioneer.robotqcsystem.service.qc.QcTerminalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class QcTerminalServiceImpl implements QcTerminalService {

    private final QcTerminalMapper mapper;
    private final SnowflakeIdGenerator idGenerator;

    @Override
    public List<QcTerminalVO> list(String workstationId) {
        List<QcTerminal> list = mapper.selectList(workstationId);
        return list.stream().map(this::toVO).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public QcTerminalVO create(QcTerminalCreateDTO dto) {
        long id = idGenerator.nextId();
        LocalDateTime now = LocalDateTime.now();
        QcTerminal e = QcTerminal.builder()
                .id(id)
                .code(dto.getCode())
                .sn(dto.getSn())
                .terminalType(dto.getTerminalType())
                .terminalIp(dto.getTerminalIp())
                .workstationId(dto.getWorkstationId())
                .boundStationIds(dto.getBoundStationIds() != null ? dto.getBoundStationIds() : Collections.emptyList())
                .online(false)
                .currentUser(null)
                .createdAt(now)
                .updatedAt(now)
                .build();
        mapper.insert(e);
        return toVO(e);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Long id, QcTerminalUpdateDTO dto) {
        QcTerminal e = mapper.selectById(id);
        if (e == null) throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "终端不存在");
        if (dto.getCode() != null) e.setCode(dto.getCode());
        if (dto.getSn() != null) e.setSn(dto.getSn());
        if (dto.getTerminalType() != null) e.setTerminalType(dto.getTerminalType());
        if (dto.getTerminalIp() != null) e.setTerminalIp(dto.getTerminalIp());
        if (dto.getWorkstationId() != null) e.setWorkstationId(dto.getWorkstationId());
        if (dto.getBoundStationIds() != null) e.setBoundStationIds(dto.getBoundStationIds());
        e.setUpdatedAt(LocalDateTime.now());
        mapper.updateById(e);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        if (mapper.selectById(id) == null)
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "终端不存在");
        mapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateByCode(String code, QcTerminalUpdateDTO dto) {
        QcTerminal e = mapper.selectByCode(code);
        if (e == null) throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "终端不存在");
        if (dto.getCode() != null) e.setCode(dto.getCode());
        if (dto.getSn() != null) e.setSn(dto.getSn());
        if (dto.getTerminalType() != null) e.setTerminalType(dto.getTerminalType());
        if (dto.getTerminalIp() != null) e.setTerminalIp(dto.getTerminalIp());
        if (dto.getWorkstationId() != null) e.setWorkstationId(dto.getWorkstationId());
        if (dto.getBoundStationIds() != null) e.setBoundStationIds(dto.getBoundStationIds());
        e.setUpdatedAt(LocalDateTime.now());
        mapper.updateById(e);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByCode(String code) {
        QcTerminal e = mapper.selectByCode(code);
        if (e == null) throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "终端不存在");
        mapper.deleteById(e.getId());
    }

    private QcTerminalVO toVO(QcTerminal e) {
        return QcTerminalVO.builder()
                .id(e.getId())
                .code(e.getCode())
                .sn(e.getSn())
                .terminalType(e.getTerminalType())
                .terminalIp(e.getTerminalIp())
                .workstationId(e.getWorkstationId())
                .boundStationIds(e.getBoundStationIds() != null ? e.getBoundStationIds() : Collections.emptyList())
                .online(e.getOnline())
                .currentUser(e.getCurrentUser())
                .build();
    }
}
