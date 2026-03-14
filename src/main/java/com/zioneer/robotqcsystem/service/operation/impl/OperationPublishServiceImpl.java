package com.zioneer.robotqcsystem.service.operation.impl;

import com.zioneer.robotqcsystem.common.exception.BusinessException;
import com.zioneer.robotqcsystem.common.id.SnowflakeIdGenerator;
import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.common.result.ResultCode;
import com.zioneer.robotqcsystem.domain.dto.OperationPublishCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.OperationPublishDeviceQuery;
import com.zioneer.robotqcsystem.domain.dto.OperationPublishQuery;
import com.zioneer.robotqcsystem.domain.dto.OperationPublishUpdateDTO;
import com.zioneer.robotqcsystem.domain.entity.OperationPublish;
import com.zioneer.robotqcsystem.domain.entity.OperationPublishDevice;
import com.zioneer.robotqcsystem.domain.entity.OperationPublishTargetRobot;
import com.zioneer.robotqcsystem.domain.vo.OperationPublishCancelResultVO;
import com.zioneer.robotqcsystem.domain.vo.OperationPublishCreateResultVO;
import com.zioneer.robotqcsystem.domain.vo.OperationPublishDeviceCancelResultVO;
import com.zioneer.robotqcsystem.domain.vo.OperationPublishDeviceVO;
import com.zioneer.robotqcsystem.domain.vo.OperationPublishVO;
import com.zioneer.robotqcsystem.mapper.OperationPublishMapper;
import com.zioneer.robotqcsystem.service.operation.OperationPublishService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 发布管理服务实现。
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OperationPublishServiceImpl implements OperationPublishService {

    private static final Pattern VERSION_PATTERN = Pattern.compile("(v?\\d+(?:\\.\\d+)+)", Pattern.CASE_INSENSITIVE);
    private static final Set<String> ACTIVE_TASK_STATUSES = Set.of("pending", "running");
    private static final Set<String> ACTIVE_DEVICE_STATUSES = Set.of("pending", "upgrading");

    private final OperationPublishMapper operationPublishMapper;
    private final SnowflakeIdGenerator idGenerator;

    /** {@inheritDoc} */
    @Override
    public PageResult<OperationPublishVO> page(OperationPublishQuery query) {
        String keyword = StringUtils.hasText(query.getKeyword()) ? query.getKeyword().trim() : null;
        String status = StringUtils.hasText(query.getStatus()) ? query.getStatus().trim() : null;
        String strategy = StringUtils.hasText(query.getStrategy()) ? query.getStrategy().trim() : null;
        String packageName = StringUtils.hasText(query.getPackageName()) ? query.getPackageName().trim() : null;
        String creator = StringUtils.hasText(query.getCreator()) ? query.getCreator().trim() : null;

        long total = operationPublishMapper.countList(keyword, status, strategy, packageName, creator);
        if (total == 0) {
            return PageResult.empty(query);
        }
        List<OperationPublish> list = operationPublishMapper.selectList(
                keyword, status, strategy, packageName, creator, query.getOffset(), query.getPageSize());
        List<Long> ids = list.stream().map(OperationPublish::getId).collect(Collectors.toList());
        Map<Long, List<OperationPublishDeviceVO>> deviceMap = fetchDevices(ids);

        List<OperationPublishVO> voList = list.stream()
                .map(p -> toVO(p, deviceMap.getOrDefault(p.getId(), Collections.emptyList())))
                .collect(Collectors.toList());
        return PageResult.of(voList, total, query.getPageNum(), query.getPageSize());
    }

    /** {@inheritDoc} */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OperationPublishCreateResultVO create(OperationPublishCreateDTO dto) {
        LocalDateTime now = LocalDateTime.now();
        Long id = idGenerator.nextId();
        List<String> targetRobots = normalizeList(dto.getTargetRobots());
        List<String> targetRobotGroups = normalizeList(dto.getTargetRobotGroups());
        List<String> targetRobotTypes = normalizeList(dto.getTargetRobotTypes());
        List<OperationPublishTargetRobot> targets = resolveTargets(targetRobots, targetRobotGroups, targetRobotTypes);

        OperationPublish publish = OperationPublish.builder()
                .id(id)
                .name(dto.getName().trim())
                .packageName(dto.getPackageName().trim())
                .targetRobots(targetRobots)
                .targetRobotGroups(targetRobotGroups)
                .targetRobotTypes(targetRobotTypes)
                .strategy(dto.getStrategy().trim())
                .restartAfterUpgrade(dto.getRestartAfterUpgrade())
                .status("pending")
                .creator("system")
                .createdAt(now)
                .updatedAt(now)
                .completedAt(null)
                .build();
        operationPublishMapper.insert(publish);
        rebuildDevices(id, dto.getPackageName().trim(), targets, now);

        return OperationPublishCreateResultVO.builder()
                .id(id)
                .name(dto.getName().trim())
                .status("pending")
                .createdAt(now)
                .build();
    }

    /** {@inheritDoc} */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Long id, OperationPublishUpdateDTO dto) {
        OperationPublish exist = operationPublishMapper.selectById(id);
        if (exist == null) {
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "发布任务不存在");
        }
        if (!ACTIVE_TASK_STATUSES.contains(exist.getStatus())) {
            throw new BusinessException("仅待发布或发布中任务允许编辑");
        }
        boolean hasName = StringUtils.hasText(dto.getName());
        boolean hasPackage = StringUtils.hasText(dto.getPackageName());
        boolean hasStrategy = StringUtils.hasText(dto.getStrategy());
        boolean hasRestart = dto.getRestartAfterUpgrade() != null;
        boolean hasRobotCodes = dto.getTargetRobots() != null;
        boolean hasRobotGroups = dto.getTargetRobotGroups() != null;
        boolean hasRobotTypes = dto.getTargetRobotTypes() != null;
        if (!hasName && !hasPackage && !hasStrategy && !hasRestart && !hasRobotCodes && !hasRobotGroups && !hasRobotTypes) {
            throw new BusinessException("请至少填写一项要更新的内容");
        }
        String effectivePackageName = hasPackage ? dto.getPackageName().trim() : exist.getPackageName();
        List<String> effectiveRobots = hasRobotCodes ? normalizeList(dto.getTargetRobots()) : normalizeList(exist.getTargetRobots());
        List<String> effectiveGroups = hasRobotGroups ? normalizeList(dto.getTargetRobotGroups()) : normalizeList(exist.getTargetRobotGroups());
        List<String> effectiveTypes = hasRobotTypes ? normalizeList(dto.getTargetRobotTypes()) : normalizeList(exist.getTargetRobotTypes());
        OperationPublish publish = OperationPublish.builder()
                .id(id)
                .name(hasName ? dto.getName().trim() : null)
                .packageName(hasPackage ? effectivePackageName : null)
                .targetRobots(hasRobotCodes ? effectiveRobots : null)
                .targetRobotGroups(hasRobotGroups ? effectiveGroups : null)
                .targetRobotTypes(hasRobotTypes ? effectiveTypes : null)
                .strategy(hasStrategy ? dto.getStrategy().trim() : null)
                .restartAfterUpgrade(hasRestart ? dto.getRestartAfterUpgrade() : null)
                .updatedAt(LocalDateTime.now())
                .build();
        operationPublishMapper.updateById(publish);
        if (hasPackage || hasRobotCodes || hasRobotGroups || hasRobotTypes) {
            List<OperationPublishTargetRobot> targets = resolveTargets(effectiveRobots, effectiveGroups, effectiveTypes);
            rebuildDevices(id, effectivePackageName, targets, LocalDateTime.now());
        }
    }

    /** {@inheritDoc} */
    @Override
    public PageResult<OperationPublishDeviceVO> devicePage(Long publishId, OperationPublishDeviceQuery query) {
        OperationPublish exist = operationPublishMapper.selectById(publishId);
        if (exist == null) {
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "发布任务不存在");
        }
        long total = operationPublishMapper.countDeviceList(publishId);
        if (total == 0) {
            return PageResult.empty(query);
        }
        List<OperationPublishDevice> list = operationPublishMapper.selectDeviceList(
                publishId, query.getOffset(), query.getPageSize());
        List<OperationPublishDeviceVO> voList = list.stream()
                .map(this::toDeviceVO)
                .collect(Collectors.toList());
        return PageResult.of(voList, total, query.getPageNum(), query.getPageSize());
    }

    /** {@inheritDoc} */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OperationPublishCancelResultVO cancel(Long id) {
        OperationPublish exist = operationPublishMapper.selectById(id);
        if (exist == null) {
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "发布任务不存在");
        }
        if ("cancelled".equals(exist.getStatus())) {
            throw new BusinessException("任务已取消");
        }
        if ("completed".equals(exist.getStatus())) {
            throw new BusinessException("任务已完成，不能取消");
        }
        LocalDateTime now = LocalDateTime.now();
        operationPublishMapper.updateStatus(id, "cancelled", now, now);
        operationPublishMapper.cancelDevicesByPublishId(id, now);
        return OperationPublishCancelResultVO.builder()
                .id(id)
                .status("cancelled")
                .build();
    }

    /** {@inheritDoc} */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OperationPublishDeviceCancelResultVO cancelDevice(Long publishId, Long deviceId) {
        OperationPublish exist = operationPublishMapper.selectById(publishId);
        if (exist == null) {
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "发布任务不存在");
        }
        if (!ACTIVE_TASK_STATUSES.contains(exist.getStatus())) {
            throw new BusinessException("当前任务状态不允许取消设备升级");
        }
        OperationPublishDevice device = operationPublishMapper.selectDeviceById(deviceId);
        if (device == null || !publishId.equals(device.getPublishId())) {
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "设备进度不存在");
        }
        if ("cancelled".equals(device.getStatus())) {
            throw new BusinessException("设备已取消");
        }
        if ("completed".equals(device.getStatus())) {
            throw new BusinessException("设备已完成，不能取消");
        }
        if (!ACTIVE_DEVICE_STATUSES.contains(device.getStatus())) {
            throw new BusinessException("当前设备状态不允许取消");
        }
        LocalDateTime now = LocalDateTime.now();
        operationPublishMapper.updateDeviceStatus(deviceId, "cancelled", device.getProgress(), now, now);
        return OperationPublishDeviceCancelResultVO.builder()
                .deviceId(deviceId)
                .status("cancelled")
                .build();
    }

    private List<OperationPublishTargetRobot> resolveTargets(List<String> robotCodes,
                                                             List<String> robotGroups,
                                                             List<String> robotTypes) {
        if (robotCodes.isEmpty() && robotGroups.isEmpty() && robotTypes.isEmpty()) {
            throw new BusinessException("请至少选择一个发布目标（机器人/分组/类型）");
        }
        List<OperationPublishTargetRobot> targets = operationPublishMapper.selectTargetRobots(robotCodes, robotGroups, robotTypes);
        if (targets == null || targets.isEmpty()) {
            throw new BusinessException("未匹配到可发布设备，请检查目标机器人/分组/类型");
        }
        return targets;
    }

    private void rebuildDevices(Long publishId, String packageName, List<OperationPublishTargetRobot> targets, LocalDateTime now) {
        operationPublishMapper.deleteDevicesByPublishId(publishId);
        String version = extractVersion(packageName);
        for (OperationPublishTargetRobot target : targets) {
            OperationPublishDevice device = OperationPublishDevice.builder()
                    .id(idGenerator.nextId())
                    .publishId(publishId)
                    .deviceName(formatDeviceName(target.getRobotCode()))
                    .ip(target.getIp())
                    .status("pending")
                    .packageName(packageName)
                    .version(version)
                    .progress(0)
                    .updatedAt(now)
                    .completedAt(null)
                    .build();
            operationPublishMapper.insertDevice(device);
        }
    }

    private Map<Long, List<OperationPublishDeviceVO>> fetchDevices(List<Long> publishIds) {
        if (publishIds == null || publishIds.isEmpty()) {
            return Collections.emptyMap();
        }
        List<OperationPublishDevice> devices = operationPublishMapper.selectDevicesByPublishIds(publishIds);
        return devices.stream()
                .collect(Collectors.groupingBy(
                        OperationPublishDevice::getPublishId,
                        Collectors.mapping(this::toDeviceVO, Collectors.toList())));
    }

    private OperationPublishVO toVO(OperationPublish publish, List<OperationPublishDeviceVO> devices) {
        return OperationPublishVO.builder()
                .id(publish.getId())
                .name(publish.getName())
                .packageName(publish.getPackageName())
                .targetRobots(publish.getTargetRobots())
                .targetRobotGroups(publish.getTargetRobotGroups())
                .targetRobotTypes(publish.getTargetRobotTypes())
                .strategy(publish.getStrategy())
                .restartAfterUpgrade(publish.getRestartAfterUpgrade())
                .status(publish.getStatus())
                .creator(publish.getCreator())
                .createdAt(publish.getCreatedAt())
                .completedAt(publish.getCompletedAt())
                .devices(devices)
                .build();
    }

    private OperationPublishDeviceVO toDeviceVO(OperationPublishDevice device) {
        return OperationPublishDeviceVO.builder()
                .id(device.getId())
                .deviceName(device.getDeviceName())
                .ip(device.getIp())
                .status(device.getStatus())
                .packageName(device.getPackageName())
                .version(device.getVersion())
                .updatedAt(device.getUpdatedAt())
                .completedAt(device.getCompletedAt())
                .progress(device.getProgress())
                .build();
    }

    private static String formatDeviceName(String robotCode) {
        String code = robotCode.toLowerCase(Locale.ROOT).replace("_", "-");
        if (code.startsWith("robot-")) {
            return code;
        }
        return "robot-" + code;
    }

    private static String extractVersion(String text) {
        if (!StringUtils.hasText(text)) {
            return null;
        }
        Matcher matcher = VERSION_PATTERN.matcher(text);
        String version = null;
        while (matcher.find()) {
            version = matcher.group(1);
        }
        if (version == null) {
            return null;
        }
        return version.toLowerCase(Locale.ROOT).startsWith("v") ? version : "v" + version;
    }

    private static List<String> normalizeList(List<String> source) {
        if (source == null || source.isEmpty()) {
            return Collections.emptyList();
        }
        LinkedHashSet<String> set = new LinkedHashSet<>();
        for (String item : source) {
            if (StringUtils.hasText(item)) {
                set.add(item.trim());
            }
        }
        return set.isEmpty() ? Collections.emptyList() : new ArrayList<>(set);
    }
}
