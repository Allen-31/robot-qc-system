package com.zioneer.robotqcsystem.service.ops.impl;

import com.zioneer.robotqcsystem.common.exception.BusinessException;
import com.zioneer.robotqcsystem.common.id.SnowflakeIdGenerator;
import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.common.result.ResultCode;
import com.zioneer.robotqcsystem.domain.dto.OpsExceptionNotificationCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.OpsExceptionNotificationQuery;
import com.zioneer.robotqcsystem.domain.entity.OpsExceptionNotification;
import com.zioneer.robotqcsystem.domain.vo.OpsExceptionNotificationVO;
import com.zioneer.robotqcsystem.mapper.OpsExceptionNotificationMapper;
import com.zioneer.robotqcsystem.service.ops.OpsExceptionNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Exception notification service implementation.
 */
@Service
@RequiredArgsConstructor
public class OpsExceptionNotificationServiceImpl implements OpsExceptionNotificationService {

    private static final DateTimeFormatter CODE_DATE = DateTimeFormatter.BASIC_ISO_DATE;

    private final OpsExceptionNotificationMapper notificationMapper;
    private final SnowflakeIdGenerator idGenerator;

    @Override
    public PageResult<OpsExceptionNotificationVO> page(OpsExceptionNotificationQuery query) {
        String keyword = StringUtils.hasText(query.getKeyword()) ? query.getKeyword().trim() : null;
        String level = StringUtils.hasText(query.getLevel()) ? query.getLevel().trim() : null;
        String status = StringUtils.hasText(query.getStatus()) ? query.getStatus().trim() : null;

        long total = notificationMapper.countList(keyword, level, status);
        if (total == 0) {
            return PageResult.empty(query);
        }
        List<OpsExceptionNotification> list =
                notificationMapper.selectList(keyword, level, status, query.getOffset(), query.getPageSize());
        List<OpsExceptionNotificationVO> voList = list.stream().map(this::toVO).collect(Collectors.toList());
        return PageResult.of(voList, total, query.getPageNum(), query.getPageSize());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long create(OpsExceptionNotificationCreateDTO dto) {
        Long id = idGenerator.nextId();
        LocalDateTime now = LocalDateTime.now();
        String code = buildCode("EX", id);
        String status = StringUtils.hasText(dto.getStatus()) ? dto.getStatus().trim() : "pending";
        OpsExceptionNotification entity = new OpsExceptionNotification();
        entity.setId(id);
        entity.setCode(code);
        entity.setLevel(dto.getLevel());
        entity.setType(dto.getType());
        entity.setSourceSystem(dto.getSourceSystem());
        entity.setIssue(dto.getIssue());
        entity.setStatus(status);
        entity.setRelatedTask(dto.getRelatedTask());
        entity.setRobotCode(dto.getRobot());
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);
        notificationMapper.insert(entity);
        return id;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(Long id, String status) {
        OpsExceptionNotification exist = notificationMapper.selectById(id);
        if (exist == null) {
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "异常通知不存在");
        }
        notificationMapper.updateStatus(id, status, LocalDateTime.now());
    }

    private OpsExceptionNotificationVO toVO(OpsExceptionNotification entity) {
        return OpsExceptionNotificationVO.builder()
                .id(entity.getCode())
                .level(entity.getLevel())
                .type(entity.getType())
                .sourceSystem(entity.getSourceSystem())
                .issue(entity.getIssue())
                .status(entity.getStatus())
                .relatedTask(entity.getRelatedTask())
                .robot(entity.getRobotCode())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    private String buildCode(String prefix, Long id) {
        String date = LocalDate.now().format(CODE_DATE);
        long suffix = Math.abs(id % 1000);
        return prefix + "-" + date + "-" + String.format("%03d", suffix);
    }
}
