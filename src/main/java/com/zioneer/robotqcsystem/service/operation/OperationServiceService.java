package com.zioneer.robotqcsystem.service.operation;

import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.domain.dto.OperationServiceLogQuery;
import com.zioneer.robotqcsystem.domain.dto.OperationServiceQuery;
import com.zioneer.robotqcsystem.domain.vo.OperationServiceLogVO;
import com.zioneer.robotqcsystem.domain.vo.OperationServiceVO;

/**
 * Operation service management.
 */
public interface OperationServiceService {

    PageResult<OperationServiceVO> page(OperationServiceQuery query);

    OperationServiceVO start(Long id);

    OperationServiceVO stop(Long id);

    OperationServiceVO restart(Long id);

    PageResult<OperationServiceLogVO> logPage(Long serviceId, OperationServiceLogQuery query);
}
