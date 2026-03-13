package com.zioneer.robotqcsystem.service.deploy.impl;

import com.zioneer.robotqcsystem.common.exception.BusinessException;
import com.zioneer.robotqcsystem.common.id.SnowflakeIdGenerator;
import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.common.result.ResultCode;
import com.zioneer.robotqcsystem.domain.dto.DeployDeviceCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.DeployDeviceUpdateDTO;
import com.zioneer.robotqcsystem.domain.entity.DeployDevice;
import com.zioneer.robotqcsystem.domain.vo.DeployDeviceVO;
import com.zioneer.robotqcsystem.mapper.DeployDeviceMapper;
import com.zioneer.robotqcsystem.service.deploy.DeviceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 设备管理实现（3.3.2）
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {

    private static final String DEFAULT_STATUS = "offline";

    private final DeployDeviceMapper deployDeviceMapper;
    private final SnowflakeIdGenerator idGenerator;

    @Override
    public PageResult<DeployDeviceVO> list(String mapCode) {
        List<DeployDevice> list = deployDeviceMapper.selectListByMapCode(mapCode);
        List<DeployDeviceVO> voList = list.stream().map(this::toVO).collect(Collectors.toList());
        long total = voList.size();
        return PageResult.of(voList, total, 1, (int) total);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DeployDeviceVO create(DeployDeviceCreateDTO dto) {
        if (deployDeviceMapper.selectByCode(dto.getCode()) != null) {
            log.warn("create device failed, code already exists: {}", dto.getCode());
            throw new BusinessException("设备编码已存在: " + dto.getCode());
        }
        long id = idGenerator.nextId();
        LocalDateTime now = LocalDateTime.now();
        DeployDevice device = DeployDevice.builder()
                .id(id)
                .code(dto.getCode())
                .name(dto.getName())
                .type(dto.getType())
                .deviceGroup(dto.getGroup())
                .mapCode(dto.getMapCode())
                .status(DEFAULT_STATUS)
                .ip(dto.getIp())
                .createdAt(now)
                .updatedAt(now)
                .build();
        deployDeviceMapper.insert(device);
        log.info("create device, code={}, id={}", device.getCode(), device.getId());
        DeployDevice created = deployDeviceMapper.selectById(id);
        return toVO(created);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Long id, DeployDeviceUpdateDTO dto) {
        DeployDevice exist = deployDeviceMapper.selectById(id);
        if (exist == null) {
            log.warn("update device failed, device not found: id={}", id);
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "设备不存在");
        }
        DeployDevice device = DeployDevice.builder()
                .id(id)
                .code(exist.getCode())
                .name(dto.getName())
                .type(dto.getType())
                .deviceGroup(dto.getGroup())
                .mapCode(dto.getMapCode())
                .status(dto.getStatus() != null ? dto.getStatus() : exist.getStatus())
                .ip(dto.getIp())
                .updatedAt(LocalDateTime.now())
                .build();
        deployDeviceMapper.updateById(device);
        log.info("update device, id={}, code={}", id, exist.getCode());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        DeployDevice exist = deployDeviceMapper.selectById(id);
        if (exist == null) {
            log.warn("delete device failed, device not found: id={}", id);
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "设备不存在");
        }
        deployDeviceMapper.deleteById(id);
        log.info("delete device, id={}, code={}", id, exist.getCode());
    }

    private DeployDeviceVO toVO(DeployDevice entity) {
        return DeployDeviceVO.builder()
                .id(entity.getId())
                .code(entity.getCode())
                .name(entity.getName())
                .type(entity.getType())
                .group(entity.getDeviceGroup())
                .mapCode(entity.getMapCode())
                .status(entity.getStatus())
                .ip(entity.getIp())
                .build();
    }
}
