package com.zioneer.robotqcsystem.service.deploy.task.impl;

import com.zioneer.robotqcsystem.common.exception.BusinessException;
import com.zioneer.robotqcsystem.domain.dto.DeployTaskFlowTemplateCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.DeployTaskFlowTemplateUpdateDTO;
import com.zioneer.robotqcsystem.domain.entity.DeployTaskFlowTemplate;
import com.zioneer.robotqcsystem.mapper.DeployTaskFlowTemplateMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeployTaskFlowTemplateServiceImplTest {

    @Mock
    private DeployTaskFlowTemplateMapper taskFlowTemplateMapper;

    @InjectMocks
    private DeployTaskFlowTemplateServiceImpl service;

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void create_shouldTrimFields_andUseCurrentUser() {
        DeployTaskFlowTemplateCreateDTO dto = new DeployTaskFlowTemplateCreateDTO();
        dto.setCode(" TASK-T03 ");
        dto.setName("  Flow Name  ");
        dto.setPriority(2);
        dto.setEnabled(true);

        when(taskFlowTemplateMapper.selectByCode("TASK-T03")).thenReturn(null);
        setCurrentUser("ops-user");

        service.create(dto);

        ArgumentCaptor<DeployTaskFlowTemplate> captor = ArgumentCaptor.forClass(DeployTaskFlowTemplate.class);
        verify(taskFlowTemplateMapper).insert(captor.capture());
        DeployTaskFlowTemplate inserted = captor.getValue();
        assertEquals("TASK-T03", inserted.getCode());
        assertEquals("Flow Name", inserted.getName());
        assertEquals("ops-user", inserted.getCreatedBy());
        assertEquals("ops-user", inserted.getUpdatedBy());
    }

    @Test
    void update_shouldRejectBlankNameWhenProvided() {
        DeployTaskFlowTemplate existing = DeployTaskFlowTemplate.builder()
                .code("TASK-T01")
                .name("Old Flow")
                .enabled(true)
                .priority(1)
                .build();
        when(taskFlowTemplateMapper.selectByCode("TASK-T01")).thenReturn(existing);

        DeployTaskFlowTemplateUpdateDTO dto = new DeployTaskFlowTemplateUpdateDTO();
        dto.setName("  ");

        assertThrows(BusinessException.class, () -> service.update(" TASK-T01 ", dto));
    }

    @Test
    void delete_shouldRejectWhenReferencedByTask() {
        DeployTaskFlowTemplate existing = DeployTaskFlowTemplate.builder()
                .code("TASK-T01")
                .name("Old Flow")
                .enabled(true)
                .priority(1)
                .build();
        when(taskFlowTemplateMapper.selectByCode("TASK-T01")).thenReturn(existing);
        when(taskFlowTemplateMapper.countTaskReferences("TASK-T01")).thenReturn(2L);

        assertThrows(BusinessException.class, () -> service.deleteByCode(" TASK-T01 "));
    }

    private void setCurrentUser(String username) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(username, "N/A"));
        SecurityContextHolder.setContext(context);
    }
}
