package com.zioneer.robotqcsystem.service.deploy.task.impl;

import com.zioneer.robotqcsystem.common.exception.BusinessException;
import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.common.result.ResultCode;
import com.zioneer.robotqcsystem.common.security.SecurityUserUtils;
import com.zioneer.robotqcsystem.domain.dto.DeployTaskTemplateCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.DeployTaskTemplateQuery;
import com.zioneer.robotqcsystem.domain.dto.DeployTaskTemplateUpdateDTO;
import com.zioneer.robotqcsystem.domain.entity.DeployTaskTemplate;
import com.zioneer.robotqcsystem.domain.vo.DeployTaskTemplateVO;
import com.zioneer.robotqcsystem.mapper.DeployTaskTemplateMapper;
import com.zioneer.robotqcsystem.service.deploy.task.DeployTaskTemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 任务模板服务实现。
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DeployTaskTemplateServiceImpl implements DeployTaskTemplateService {

    private final DeployTaskTemplateMapper taskTemplateMapper;

    @Override
    public PageResult<DeployTaskTemplateVO> page(DeployTaskTemplateQuery query) {
        String keyword = StringUtils.hasText(query.getKeyword()) ? query.getKeyword().trim() : null;
        long total = taskTemplateMapper.countList(keyword, query.getEnabled());
        if (total == 0) {
            return PageResult.empty(query);
        }
        List<DeployTaskTemplateVO> list = taskTemplateMapper
                .selectList(keyword, query.getEnabled(), query.getOffset(), query.getPageSize())
                .stream()
                .map(this::toVO)
                .collect(Collectors.toList());
        return PageResult.of(list, total, query.getPageNum(), query.getPageSize());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DeployTaskTemplateVO create(DeployTaskTemplateCreateDTO dto) {
        String code = normalizeRequired(dto.getCode(), "模板编码");
        String name = normalizeRequired(dto.getName(), "模板名称");
        if (taskTemplateMapper.selectByCode(code) != null) {
            throw new BusinessException("模板编码已存在：" + code);
        }
        LocalDateTime now = LocalDateTime.now();
        String operator = SecurityUserUtils.currentUsernameOr("system");
        DeployTaskTemplate template = DeployTaskTemplate.builder()
                .code(code)
                .name(name)
                .enabled(dto.getEnabled() != null ? dto.getEnabled() : Boolean.TRUE)
                .createdBy(operator)
                .createdAt(now)
                .updatedBy(operator)
                .updatedAt(now)
                .build();
        taskTemplateMapper.insert(template);
        log.info("create task template, code={}", code);
        return toVO(template);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(String code, DeployTaskTemplateUpdateDTO dto) {
        String normalizedCode = normalizeRequired(code, "模板编码");
        DeployTaskTemplate existing = taskTemplateMapper.selectByCode(normalizedCode);
        if (existing == null) {
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "模板不存在");
        }
        String name = existing.getName();
        if (dto.getName() != null) {
            name = normalizeRequired(dto.getName(), "模板名称");
        }
        String operator = SecurityUserUtils.currentUsernameOr("system");
        DeployTaskTemplate template = DeployTaskTemplate.builder()
                .code(normalizedCode)
                .name(name)
                .enabled(dto.getEnabled() != null ? dto.getEnabled() : existing.getEnabled())
                .updatedBy(operator)
                .updatedAt(LocalDateTime.now())
                .build();
        taskTemplateMapper.updateByCode(template);
        log.info("update task template, code={}", normalizedCode);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByCode(String code) {
        String normalizedCode = normalizeRequired(code, "模板编码");
        if (taskTemplateMapper.selectByCode(normalizedCode) == null) {
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "模板不存在");
        }
        long referenceCount = taskTemplateMapper.countTaskReferences(normalizedCode);
        if (referenceCount > 0) {
            throw new BusinessException("模板已被任务引用，不能删除");
        }
        taskTemplateMapper.deleteByCode(normalizedCode);
        log.info("delete task template, code={}", normalizedCode);
    }

    private String normalizeRequired(String input, String fieldName) {
        if (input == null) {
            throw new BusinessException(fieldName + "不能为空");
        }
        String normalized = input.trim();
        if (!StringUtils.hasText(normalized)) {
            throw new BusinessException(fieldName + "不能为空");
        }
        return normalized;
    }

    private DeployTaskTemplateVO toVO(DeployTaskTemplate template) {
        return DeployTaskTemplateVO.builder()
                .code(template.getCode())
                .name(template.getName())
                .enabled(template.getEnabled())
                .createdBy(template.getCreatedBy())
                .createdAt(template.getCreatedAt())
                .updatedBy(template.getUpdatedBy())
                .updatedAt(template.getUpdatedAt())
                .build();
    }
}