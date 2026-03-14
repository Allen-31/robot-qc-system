package com.zioneer.robotqcsystem.service.operation.impl;

import com.zioneer.robotqcsystem.common.exception.BusinessException;
import com.zioneer.robotqcsystem.common.id.SnowflakeIdGenerator;
import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.common.result.ResultCode;
import com.zioneer.robotqcsystem.domain.dto.OperationServiceLogQuery;
import com.zioneer.robotqcsystem.domain.dto.OperationServiceQuery;
import com.zioneer.robotqcsystem.domain.entity.OperationService;
import com.zioneer.robotqcsystem.domain.entity.OperationServiceLog;
import com.zioneer.robotqcsystem.domain.vo.OperationServiceLogVO;
import com.zioneer.robotqcsystem.domain.vo.OperationServiceVO;
import com.zioneer.robotqcsystem.mapper.OperationServiceMapper;
import com.zioneer.robotqcsystem.service.operation.OperationServiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Operation service management implementation.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OperationServiceServiceImpl implements OperationServiceService {

    private static final int DEFAULT_LOGS_LIMIT = 5;

    private final OperationServiceMapper operationServiceMapper;
    private final SnowflakeIdGenerator idGenerator;

    @Override
    public PageResult<OperationServiceVO> page(OperationServiceQuery query) {
        String keyword = StringUtils.hasText(query.getKeyword()) ? query.getKeyword().trim() : null;
        String type = StringUtils.hasText(query.getType()) ? query.getType().trim() : null;
        String version = StringUtils.hasText(query.getVersion()) ? query.getVersion().trim() : null;
        String ip = StringUtils.hasText(query.getIp()) ? query.getIp().trim() : null;
        String status = StringUtils.hasText(query.getStatus()) ? query.getStatus().trim() : null;

        long total = operationServiceMapper.countList(keyword, type, version, ip, status);
        if (total == 0) {
            return PageResult.empty(query);
        }
        List<OperationService> list = operationServiceMapper.selectList(
                keyword, type, version, ip, status, query.getOffset(), query.getPageSize());
        List<Long> ids = list.stream().map(OperationService::getId).collect(Collectors.toList());
        Map<Long, List<OperationServiceLogVO>> logMap = fetchLogs(ids);
        List<OperationServiceVO> voList = list.stream()
                .map(e -> toVO(e, logMap.getOrDefault(e.getId(), Collections.emptyList())))
                .collect(Collectors.toList());
        return PageResult.of(voList, total, query.getPageNum(), query.getPageSize());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OperationServiceVO start(Long id) {
        OperationService service = getRequired(id);
        updateStatus(id, "running");
        appendLog(id, "Service started");
        return toVO(serviceWithStatus(service, "running"), latestLogs(id));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OperationServiceVO stop(Long id) {
        OperationService service = getRequired(id);
        updateStatus(id, "stopped");
        appendLog(id, "Service stopped");
        return toVO(serviceWithStatus(service, "stopped"), latestLogs(id));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OperationServiceVO restart(Long id) {
        OperationService service = getRequired(id);
        updateStatus(id, "running");
        appendLog(id, "Service restarted");
        return toVO(serviceWithStatus(service, "running"), latestLogs(id));
    }

    @Override
    public PageResult<OperationServiceLogVO> logPage(Long serviceId, OperationServiceLogQuery query) {
        OperationService service = operationServiceMapper.selectById(serviceId);
        if (service == null) {
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "Service not found");
        }
        String type = StringUtils.hasText(query.getType()) ? query.getType().trim() : null;
        long total = operationServiceMapper.countLogs(serviceId, type);
        if (total == 0) {
            return PageResult.empty(query);
        }
        List<OperationServiceLog> logs = operationServiceMapper.selectLogList(
                serviceId, type, query.getOffset(), query.getPageSize());
        List<OperationServiceLogVO> list = logs.stream()
                .map(this::toLogVO)
                .collect(Collectors.toList());
        return PageResult.of(list, total, query.getPageNum(), query.getPageSize());
    }

    private OperationService getRequired(Long id) {
        OperationService service = operationServiceMapper.selectById(id);
        if (service == null) {
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "Service not found");
        }
        return service;
    }

    private void updateStatus(Long id, String status) {
        int updated = operationServiceMapper.updateStatus(id, status, LocalDateTime.now());
        if (updated == 0) {
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "Service not found");
        }
    }

    private void appendLog(Long serviceId, String message) {
        LocalDateTime now = LocalDateTime.now();
        OperationServiceLog log = OperationServiceLog.builder()
                .id(idGenerator.nextId())
                .serviceId(serviceId)
                .logName("service-" + serviceId + "-" + now.toLocalDate())
                .type("runtime")
                .content(message)
                .createdAt(now)
                .updatedAt(now)
                .build();
        operationServiceMapper.insertLog(log);
    }

    private Map<Long, List<OperationServiceLogVO>> fetchLogs(List<Long> serviceIds) {
        if (serviceIds == null || serviceIds.isEmpty()) {
            return Collections.emptyMap();
        }
        List<OperationServiceLog> logs = operationServiceMapper.selectLogsByServiceIds(serviceIds);
        Map<Long, List<OperationServiceLogVO>> map = logs.stream()
                .collect(Collectors.groupingBy(
                        OperationServiceLog::getServiceId,
                        Collectors.mapping(this::toLogVO, Collectors.toList())));
        map.replaceAll((key, value) -> value.size() > DEFAULT_LOGS_LIMIT
                ? value.subList(0, DEFAULT_LOGS_LIMIT)
                : value);
        return map;
    }

    private List<OperationServiceLogVO> latestLogs(Long serviceId) {
        List<OperationServiceLog> logs = operationServiceMapper.selectLogList(serviceId, null, 0, DEFAULT_LOGS_LIMIT);
        if (logs == null || logs.isEmpty()) {
            return Collections.emptyList();
        }
        List<OperationServiceLogVO> list = new ArrayList<>();
        for (OperationServiceLog log : logs) {
            list.add(toLogVO(log));
        }
        return list;
    }

    private OperationService serviceWithStatus(OperationService service, String status) {
        return OperationService.builder()
                .id(service.getId())
                .name(service.getName())
                .type(service.getType())
                .version(service.getVersion())
                .ip(service.getIp())
                .status(status)
                .cpuUsage(service.getCpuUsage())
                .memoryUsage(service.getMemoryUsage())
                .runtime(service.getRuntime())
                .createdAt(service.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    private OperationServiceVO toVO(OperationService service, List<OperationServiceLogVO> logs) {
        return OperationServiceVO.builder()
                .id(service.getId())
                .name(service.getName())
                .type(service.getType())
                .version(service.getVersion())
                .ip(service.getIp())
                .status(service.getStatus())
                .cpuUsage(service.getCpuUsage())
                .memoryUsage(service.getMemoryUsage())
                .runtime(service.getRuntime())
                .logs(logs)
                .build();
    }

    private OperationServiceLogVO toLogVO(OperationServiceLog log) {
        return OperationServiceLogVO.builder()
                .id(log.getId())
                .logName(log.getLogName())
                .type(log.getType())
                .createdAt(log.getCreatedAt())
                .updatedAt(log.getUpdatedAt())
                .content(log.getContent())
                .build();
    }

}
