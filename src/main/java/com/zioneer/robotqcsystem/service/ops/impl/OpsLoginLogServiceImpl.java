package com.zioneer.robotqcsystem.service.ops.impl;

import com.zioneer.robotqcsystem.common.id.SnowflakeIdGenerator;
import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.domain.dto.OpsLoginLogQuery;
import com.zioneer.robotqcsystem.domain.entity.OpsLoginLog;
import com.zioneer.robotqcsystem.domain.vo.OpsLoginLogVO;
import com.zioneer.robotqcsystem.mapper.OpsLoginLogMapper;
import com.zioneer.robotqcsystem.service.ops.OpsLoginLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Login log service implementation.
 */
@Service
@RequiredArgsConstructor
public class OpsLoginLogServiceImpl implements OpsLoginLogService {

    private static final DateTimeFormatter CODE_DATE = DateTimeFormatter.BASIC_ISO_DATE;

    private final OpsLoginLogMapper loginLogMapper;
    private final SnowflakeIdGenerator idGenerator;

    @Override
    public PageResult<OpsLoginLogVO> page(OpsLoginLogQuery query) {
        String keyword = StringUtils.hasText(query.getKeyword()) ? query.getKeyword().trim() : null;
        String type = StringUtils.hasText(query.getType()) ? query.getType().trim() : null;
        long total = loginLogMapper.countList(keyword, type);
        if (total == 0) {
            return PageResult.empty(query);
        }
        List<OpsLoginLog> list = loginLogMapper.selectList(keyword, type, query.getOffset(), query.getPageSize());
        List<OpsLoginLogVO> voList = list.stream().map(this::toVO).collect(Collectors.toList());
        return PageResult.of(voList, total, query.getPageNum(), query.getPageSize());
    }

    @Override
    public void record(String user, String type, String ip) {
        Long id = idGenerator.nextId();
        LocalDateTime now = LocalDateTime.now();
        OpsLoginLog log = new OpsLoginLog();
        log.setId(id);
        log.setLogCode(buildCode("LG", id));
        log.setUserCode(StringUtils.hasText(user) ? user : "anonymous");
        log.setType(StringUtils.hasText(type) ? type : "login");
        log.setIp(ip);
        log.setCreatedAt(now);
        loginLogMapper.insert(log);
    }

    private OpsLoginLogVO toVO(OpsLoginLog log) {
        return OpsLoginLogVO.builder()
                .id(log.getLogCode())
                .user(log.getUserCode())
                .type(log.getType())
                .ip(log.getIp())
                .time(log.getCreatedAt())
                .build();
    }

    private String buildCode(String prefix, Long id) {
        String date = LocalDate.now().format(CODE_DATE);
        long suffix = Math.abs(id % 1000);
        return prefix + "-" + date + "-" + String.format("%03d", suffix);
    }
}
