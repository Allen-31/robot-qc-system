package com.zioneer.robotqcsystem.service.operation.impl;

import com.zioneer.robotqcsystem.common.exception.BusinessException;
import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.common.result.ResultCode;
import com.zioneer.robotqcsystem.domain.dto.OperationTaskQuery;
import com.zioneer.robotqcsystem.domain.entity.OperationTask;
import com.zioneer.robotqcsystem.domain.vo.OperationTaskActionResultVO;
import com.zioneer.robotqcsystem.domain.vo.OperationTaskVO;
import com.zioneer.robotqcsystem.mapper.OperationTaskMapper;
import com.zioneer.robotqcsystem.service.operation.OperationTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 任务流服务实现。
 */
@Service
@RequiredArgsConstructor
public class OperationTaskServiceImpl implements OperationTaskService {

    private final OperationTaskMapper operationTaskMapper;

    @Override
    public PageResult<OperationTaskVO> page(OperationTaskQuery query) {
        String keyword = StringUtils.hasText(query.getKeyword()) ? query.getKeyword().trim() : null;
        String status = StringUtils.hasText(query.getStatus()) ? query.getStatus().trim() : null;
        String robot = StringUtils.hasText(query.getRobot()) ? query.getRobot().trim() : null;

        long total = operationTaskMapper.countList(keyword, status, robot);
        if (total == 0) {
            return PageResult.empty(query);
        }
        List<OperationTask> tasks = operationTaskMapper.selectList(keyword, status, robot, query.getOffset(), query.getPageSize());
        List<OperationTaskVO> voList = tasks.stream().map(this::toVO).collect(Collectors.toList());
        return PageResult.of(voList, total, query.getPageNum(), query.getPageSize());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OperationTaskActionResultVO pause(Long id) {
        OperationTask task = getRequired(id);
        if (!"running".equals(task.getStatus())) {
            throw new BusinessException("仅运行中的调度可暂停");
        }
        updateStatus(id, "paused", task.getStatus(), null);
        return OperationTaskActionResultVO.builder().status("paused").build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OperationTaskActionResultVO resume(Long id) {
        OperationTask task = getRequired(id);
        if (!"paused".equals(task.getStatus())) {
            throw new BusinessException("仅暂停中的调度可恢复");
        }
        updateStatus(id, "running", task.getStatus(), null);
        return OperationTaskActionResultVO.builder().status("running").build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OperationTaskActionResultVO cancel(Long id) {
        OperationTask task = getRequired(id);
        if ("cancelled".equals(task.getStatus())) {
            throw new BusinessException("调度已取消");
        }
        if ("completed".equals(task.getStatus())) {
            throw new BusinessException("已完成调度不能取消");
        }
        updateStatus(id, "cancelled", task.getStatus(), LocalDateTime.now());
        return OperationTaskActionResultVO.builder().status("cancelled").build();
    }

    private OperationTask getRequired(Long id) {
        OperationTask task = operationTaskMapper.selectById(id);
        if (task == null) {
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "调度不存在");
        }
        return task;
    }

    private void updateStatus(Long id, String status, String expectedStatus, LocalDateTime endedAt) {
        int updated = operationTaskMapper.updateStatus(id, status, expectedStatus, LocalDateTime.now(), endedAt);
        if (updated == 0) {
            throw new BusinessException("状态已变更，请刷新后重试");
        }
    }

    private OperationTaskVO toVO(OperationTask task) {
        return OperationTaskVO.builder()
                .id(task.getId())
                .code(task.getCode())
                .externalCode(task.getExternalCode())
                .status(task.getStatus())
                .robot(task.getRobotCode())
                .priority(task.getPriority())
                .createdAt(task.getCreatedAt())
                .endedAt(task.getEndedAt())
                .description(task.getDescription())
                .build();
    }
}