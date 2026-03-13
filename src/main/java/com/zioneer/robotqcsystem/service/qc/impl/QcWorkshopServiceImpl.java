package com.zioneer.robotqcsystem.service.qc.impl;

import com.zioneer.robotqcsystem.common.exception.BusinessException;
import com.zioneer.robotqcsystem.common.result.ResultCode;
import com.zioneer.robotqcsystem.domain.dto.QcWorkshopCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.QcWorkshopUpdateDTO;
import com.zioneer.robotqcsystem.domain.entity.QcWorkshop;
import com.zioneer.robotqcsystem.domain.vo.QcWorkshopVO;
import com.zioneer.robotqcsystem.mapper.QcWorkshopMapper;
import com.zioneer.robotqcsystem.service.qc.QcWorkshopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class QcWorkshopServiceImpl implements QcWorkshopService {

    private final QcWorkshopMapper mapper;

    @Override
    public List<QcWorkshopVO> list() {
        return mapper.selectList().stream().map(this::toVO).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public QcWorkshopVO create(QcWorkshopCreateDTO dto) {
        if (mapper.selectByCode(dto.getCode()) != null)
            throw new BusinessException("车间编码已存在: " + dto.getCode());
        LocalDateTime now = LocalDateTime.now();
        QcWorkshop e = QcWorkshop.builder()
                .code(dto.getCode())
                .name(dto.getName())
                .location(dto.getLocation())
                .enabled(dto.getEnabled() != null ? dto.getEnabled() : true)
                .createdAt(now)
                .updatedAt(now)
                .build();
        mapper.insert(e);
        return toVO(e);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(String code, QcWorkshopUpdateDTO dto) {
        QcWorkshop e = mapper.selectByCode(code);
        if (e == null) throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "车间不存在");
        e.setName(dto.getName());
        e.setLocation(dto.getLocation());
        e.setEnabled(dto.getEnabled());
        e.setUpdatedAt(LocalDateTime.now());
        mapper.updateByCode(e);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByCode(String code) {
        if (mapper.selectByCode(code) == null)
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "车间不存在");
        mapper.deleteByCode(code);
    }

    private QcWorkshopVO toVO(QcWorkshop e) {
        return QcWorkshopVO.builder()
                .code(e.getCode())
                .name(e.getName())
                .location(e.getLocation())
                .enabled(e.getEnabled())
                .build();
    }
}
