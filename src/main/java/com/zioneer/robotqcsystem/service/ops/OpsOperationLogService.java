package com.zioneer.robotqcsystem.service.ops;

import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.domain.dto.OpsOperationLogQuery;
import com.zioneer.robotqcsystem.domain.vo.OpsOperationLogVO;

/**
 * Operation log service.
 */
public interface OpsOperationLogService {

    PageResult<OpsOperationLogVO> page(OpsOperationLogQuery query);

    void record(String user,
                String operationType,
                String result,
                String failReason,
                Integer responseTime,
                String ip,
                String requestInfo,
                String responseInfo);
}
