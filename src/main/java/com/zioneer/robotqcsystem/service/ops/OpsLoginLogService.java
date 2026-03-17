package com.zioneer.robotqcsystem.service.ops;

import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.domain.dto.OpsLoginLogQuery;
import com.zioneer.robotqcsystem.domain.vo.OpsLoginLogVO;

/**
 * Login log service.
 */
public interface OpsLoginLogService {

    PageResult<OpsLoginLogVO> page(OpsLoginLogQuery query);

    void record(String user, String type, String ip);
}
