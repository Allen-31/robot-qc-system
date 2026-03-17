package com.zioneer.robotqcsystem.service.ops.impl;

import com.zioneer.robotqcsystem.common.id.SnowflakeIdGenerator;
import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.domain.dto.OpsOperationLogQuery;
import com.zioneer.robotqcsystem.domain.entity.OpsOperationLog;
import com.zioneer.robotqcsystem.domain.vo.OpsOperationLogVO;
import com.zioneer.robotqcsystem.mapper.OpsOperationLogMapper;
import com.zioneer.robotqcsystem.service.ops.OpsOperationLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Operation log service implementation.
 */
@Service
@RequiredArgsConstructor
public class OpsOperationLogServiceImpl implements OpsOperationLogService {

    private static final DateTimeFormatter CODE_DATE = DateTimeFormatter.BASIC_ISO_DATE;

    private final OpsOperationLogMapper operationLogMapper;
    private final SnowflakeIdGenerator idGenerator;

    @Override
    public PageResult<OpsOperationLogVO> page(OpsOperationLogQuery query) {
        String keyword = StringUtils.hasText(query.getKeyword()) ? query.getKeyword().trim() : null;
        String result = StringUtils.hasText(query.getResult()) ? query.getResult().trim() : null;
        long total = operationLogMapper.countList(keyword, result);
        if (total == 0) {
            return PageResult.empty(query);
        }
        List<OpsOperationLog> list =
                operationLogMapper.selectList(keyword, result, query.getOffset(), query.getPageSize());
        List<OpsOperationLogVO> voList = list.stream().map(this::toVO).collect(Collectors.toList());
        return PageResult.of(voList, total, query.getPageNum(), query.getPageSize());
    }

    @Override
    public void record(String user,
                       String operationType,
                       String result,
                       String failReason,
                       Integer responseTime,
                       String ip,
                       String requestInfo,
                       String responseInfo) {
        Long id = idGenerator.nextId();
        OpsOperationLog log = new OpsOperationLog();
        log.setId(id);
        log.setLogCode(buildCode("OP", id));
        log.setUserCode(StringUtils.hasText(user) ? user : "anonymous");
        log.setOperationType(operationType);
        log.setResult(StringUtils.hasText(result) ? result : "success");
        log.setFailReason(failReason);
        log.setResponseTime(responseTime);
        log.setIp(ip);
        log.setRequestInfo(requestInfo);
        log.setResponseInfo(responseInfo);
        log.setCreatedAt(LocalDateTime.now());
        operationLogMapper.insert(log);
    }

    private OpsOperationLogVO toVO(OpsOperationLog log) {
        return OpsOperationLogVO.builder()
                .id(log.getLogCode())
                .user(log.getUserCode())
                .operationType(log.getOperationType())
                .result(log.getResult())
                .failReason(log.getFailReason())
                .responseTime(log.getResponseTime())
                .ip(log.getIp())
                .requestInfo(log.getRequestInfo())
                .responseInfo(log.getResponseInfo())
                .createdAt(log.getCreatedAt())
                .build();
    }

    private String buildCode(String prefix, Long id) {
        String date = LocalDate.now().format(CODE_DATE);
        long suffix = Math.abs(id % 1000);
        return prefix + "-" + date + "-" + String.format("%03d", suffix);
    }
}
