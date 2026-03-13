package com.zioneer.robotqcsystem.service.impl;

import com.zioneer.robotqcsystem.common.exception.BusinessException;
import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.common.result.ResultCode;
import com.zioneer.robotqcsystem.domain.dto.RobotGroupCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.RobotGroupQuery;
import com.zioneer.robotqcsystem.domain.dto.RobotGroupUpdateDTO;
import com.zioneer.robotqcsystem.domain.entity.RobotGroup;
import com.zioneer.robotqcsystem.domain.vo.RobotGroupVO;
import com.zioneer.robotqcsystem.mapper.RobotGroupMapper;
import com.zioneer.robotqcsystem.service.RobotGroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 机器人分组业务实现。
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RobotGroupServiceImpl implements RobotGroupService {

    private final RobotGroupMapper robotGroupMapper;

    @Override
    public PageResult<RobotGroupVO> page(RobotGroupQuery query) {
        long total = robotGroupMapper.countList(query.getKeyword());
        if (total == 0) {
            return PageResult.empty(query);
        }
        List<RobotGroup> list = robotGroupMapper.selectList(query.getKeyword(),
                query.getOffset(), query.getPageSize());
        List<RobotGroupVO> voList = list.stream().map(this::toVO).collect(Collectors.toList());
        return PageResult.of(voList, total, query.getPageNum(), query.getPageSize());
    }

    @Override
    public RobotGroupVO getById(Long id) {
        RobotGroup group = robotGroupMapper.selectById(id);
        if (group == null) {
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "机器人分组不存在");
        }
        return toVO(group);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long create(RobotGroupCreateDTO dto) {
        if (robotGroupMapper.selectByGroupNo(dto.getGroupNo()) != null) {
            log.warn("create robot group failed, groupNo already exists: {}", dto.getGroupNo());
            throw new BusinessException("分组编号已存在: " + dto.getGroupNo());
        }
        LocalDateTime now = LocalDateTime.now();
        RobotGroup group = RobotGroup.builder()
                .groupNo(dto.getGroupNo())
                .groupName(dto.getGroupName())
                .description(dto.getDescription())
                .createdAt(now)
                .updatedAt(now)
                .build();
        robotGroupMapper.insert(group);
        log.info("create robot group, groupNo={}, id={}", dto.getGroupNo(), group.getId());
        return group.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Long id, RobotGroupUpdateDTO dto) {
        RobotGroup exist = robotGroupMapper.selectById(id);
        if (exist == null) {
            log.warn("update robot group failed, not found: id={}", id);
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "机器人分组不存在");
        }
        RobotGroup group = RobotGroup.builder()
                .id(id)
                .groupName(dto.getGroupName())
                .description(dto.getDescription())
                .updatedAt(LocalDateTime.now())
                .build();
        robotGroupMapper.updateById(group);
        log.info("update robot group, id={}, groupNo={}", id, exist.getGroupNo());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        RobotGroup exist = robotGroupMapper.selectById(id);
        if (exist == null) {
            log.warn("delete robot group failed, not found: id={}", id);
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "机器人分组不存在");
        }
        robotGroupMapper.deleteById(id);
        log.info("delete robot group, id={}, groupNo={}", id, exist.getGroupNo());
    }

    private RobotGroupVO toVO(RobotGroup group) {
        return RobotGroupVO.builder()
                .id(group.getId())
                .groupNo(group.getGroupNo())
                .groupName(group.getGroupName())
                .description(group.getDescription())
                .createdAt(group.getCreatedAt())
                .updatedAt(group.getUpdatedAt())
                .build();
    }
}
