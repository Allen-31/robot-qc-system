package com.zioneer.robotqcsystem.service.qc.impl;

import com.zioneer.robotqcsystem.common.exception.BusinessException;
import com.zioneer.robotqcsystem.common.id.SnowflakeIdGenerator;
import com.zioneer.robotqcsystem.common.result.ResultCode;
import com.zioneer.robotqcsystem.domain.dto.QcWireHarnessTypeCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.QcWireHarnessTypeUpdateDTO;
import com.zioneer.robotqcsystem.domain.entity.QcWireHarnessType;
import com.zioneer.robotqcsystem.domain.vo.QcWireHarnessTypeVO;
import com.zioneer.robotqcsystem.mapper.QcWireHarnessTypeMapper;
import com.zioneer.robotqcsystem.service.qc.QcWireHarnessTypeService;
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
public class QcWireHarnessTypeServiceImpl implements QcWireHarnessTypeService {

    private final QcWireHarnessTypeMapper mapper;
    private final SnowflakeIdGenerator idGenerator;

    @Override
    public List<QcWireHarnessTypeVO> list() {
        return mapper.selectList().stream().map(this::toVO).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public QcWireHarnessTypeVO create(QcWireHarnessTypeCreateDTO dto) {
        long id = idGenerator.nextId();
        LocalDateTime now = LocalDateTime.now();
        QcWireHarnessType e = QcWireHarnessType.builder()
                .id(id)
                .name(dto.getName())
                .project(dto.getProject())
                .taskType(dto.getTaskType())
                .planarStructureFile(dto.getPlanarStructureFile())
                .threeDStructureFile(dto.getThreeDStructureFile())
                .createdAt(now)
                .updatedAt(now)
                .build();
        mapper.insert(e);
        return toVO(e);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Long id, QcWireHarnessTypeUpdateDTO dto) {
        QcWireHarnessType e = mapper.selectById(id);
        if (e == null) throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "线束类型不存在");
        e.setName(dto.getName());
        e.setProject(dto.getProject());
        e.setTaskType(dto.getTaskType());
        e.setPlanarStructureFile(dto.getPlanarStructureFile());
        e.setThreeDStructureFile(dto.getThreeDStructureFile());
        e.setUpdatedAt(LocalDateTime.now());
        mapper.updateById(e);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        if (mapper.selectById(id) == null)
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "线束类型不存在");
        mapper.deleteById(id);
    }

    private QcWireHarnessTypeVO toVO(QcWireHarnessType e) {
        return QcWireHarnessTypeVO.builder()
                .id(e.getId())
                .name(e.getName())
                .project(e.getProject())
                .taskType(e.getTaskType())
                .planarStructureFile(e.getPlanarStructureFile())
                .threeDStructureFile(e.getThreeDStructureFile())
                .build();
    }
}
