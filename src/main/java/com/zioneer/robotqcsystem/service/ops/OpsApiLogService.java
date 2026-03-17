package com.zioneer.robotqcsystem.service.ops;

import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.domain.dto.OpsApiLogQuery;
import com.zioneer.robotqcsystem.domain.vo.OpsApiLogVO;

/**
 * API log service.
 */
public interface OpsApiLogService {

    PageResult<OpsApiLogVO> page(OpsApiLogQuery query);

    void record(String apiName,
                String result,
                String failReason,
                Integer responseTime,
                String requestInfo,
                String responseInfo);
}
