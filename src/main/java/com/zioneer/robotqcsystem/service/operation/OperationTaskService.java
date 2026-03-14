package com.zioneer.robotqcsystem.service.operation;

import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.domain.dto.OperationTaskQuery;
import com.zioneer.robotqcsystem.domain.vo.OperationTaskActionResultVO;
import com.zioneer.robotqcsystem.domain.vo.OperationTaskVO;

/**
 * 任务流服务。
 */
public interface OperationTaskService {

    PageResult<OperationTaskVO> page(OperationTaskQuery query);

    OperationTaskActionResultVO pause(Long id);

    OperationTaskActionResultVO resume(Long id);

    OperationTaskActionResultVO cancel(Long id);
}