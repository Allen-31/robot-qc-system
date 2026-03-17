package com.zioneer.robotqcsystem.service.deploy.task;

import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.domain.dto.DeployTaskFlowTemplateCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.DeployTaskFlowTemplateQuery;
import com.zioneer.robotqcsystem.domain.dto.DeployTaskFlowTemplateUpdateDTO;
import com.zioneer.robotqcsystem.domain.vo.DeployTaskFlowTemplateVO;

/**
 * 任务流模板服务。
 */
public interface DeployTaskFlowTemplateService {

    PageResult<DeployTaskFlowTemplateVO> page(DeployTaskFlowTemplateQuery query);

    DeployTaskFlowTemplateVO create(DeployTaskFlowTemplateCreateDTO dto);

    void update(String code, DeployTaskFlowTemplateUpdateDTO dto);

    void deleteByCode(String code);
}