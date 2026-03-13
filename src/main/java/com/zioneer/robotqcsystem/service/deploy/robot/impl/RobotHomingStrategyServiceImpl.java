package com.zioneer.robotqcsystem.service.deploy.robot.impl;

import com.zioneer.robotqcsystem.common.exception.BusinessException;
import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.common.result.ResultCode;
import com.zioneer.robotqcsystem.domain.dto.RobotHomingStrategyCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.RobotHomingStrategyQuery;
import com.zioneer.robotqcsystem.domain.dto.RobotHomingStrategyUpdateDTO;
import com.zioneer.robotqcsystem.domain.entity.RobotHomingStrategy;
import com.zioneer.robotqcsystem.domain.vo.RobotHomingStrategyVO;
import com.zioneer.robotqcsystem.domain.vo.RobotHomingTriggerRuleVO;
import com.zioneer.robotqcsystem.mapper.RobotHomingStrategyMapper;
import com.zioneer.robotqcsystem.service.deploy.robot.RobotHomingStrategyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 归位策略业务实现。
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RobotHomingStrategyServiceImpl implements RobotHomingStrategyService {

    private final RobotHomingStrategyMapper robotHomingStrategyMapper;

    @Override
    public PageResult<RobotHomingStrategyVO> page(RobotHomingStrategyQuery query) {
        long total = robotHomingStrategyMapper.countList(query.getKeyword(), query.getStatus());
        if (total == 0) {
            return PageResult.empty(query);
        }
        List<RobotHomingStrategy> list = robotHomingStrategyMapper.selectList(query.getKeyword(), query.getStatus(),
                query.getOffset(), query.getPageSize());
        List<RobotHomingStrategyVO> voList = list.stream()
                .map(this::toVO)
                .collect(Collectors.toList());
        return PageResult.of(voList, total, query.getPageNum(), query.getPageSize());
    }

    @Override
    public RobotHomingStrategyVO getByCode(String code) {
        RobotHomingStrategy strategy = robotHomingStrategyMapper.selectByCode(code);
        if (strategy == null) {
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "归位策略不存在");
        }
        return toVO(strategy);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(RobotHomingStrategyCreateDTO dto) {
        if (robotHomingStrategyMapper.selectByCode(dto.getCode()) != null) {
            log.warn("create homing strategy failed, code already exists: {}", dto.getCode());
            throw new BusinessException("归位策略编码已存在: " + dto.getCode());
        }
        LocalDateTime now = LocalDateTime.now();
        RobotHomingStrategy strategy = RobotHomingStrategy.builder()
                .code(dto.getCode())
                .name(dto.getName())
                .status(dto.getStatus() != null ? dto.getStatus() : "enabled")
                .robotTypeNo(dto.getRobotTypeNo())
                .robotGroupNo(dto.getRobotGroupNo())
                .idleWaitSeconds(dto.getTriggerRule().getIdleWaitSeconds())
                .createdAt(now)
                .updatedAt(now)
                .build();
        robotHomingStrategyMapper.insert(strategy);
        log.info("create homing strategy, code={}", dto.getCode());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(String code, RobotHomingStrategyUpdateDTO dto) {
        RobotHomingStrategy exist = robotHomingStrategyMapper.selectByCode(code);
        if (exist == null) {
            log.warn("update homing strategy failed, not found: code={}", code);
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "归位策略不存在");
        }
        RobotHomingStrategy strategy = RobotHomingStrategy.builder()
                .code(code)
                .name(dto.getName())
                .status(dto.getStatus())
                .robotTypeNo(dto.getRobotTypeNo())
                .robotGroupNo(dto.getRobotGroupNo())
                .idleWaitSeconds(dto.getTriggerRule().getIdleWaitSeconds())
                .updatedAt(LocalDateTime.now())
                .build();
        robotHomingStrategyMapper.updateByCode(strategy);
        log.info("update homing strategy, code={}", code);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByCode(String code) {
        RobotHomingStrategy exist = robotHomingStrategyMapper.selectByCode(code);
        if (exist == null) {
            log.warn("delete homing strategy failed, not found: code={}", code);
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "归位策略不存在");
        }
        robotHomingStrategyMapper.deleteByCode(code);
        log.info("delete homing strategy, code={}", code);
    }

    private RobotHomingStrategyVO toVO(RobotHomingStrategy strategy) {
        return RobotHomingStrategyVO.builder()
                .code(strategy.getCode())
                .name(strategy.getName())
                .status(strategy.getStatus())
                .robotTypeNo(strategy.getRobotTypeNo())
                .robotGroupNo(strategy.getRobotGroupNo())
                .triggerRule(RobotHomingTriggerRuleVO.builder()
                        .idleWaitSeconds(strategy.getIdleWaitSeconds())
                        .build())
                .createdAt(strategy.getCreatedAt())
                .updatedAt(strategy.getUpdatedAt())
                .build();
    }
}
