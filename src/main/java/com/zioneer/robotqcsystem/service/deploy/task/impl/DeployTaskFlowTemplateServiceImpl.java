package com.zioneer.robotqcsystem.service.deploy.task.impl;

import com.zioneer.robotqcsystem.common.exception.BusinessException;
import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.common.result.ResultCode;
import com.zioneer.robotqcsystem.common.security.SecurityUserUtils;
import com.zioneer.robotqcsystem.domain.dto.DeployTaskFlowTemplateCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.DeployTaskFlowTemplateQuery;
import com.zioneer.robotqcsystem.domain.dto.DeployTaskFlowTemplateUpdateDTO;
import com.zioneer.robotqcsystem.domain.entity.DeployTaskFlowTemplate;
import com.zioneer.robotqcsystem.domain.vo.DeployTaskFlowTemplateVO;
import com.zioneer.robotqcsystem.mapper.DeployTaskFlowTemplateMapper;
import com.zioneer.robotqcsystem.service.deploy.task.DeployTaskFlowTemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 任务流模板服务实现。
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DeployTaskFlowTemplateServiceImpl implements DeployTaskFlowTemplateService {

    private final DeployTaskFlowTemplateMapper taskFlowTemplateMapper;

    @Override
    public PageResult<DeployTaskFlowTemplateVO> page(DeployTaskFlowTemplateQuery query) {
        String keyword = StringUtils.hasText(query.getKeyword()) ? query.getKeyword().trim() : null;
        long total = taskFlowTemplateMapper.countList(keyword, query.getEnabled());
        if (total == 0) {
            return PageResult.empty(query);
        }
        List<DeployTaskFlowTemplateVO> list = taskFlowTemplateMapper
                .selectList(keyword, query.getEnabled(), query.getOffset(), query.getPageSize())
                .stream()
                .map(this::toVO)
                .collect(Collectors.toList());
        return PageResult.of(list, total, query.getPageNum(), query.getPageSize());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DeployTaskFlowTemplateVO create(DeployTaskFlowTemplateCreateDTO dto) {
        String code = normalizeRequired(dto.getCode(), "模板编码");
        String name = normalizeRequired(dto.getName(), "模板名称");
        if (taskFlowTemplateMapper.selectByCode(code) != null) {
            throw new BusinessException("模板编码已存在：" + code);
        }
        LocalDateTime now = LocalDateTime.now();
        String operator = SecurityUserUtils.currentUsernameOr("system");
        DeployTaskFlowTemplate template = DeployTaskFlowTemplate.builder()
                .code(code)
                .name(name)
                .enabled(dto.getEnabled() != null ? dto.getEnabled() : Boolean.TRUE)
                .priority(dto.getPriority() != null ? dto.getPriority() : 1)
                .createdBy(operator)
                .createdAt(now)
                .updatedBy(operator)
                .updatedAt(now)
                .build();
        taskFlowTemplateMapper.insert(template);
        log.info("create task flow template, code={}", code);
        return toVO(template);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(String code, DeployTaskFlowTemplateUpdateDTO dto) {
        String normalizedCode = normalizeRequired(code, "模板编码");
        DeployTaskFlowTemplate existing = taskFlowTemplateMapper.selectByCode(normalizedCode);
        if (existing == null) {
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "模板不存在");
        }
        String name = existing.getName();
        if (dto.getName() != null) {
            name = normalizeRequired(dto.getName(), "模板名称");
        }
        String operator = SecurityUserUtils.currentUsernameOr("system");
        DeployTaskFlowTemplate template = DeployTaskFlowTemplate.builder()
                .code(normalizedCode)
                .name(name)
                .enabled(dto.getEnabled() != null ? dto.getEnabled() : existing.getEnabled())
                .priority(dto.getPriority() != null ? dto.getPriority() : existing.getPriority())
                .updatedBy(operator)
                .updatedAt(LocalDateTime.now())
                .build();
        taskFlowTemplateMapper.updateByCode(template);
        log.info("update task flow template, code={}", normalizedCode);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByCode(String code) {
        String normalizedCode = normalizeRequired(code, "模板编码");
        if (taskFlowTemplateMapper.selectByCode(normalizedCode) == null) {
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "模板不存在");
        }
        long referenceCount = taskFlowTemplateMapper.countTaskReferences(normalizedCode);
        if (referenceCount > 0) {
            throw new BusinessException("模板已被任务引用，不能删除");
        }
        taskFlowTemplateMapper.deleteByCode(normalizedCode);
        log.info("delete task flow template, code={}", normalizedCode);
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

    private DeployTaskFlowTemplateVO toVO(DeployTaskFlowTemplate template) {
        return DeployTaskFlowTemplateVO.builder()
                .code(template.getCode())
                .name(template.getName())
                .enabled(template.getEnabled())
                .priority(template.getPriority())
                .createdBy(template.getCreatedBy())
                .createdAt(template.getCreatedAt())
                .updatedBy(template.getUpdatedBy())
                .updatedAt(template.getUpdatedAt())
                .build();
    }
}