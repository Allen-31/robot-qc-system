package com.zioneer.robotqcsystem.service.impl;

import com.zioneer.robotqcsystem.common.exception.BusinessException;
import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.common.result.ResultCode;
import com.zioneer.robotqcsystem.domain.dto.RobotPartCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.RobotPartParamDTO;
import com.zioneer.robotqcsystem.domain.dto.RobotPartQuery;
import com.zioneer.robotqcsystem.domain.dto.RobotPartUpdateDTO;
import com.zioneer.robotqcsystem.domain.entity.RobotPart;
import com.zioneer.robotqcsystem.domain.entity.RobotPartParam;
import com.zioneer.robotqcsystem.domain.vo.RobotPartParamVO;
import com.zioneer.robotqcsystem.domain.vo.RobotPartVO;
import com.zioneer.robotqcsystem.mapper.RobotPartMapper;
import com.zioneer.robotqcsystem.service.RobotPartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 机器人零部件业务实现。
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RobotPartServiceImpl implements RobotPartService {

    private final RobotPartMapper robotPartMapper;

    @Override
    public PageResult<RobotPartVO> page(RobotPartQuery query) {
        long total = robotPartMapper.countList(query.getKeyword(), query.getType(), query.getStatus());
        if (total == 0) {
            return PageResult.empty(query);
        }
        List<RobotPart> list = robotPartMapper.selectList(query.getKeyword(), query.getType(), query.getStatus(),
                query.getOffset(), query.getPageSize());
        List<RobotPartVO> voList = list.stream().map(this::toVOWithoutParams).collect(Collectors.toList());
        return PageResult.of(voList, total, query.getPageNum(), query.getPageSize());
    }

    @Override
    public RobotPartVO getById(Long id) {
        RobotPart part = robotPartMapper.selectById(id);
        if (part == null) {
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "零部件不存在");
        }
        List<RobotPartParam> params = robotPartMapper.selectParamsByPartId(id);
        return toVO(part, params);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long create(RobotPartCreateDTO dto) {
        if (robotPartMapper.selectByPartNo(dto.getPartNo()) != null) {
            log.warn("create robot part failed, partNo already exists: {}", dto.getPartNo());
            throw new BusinessException("零部件编码已存在: " + dto.getPartNo());
        }
        LocalDateTime now = LocalDateTime.now();
        RobotPart part = RobotPart.builder()
                .partNo(dto.getPartNo())
                .name(dto.getName())
                .position(dto.getPosition())
                .type(dto.getType())
                .model(dto.getModel())
                .vendor(dto.getVendor())
                .supplier(dto.getSupplier())
                .lifecycle(dto.getLifecycle())
                .status(dto.getStatus() != null ? dto.getStatus() : "enabled")
                .remark(dto.getRemark())
                .createdAt(now)
                .updatedAt(now)
                .build();
        robotPartMapper.insert(part);
        saveParams(part.getId(), dto.getTechnicalParams(), now);
        log.info("create robot part, partNo={}, id={}", dto.getPartNo(), part.getId());
        return part.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Long id, RobotPartUpdateDTO dto) {
        RobotPart exist = robotPartMapper.selectById(id);
        if (exist == null) {
            log.warn("update robot part failed, not found: id={}", id);
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "零部件不存在");
        }
        LocalDateTime now = LocalDateTime.now();
        RobotPart part = RobotPart.builder()
                .id(id)
                .name(dto.getName())
                .position(dto.getPosition())
                .type(dto.getType())
                .model(dto.getModel())
                .vendor(dto.getVendor())
                .supplier(dto.getSupplier())
                .lifecycle(dto.getLifecycle())
                .status(dto.getStatus())
                .remark(dto.getRemark())
                .updatedAt(now)
                .build();
        robotPartMapper.updateById(part);
        robotPartMapper.deleteParamsByPartId(id);
        saveParams(id, dto.getTechnicalParams(), now);
        log.info("update robot part, id={}, partNo={}", id, exist.getPartNo());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        RobotPart exist = robotPartMapper.selectById(id);
        if (exist == null) {
            log.warn("delete robot part failed, not found: id={}", id);
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "零部件不存在");
        }
        robotPartMapper.deleteParamsByPartId(id);
        robotPartMapper.deleteById(id);
        log.info("delete robot part, id={}, partNo={}", id, exist.getPartNo());
    }

    private void saveParams(Long partId, List<RobotPartParamDTO> params, LocalDateTime now) {
        if (params == null || params.isEmpty()) {
            return;
        }
        for (int i = 0; i < params.size(); i++) {
            RobotPartParamDTO dto = params.get(i);
            RobotPartParam param = RobotPartParam.builder()
                    .partId(partId)
                    .name(dto.getName())
                    .value(dto.getValue())
                    .unit(dto.getUnit())
                    .range(dto.getRange())
                    .sortOrder(dto.getSortOrder() != null ? dto.getSortOrder() : i + 1)
                    .createdAt(now)
                    .updatedAt(now)
                    .build();
            robotPartMapper.insertParam(param);
        }
    }

    private RobotPartVO toVOWithoutParams(RobotPart part) {
        return RobotPartVO.builder()
                .id(part.getId())
                .partNo(part.getPartNo())
                .name(part.getName())
                .position(part.getPosition())
                .type(part.getType())
                .model(part.getModel())
                .vendor(part.getVendor())
                .supplier(part.getSupplier())
                .lifecycle(part.getLifecycle())
                .status(part.getStatus())
                .remark(part.getRemark())
                .createdAt(part.getCreatedAt())
                .updatedAt(part.getUpdatedAt())
                .technicalParams(Collections.emptyList())
                .build();
    }

    private RobotPartVO toVO(RobotPart part, List<RobotPartParam> params) {
        List<RobotPartParamVO> paramList = params == null ? Collections.emptyList() : params.stream()
                .map(p -> RobotPartParamVO.builder()
                        .name(p.getName())
                        .value(p.getValue())
                        .unit(p.getUnit())
                        .range(p.getRange())
                        .sortOrder(p.getSortOrder())
                        .build())
                .collect(Collectors.toList());
        return RobotPartVO.builder()
                .id(part.getId())
                .partNo(part.getPartNo())
                .name(part.getName())
                .position(part.getPosition())
                .type(part.getType())
                .model(part.getModel())
                .vendor(part.getVendor())
                .supplier(part.getSupplier())
                .lifecycle(part.getLifecycle())
                .status(part.getStatus())
                .remark(part.getRemark())
                .createdAt(part.getCreatedAt())
                .updatedAt(part.getUpdatedAt())
                .technicalParams(paramList)
                .build();
    }
}
