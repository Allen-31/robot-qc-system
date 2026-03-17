package com.zioneer.robotqcsystem.service.deploy.task;

import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.domain.dto.DeployTaskTemplateCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.DeployTaskTemplateQuery;
import com.zioneer.robotqcsystem.domain.dto.DeployTaskTemplateUpdateDTO;
import com.zioneer.robotqcsystem.domain.vo.DeployTaskTemplateVO;

/**
 * 任务模板服务。
 */
public interface DeployTaskTemplateService {

    PageResult<DeployTaskTemplateVO> page(DeployTaskTemplateQuery query);

    DeployTaskTemplateVO create(DeployTaskTemplateCreateDTO dto);

    void update(String code, DeployTaskTemplateUpdateDTO dto);

    void deleteByCode(String code);
}