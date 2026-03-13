package com.zioneer.robotqcsystem.service.deploy.robot.impl;

import com.zioneer.robotqcsystem.common.exception.BusinessException;
import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.common.result.ResultCode;
import com.zioneer.robotqcsystem.domain.dto.RobotTypeCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.RobotTypePointDTO;
import com.zioneer.robotqcsystem.domain.dto.RobotTypeQuery;
import com.zioneer.robotqcsystem.domain.dto.RobotTypeUpdateDTO;
import com.zioneer.robotqcsystem.domain.entity.RobotType;
import com.zioneer.robotqcsystem.domain.entity.RobotTypePoint;
import com.zioneer.robotqcsystem.domain.vo.RobotTypePointVO;
import com.zioneer.robotqcsystem.domain.vo.RobotTypeVO;
import com.zioneer.robotqcsystem.mapper.RobotTypeMapper;
import com.zioneer.robotqcsystem.service.deploy.robot.RobotTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 机器人类型业务实现。
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RobotTypeServiceImpl implements RobotTypeService {

    private final RobotTypeMapper robotTypeMapper;

    @Override
    public PageResult<RobotTypeVO> page(RobotTypeQuery query) {
        long total = robotTypeMapper.countList(query.getKeyword(), query.getStatus());
        if (total == 0) {
            return PageResult.empty(query);
        }
        List<RobotType> list = robotTypeMapper.selectList(query.getKeyword(), query.getStatus(),
                query.getOffset(), query.getPageSize());
        List<RobotTypeVO> voList = list.stream()
                .map(this::toVOWithoutPoints)
                .collect(Collectors.toList());
        return PageResult.of(voList, total, query.getPageNum(), query.getPageSize());
    }

    @Override
    public RobotTypeVO getById(Long id) {
        RobotType robotType = robotTypeMapper.selectById(id);
        if (robotType == null) {
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "机器人类型不存在");
        }
        List<RobotTypePoint> points = robotTypeMapper.selectPointsByTypeId(id);
        return toVO(robotType, points);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long create(RobotTypeCreateDTO dto) {
        if (robotTypeMapper.selectByTypeNo(dto.getTypeNo()) != null) {
            log.warn("create robot type failed, typeNo already exists: {}", dto.getTypeNo());
            throw new BusinessException("机器人类型编号已存在: " + dto.getTypeNo());
        }
        LocalDateTime now = LocalDateTime.now();
        int partsCount = dto.getPoints() == null ? 0 : dto.getPoints().size();
        RobotType robotType = RobotType.builder()
                .typeNo(dto.getTypeNo())
                .typeName(dto.getTypeName())
                .image2d(dto.getImage2d())
                .image2dData(dto.getImage2dData())
                .partsCount(partsCount)
                .status(dto.getStatus() != null ? dto.getStatus() : "enabled")
                .createdAt(now)
                .updatedAt(now)
                .build();
        robotTypeMapper.insert(robotType);
        savePoints(robotType.getId(), dto.getPoints(), now);
        log.info("create robot type, typeNo={}, id={}", dto.getTypeNo(), robotType.getId());
        return robotType.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Long id, RobotTypeUpdateDTO dto) {
        RobotType exist = robotTypeMapper.selectById(id);
        if (exist == null) {
            log.warn("update robot type failed, not found: id={}", id);
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "机器人类型不存在");
        }
        LocalDateTime now = LocalDateTime.now();
        int partsCount = dto.getPoints() == null ? 0 : dto.getPoints().size();
        RobotType robotType = RobotType.builder()
                .id(id)
                .typeName(dto.getTypeName())
                .image2d(dto.getImage2d())
                .image2dData(dto.getImage2dData())
                .partsCount(partsCount)
                .status(dto.getStatus())
                .updatedAt(now)
                .build();
        robotTypeMapper.updateById(robotType);
        robotTypeMapper.deletePointsByTypeId(id);
        savePoints(id, dto.getPoints(), now);
        log.info("update robot type, id={}, typeNo={}", id, exist.getTypeNo());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        RobotType exist = robotTypeMapper.selectById(id);
        if (exist == null) {
            log.warn("delete robot type failed, not found: id={}", id);
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "机器人类型不存在");
        }
        robotTypeMapper.deletePointsByTypeId(id);
        robotTypeMapper.deleteById(id);
        log.info("delete robot type, id={}, typeNo={}", id, exist.getTypeNo());
    }

    private void savePoints(Long robotTypeId, List<RobotTypePointDTO> points, LocalDateTime now) {
        if (points == null || points.isEmpty()) {
            return;
        }
        for (int i = 0; i < points.size(); i++) {
            RobotTypePointDTO dto = points.get(i);
            RobotTypePoint point = RobotTypePoint.builder()
                    .robotTypeId(robotTypeId)
                    .partName(dto.getPartName())
                    .partPosition(dto.getPartPosition())
                    .x(dto.getX())
                    .y(dto.getY())
                    .rotation(dto.getRotation())
                    .remark(dto.getRemark())
                    .sortOrder(dto.getSortOrder() != null ? dto.getSortOrder() : i + 1)
                    .createdAt(now)
                    .updatedAt(now)
                    .build();
            robotTypeMapper.insertPoint(point);
        }
    }

    private RobotTypeVO toVOWithoutPoints(RobotType robotType) {
        return RobotTypeVO.builder()
                .id(robotType.getId())
                .typeNo(robotType.getTypeNo())
                .typeName(robotType.getTypeName())
                .image2d(robotType.getImage2d())
                .image2dData(robotType.getImage2dData())
                .partsCount(robotType.getPartsCount())
                .status(robotType.getStatus())
                .createdAt(robotType.getCreatedAt())
                .updatedAt(robotType.getUpdatedAt())
                .points(Collections.emptyList())
                .build();
    }

    private RobotTypeVO toVO(RobotType robotType, List<RobotTypePoint> points) {
        List<RobotTypePointVO> pointList = points == null ? Collections.emptyList() : points.stream()
                .map(p -> RobotTypePointVO.builder()
                        .partName(p.getPartName())
                        .partPosition(p.getPartPosition())
                        .x(p.getX())
                        .y(p.getY())
                        .rotation(p.getRotation())
                        .remark(p.getRemark())
                        .sortOrder(p.getSortOrder())
                        .build())
                .collect(Collectors.toList());
        return RobotTypeVO.builder()
                .id(robotType.getId())
                .typeNo(robotType.getTypeNo())
                .typeName(robotType.getTypeName())
                .image2d(robotType.getImage2d())
                .image2dData(robotType.getImage2dData())
                .partsCount(robotType.getPartsCount())
                .status(robotType.getStatus())
                .createdAt(robotType.getCreatedAt())
                .updatedAt(robotType.getUpdatedAt())
                .points(pointList)
                .build();
    }
}
