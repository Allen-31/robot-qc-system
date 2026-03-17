package com.zioneer.robotqcsystem.service.deploy.task.impl;

import com.zioneer.robotqcsystem.common.exception.BusinessException;
import com.zioneer.robotqcsystem.domain.dto.DeployTaskTemplateCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.DeployTaskTemplateUpdateDTO;
import com.zioneer.robotqcsystem.domain.entity.DeployTaskTemplate;
import com.zioneer.robotqcsystem.mapper.DeployTaskTemplateMapper;
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
class DeployTaskTemplateServiceImplTest {

    @Mock
    private DeployTaskTemplateMapper taskTemplateMapper;

    @InjectMocks
    private DeployTaskTemplateServiceImpl service;

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void create_shouldTrimFields_andUseCurrentUser() {
        DeployTaskTemplateCreateDTO dto = new DeployTaskTemplateCreateDTO();
        dto.setCode(" TASK-A03 ");
        dto.setName("  Inspection  ");
        dto.setEnabled(true);

        when(taskTemplateMapper.selectByCode("TASK-A03")).thenReturn(null);
        setCurrentUser("tester");

        service.create(dto);

        ArgumentCaptor<DeployTaskTemplate> captor = ArgumentCaptor.forClass(DeployTaskTemplate.class);
        verify(taskTemplateMapper).insert(captor.capture());
        DeployTaskTemplate inserted = captor.getValue();
        assertEquals("TASK-A03", inserted.getCode());
        assertEquals("Inspection", inserted.getName());
        assertEquals("tester", inserted.getCreatedBy());
        assertEquals("tester", inserted.getUpdatedBy());
    }

    @Test
    void update_shouldRejectBlankNameWhenProvided() {
        DeployTaskTemplate existing = DeployTaskTemplate.builder()
                .code("TASK-A01")
                .name("Old")
                .enabled(true)
                .build();
        when(taskTemplateMapper.selectByCode("TASK-A01")).thenReturn(existing);

        DeployTaskTemplateUpdateDTO dto = new DeployTaskTemplateUpdateDTO();
        dto.setName("   ");

        assertThrows(BusinessException.class, () -> service.update(" TASK-A01 ", dto));
    }

    @Test
    void delete_shouldRejectWhenReferencedByTask() {
        DeployTaskTemplate existing = DeployTaskTemplate.builder()
                .code("TASK-A01")
                .name("Old")
                .enabled(true)
                .build();
        when(taskTemplateMapper.selectByCode("TASK-A01")).thenReturn(existing);
        when(taskTemplateMapper.countTaskReferences("TASK-A01")).thenReturn(1L);

        assertThrows(BusinessException.class, () -> service.deleteByCode(" TASK-A01 "));
    }

    private void setCurrentUser(String username) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(username, "N/A"));
        SecurityContextHolder.setContext(context);
    }
}
