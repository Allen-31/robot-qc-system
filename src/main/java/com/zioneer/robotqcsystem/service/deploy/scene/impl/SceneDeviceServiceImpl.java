package com.zioneer.robotqcsystem.service.deploy.scene.impl;

import com.zioneer.robotqcsystem.common.exception.BusinessException;
import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.common.result.ResultCode;
import com.zioneer.robotqcsystem.domain.dto.SceneDeviceCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.SceneDeviceQuery;
import com.zioneer.robotqcsystem.domain.dto.SceneDeviceUpdateDTO;
import com.zioneer.robotqcsystem.domain.entity.SceneDevice;
import com.zioneer.robotqcsystem.domain.vo.SceneDeviceVO;
import com.zioneer.robotqcsystem.mapper.SceneDeviceMapper;
import com.zioneer.robotqcsystem.service.deploy.scene.SceneDeviceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 场景设备业务实现。
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SceneDeviceServiceImpl implements SceneDeviceService {

    private final SceneDeviceMapper sceneDeviceMapper;

    @Override
    public PageResult<SceneDeviceVO> page(SceneDeviceQuery query) {
        Integer isAbnormal = query.getIsAbnormal() == null ? null : (query.getIsAbnormal() ? 1 : 0);
        long total = sceneDeviceMapper.countList(query.getKeyword(), query.getOnlineStatus(), isAbnormal, query.getMapCode());
        if (total == 0) {
            return PageResult.empty(query);
        }
        List<SceneDevice> list = sceneDeviceMapper.selectList(query.getKeyword(), query.getOnlineStatus(),
                isAbnormal, query.getMapCode(), query.getOffset(), query.getPageSize());
        List<SceneDeviceVO> voList = list.stream().map(this::toVO).collect(Collectors.toList());
        return PageResult.of(voList, total, query.getPageNum(), query.getPageSize());
    }

    @Override
    public SceneDeviceVO getByCode(String code) {
        SceneDevice device = sceneDeviceMapper.selectByCode(code);
        if (device == null) {
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "场景设备不存在");
        }
        return toVO(device);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(SceneDeviceCreateDTO dto) {
        if (sceneDeviceMapper.selectByCode(dto.getCode()) != null) {
            log.warn("create scene device failed, code already exists: {}", dto.getCode());
            throw new BusinessException("设备编码已存在: " + dto.getCode());
        }
        LocalDateTime now = LocalDateTime.now();
        SceneDevice device = SceneDevice.builder()
                .code(dto.getCode())
                .name(dto.getName())
                .type(dto.getType())
                .ip(dto.getIp())
                .onlineStatus(dto.getOnlineStatus())
                .isAbnormal(dto.getIsAbnormal() != null && dto.getIsAbnormal() ? 1 : 0)
                .exceptionDetail(dto.getExceptionDetail())
                .mapCode(dto.getMapCode())
                .createdAt(now)
                .updatedAt(now)
                .build();
        sceneDeviceMapper.insert(device);
        log.info("create scene device, code={}", dto.getCode());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(String code, SceneDeviceUpdateDTO dto) {
        SceneDevice exist = sceneDeviceMapper.selectByCode(code);
        if (exist == null) {
            log.warn("update scene device failed, not found: code={}", code);
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "场景设备不存在");
        }
        SceneDevice device = SceneDevice.builder()
                .code(code)
                .name(dto.getName())
                .type(dto.getType())
                .ip(dto.getIp())
                .onlineStatus(dto.getOnlineStatus())
                .isAbnormal(dto.getIsAbnormal() != null && dto.getIsAbnormal() ? 1 : 0)
                .exceptionDetail(dto.getExceptionDetail())
                .mapCode(dto.getMapCode())
                .updatedAt(LocalDateTime.now())
                .build();
        sceneDeviceMapper.updateByCode(device);
        log.info("update scene device, code={}", code);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByCode(String code) {
        if (sceneDeviceMapper.selectByCode(code) == null) {
            log.warn("delete scene device failed, not found: code={}", code);
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "场景设备不存在");
        }
        sceneDeviceMapper.deleteByCode(code);
        log.info("delete scene device, code={}", code);
    }

    private SceneDeviceVO toVO(SceneDevice device) {
        return SceneDeviceVO.builder()
                .id(device.getId())
                .code(device.getCode())
                .name(device.getName())
                .type(device.getType())
                .ip(device.getIp())
                .onlineStatus(device.getOnlineStatus())
                .isAbnormal(device.getIsAbnormal() != null && device.getIsAbnormal() == 1)
                .exceptionDetail(device.getExceptionDetail())
                .mapCode(device.getMapCode())
                .lastOnlineAt(device.getLastOnlineAt())
                .lastHeartbeatAt(device.getLastHeartbeatAt())
                .createdAt(device.getCreatedAt())
                .updatedAt(device.getUpdatedAt())
                .build();
    }
}
