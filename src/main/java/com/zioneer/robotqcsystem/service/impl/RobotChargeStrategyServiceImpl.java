package com.zioneer.robotqcsystem.service.impl;

import com.zioneer.robotqcsystem.common.exception.BusinessException;
import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.common.result.ResultCode;
import com.zioneer.robotqcsystem.domain.dto.RobotChargeStrategyCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.RobotChargeStrategyQuery;
import com.zioneer.robotqcsystem.domain.dto.RobotChargeStrategyUpdateDTO;
import com.zioneer.robotqcsystem.domain.entity.RobotChargeStrategy;
import com.zioneer.robotqcsystem.domain.vo.RobotChargeStrategyVO;
import com.zioneer.robotqcsystem.domain.vo.RobotChargeTriggerRuleVO;
import com.zioneer.robotqcsystem.mapper.RobotChargeStrategyMapper;
import com.zioneer.robotqcsystem.service.RobotChargeStrategyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Charge strategy service.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RobotChargeStrategyServiceImpl implements RobotChargeStrategyService {

    private final RobotChargeStrategyMapper robotChargeStrategyMapper;

    @Override
    public PageResult<RobotChargeStrategyVO> page(RobotChargeStrategyQuery query) {
        long total = robotChargeStrategyMapper.countList(query.getKeyword(), query.getStatus());
        if (total == 0) {
            return PageResult.empty(query);
        }
        List<RobotChargeStrategy> list = robotChargeStrategyMapper.selectList(query.getKeyword(), query.getStatus(),
                query.getOffset(), query.getPageSize());
        List<RobotChargeStrategyVO> voList = list.stream()
                .map(this::toVO)
                .collect(Collectors.toList());
        return PageResult.of(voList, total, query.getPageNum(), query.getPageSize());
    }

    @Override
    public RobotChargeStrategyVO getByCode(String code) {
        RobotChargeStrategy strategy = robotChargeStrategyMapper.selectByCode(code);
        if (strategy == null) {
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "Charge strategy not found");
        }
        return toVO(strategy);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(RobotChargeStrategyCreateDTO dto) {
        if (robotChargeStrategyMapper.selectByCode(dto.getCode()) != null) {
            log.warn("create charge strategy failed, code already exists: {}", dto.getCode());
            throw new BusinessException("Charge strategy code already exists: " + dto.getCode());
        }
        LocalDateTime now = LocalDateTime.now();
        RobotChargeStrategy strategy = RobotChargeStrategy.builder()
                .code(dto.getCode())
                .name(dto.getName())
                .status(dto.getStatus() != null ? dto.getStatus() : "enabled")
                .robotTypeNo(dto.getRobotTypeNo())
                .robotGroupNo(dto.getRobotGroupNo())
                .lowBatteryThreshold(dto.getTriggerRule().getLowBatteryThreshold())
                .minChargeMinutes(dto.getTriggerRule().getMinChargeMinutes())
                .chargeMethod(dto.getTriggerRule().getChargeMethod())
                .createdAt(now)
                .updatedAt(now)
                .build();
        robotChargeStrategyMapper.insert(strategy);
        log.info("create charge strategy, code={}", dto.getCode());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(String code, RobotChargeStrategyUpdateDTO dto) {
        RobotChargeStrategy exist = robotChargeStrategyMapper.selectByCode(code);
        if (exist == null) {
            log.warn("update charge strategy failed, not found: code={}", code);
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "Charge strategy not found");
        }
        RobotChargeStrategy strategy = RobotChargeStrategy.builder()
                .code(code)
                .name(dto.getName())
                .status(dto.getStatus())
                .robotTypeNo(dto.getRobotTypeNo())
                .robotGroupNo(dto.getRobotGroupNo())
                .lowBatteryThreshold(dto.getTriggerRule().getLowBatteryThreshold())
                .minChargeMinutes(dto.getTriggerRule().getMinChargeMinutes())
                .chargeMethod(dto.getTriggerRule().getChargeMethod())
                .updatedAt(LocalDateTime.now())
                .build();
        robotChargeStrategyMapper.updateByCode(strategy);
        log.info("update charge strategy, code={}", code);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByCode(String code) {
        RobotChargeStrategy exist = robotChargeStrategyMapper.selectByCode(code);
        if (exist == null) {
            log.warn("delete charge strategy failed, not found: code={}", code);
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "Charge strategy not found");
        }
        robotChargeStrategyMapper.deleteByCode(code);
        log.info("delete charge strategy, code={}", code);
    }

    private RobotChargeStrategyVO toVO(RobotChargeStrategy strategy) {
        return RobotChargeStrategyVO.builder()
                .code(strategy.getCode())
                .name(strategy.getName())
                .status(strategy.getStatus())
                .robotTypeNo(strategy.getRobotTypeNo())
                .robotGroupNo(strategy.getRobotGroupNo())
                .triggerRule(RobotChargeTriggerRuleVO.builder()
                        .lowBatteryThreshold(strategy.getLowBatteryThreshold())
                        .minChargeMinutes(strategy.getMinChargeMinutes())
                        .chargeMethod(strategy.getChargeMethod())
                        .build())
                .createdAt(strategy.getCreatedAt())
                .updatedAt(strategy.getUpdatedAt())
                .build();
    }
}