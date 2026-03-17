package com.zioneer.robotqcsystem.service.ops.impl;

import com.zioneer.robotqcsystem.common.id.SnowflakeIdGenerator;
import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.domain.dto.OpsApiLogQuery;
import com.zioneer.robotqcsystem.domain.entity.OpsApiLog;
import com.zioneer.robotqcsystem.domain.vo.OpsApiLogVO;
import com.zioneer.robotqcsystem.mapper.OpsApiLogMapper;
import com.zioneer.robotqcsystem.service.ops.OpsApiLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * API log service implementation.
 */
@Service
@RequiredArgsConstructor
public class OpsApiLogServiceImpl implements OpsApiLogService {

    private static final DateTimeFormatter CODE_DATE = DateTimeFormatter.BASIC_ISO_DATE;

    private final OpsApiLogMapper apiLogMapper;
    private final SnowflakeIdGenerator idGenerator;

    @Override
    public PageResult<OpsApiLogVO> page(OpsApiLogQuery query) {
        String keyword = StringUtils.hasText(query.getKeyword()) ? query.getKeyword().trim() : null;
        String result = StringUtils.hasText(query.getCallResult()) ? query.getCallResult().trim() : null;
        long total = apiLogMapper.countList(keyword, result);
        if (total == 0) {
            return PageResult.empty(query);
        }
        List<OpsApiLog> list = apiLogMapper.selectList(keyword, result, query.getOffset(), query.getPageSize());
        List<OpsApiLogVO> voList = list.stream().map(this::toVO).collect(Collectors.toList());
        return PageResult.of(voList, total, query.getPageNum(), query.getPageSize());
    }

    @Override
    public void record(String apiName,
                       String result,
                       String failReason,
                       Integer responseTime,
                       String requestInfo,
                       String responseInfo) {
        Long id = idGenerator.nextId();
        OpsApiLog log = new OpsApiLog();
        log.setId(id);
        log.setLogCode(buildCode("API", id));
        log.setApiName(apiName);
        log.setCallResult(StringUtils.hasText(result) ? result : "success");
        log.setFailReason(failReason);
        log.setResponseTime(responseTime);
        log.setRequestInfo(requestInfo);
        log.setResponseInfo(responseInfo);
        log.setCreatedAt(LocalDateTime.now());
        apiLogMapper.insert(log);
    }

    private OpsApiLogVO toVO(OpsApiLog log) {
        return OpsApiLogVO.builder()
                .id(log.getLogCode())
                .apiName(log.getApiName())
                .callResult(log.getCallResult())
                .failReason(log.getFailReason())
                .responseTime(log.getResponseTime())
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
