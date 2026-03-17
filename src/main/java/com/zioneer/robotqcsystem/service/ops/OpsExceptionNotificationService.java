package com.zioneer.robotqcsystem.service.ops;

import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.domain.dto.OpsExceptionNotificationCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.OpsExceptionNotificationQuery;
import com.zioneer.robotqcsystem.domain.vo.OpsExceptionNotificationVO;

/**
 * Exception notification service.
 */
public interface OpsExceptionNotificationService {

    PageResult<OpsExceptionNotificationVO> page(OpsExceptionNotificationQuery query);

    Long create(OpsExceptionNotificationCreateDTO dto);

    void updateStatus(Long id, String status);
}
