package com.zioneer.robotqcsystem.service.qc.impl;

import com.zioneer.robotqcsystem.common.exception.BusinessException;
import com.zioneer.robotqcsystem.common.id.SnowflakeIdGenerator;
import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.common.result.ResultCode;
import com.zioneer.robotqcsystem.domain.dto.QcWorkstationCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.QcWorkstationUpdateDTO;
import com.zioneer.robotqcsystem.domain.entity.QcWorkstation;
import com.zioneer.robotqcsystem.domain.vo.QcWorkstationConfigVO;
import com.zioneer.robotqcsystem.domain.vo.QcWorkstationVO;
import com.zioneer.robotqcsystem.mapper.QcWorkstationMapper;
import com.zioneer.robotqcsystem.service.qc.QcWorkstationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class QcWorkstationServiceImpl implements QcWorkstationService {

    private final QcWorkstationMapper mapper;
    private final SnowflakeIdGenerator idGenerator;

    @Override
    public PageResult<QcWorkstationVO> listBusiness(String keyword) {
        List<QcWorkstation> list = mapper.selectList(keyword, null);
        List<QcWorkstationVO> voList = list.stream().map(this::toBusinessVO).collect(Collectors.toList());
        return PageResult.of(voList, (long) voList.size(), 1, voList.size());
    }

    @Override
    public List<QcWorkstationConfigVO> listConfig(String keyword, String workshopCode) {
        List<QcWorkstation> list = mapper.selectList(keyword, workshopCode);
        return list.stream().map(this::toConfigVO).collect(Collectors.toList());
    }

    @Override
    public QcWorkstationVO getByIdBusiness(Long id) {
        QcWorkstation e = mapper.selectById(id);
        if (e == null) throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "工作站不存在");
        return toBusinessVO(e);
    }

    @Override
    public QcWorkstationConfigVO getByIdConfig(Long id) {
        QcWorkstation e = mapper.selectById(id);
        if (e == null) throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "工作站不存在");
        return toConfigVO(e);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public QcWorkstationConfigVO create(QcWorkstationCreateDTO dto) {
        long id = idGenerator.nextId();
        String code = "WS-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        LocalDateTime now = LocalDateTime.now();
        QcWorkstation e = QcWorkstation.builder()
                .id(id)
                .code(code)
                .name(dto.getName())
                .workshopCode(dto.getWorkshopCode())
                .status("running")
                .robotGroup(dto.getRobotGroup())
                .wireHarnessType(dto.getWireHarnessType())
                .enabled(dto.getEnabled() != null ? dto.getEnabled() : true)
                .createdAt(now)
                .updatedAt(now)
                .build();
        mapper.insert(e);
        log.info("create qc workstation, id={}, code={}", e.getId(), code);
        return toConfigVO(mapper.selectById(id));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Long id, QcWorkstationUpdateDTO dto) {
        QcWorkstation e = mapper.selectById(id);
        if (e == null) throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "工作站不存在");
        e.setName(dto.getName());
        e.setWorkshopCode(dto.getWorkshopCode());
        e.setWireHarnessType(dto.getWireHarnessType());
        e.setRobotGroup(dto.getRobotGroup());
        e.setEnabled(dto.getEnabled());
        e.setUpdatedAt(LocalDateTime.now());
        mapper.updateById(e);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        if (mapper.selectById(id) == null)
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "工作站不存在");
        mapper.deleteById(id);
    }

    private QcWorkstationVO toBusinessVO(QcWorkstation e) {
        return QcWorkstationVO.builder()
                .id(e.getId())
                .code(e.getCode())
                .name(e.getName())
                .workshopCode(e.getWorkshopCode())
                .status(e.getStatus())
                .robotGroup(e.getRobotGroup())
                .build();
    }

    private QcWorkstationConfigVO toConfigVO(QcWorkstation e) {
        return QcWorkstationConfigVO.builder()
                .id(e.getId())
                .name(e.getName())
                .workshopCode(e.getWorkshopCode())
                .wireHarnessType(e.getWireHarnessType())
                .robotGroup(e.getRobotGroup())
                .enabled(e.getEnabled())
                .build();
    }
}
