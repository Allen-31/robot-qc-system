package com.zioneer.robotqcsystem.service.qc;

import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.domain.dto.QcWorkOrderQuery;
import com.zioneer.robotqcsystem.domain.dto.QcWorkOrderReviewDTO;
import com.zioneer.robotqcsystem.domain.dto.QcWorkOrderUpdateDTO;
import com.zioneer.robotqcsystem.domain.vo.QcWorkOrderVO;

/**
 * 工单（2.1.3）
 */
public interface QcWorkOrderService {

    PageResult<QcWorkOrderVO> page(QcWorkOrderQuery query);

    QcWorkOrderVO getById(Long id);

    void update(Long id, QcWorkOrderUpdateDTO dto);

    /** 工单复检提交（2.1.3.4） */
    QcWorkOrderVO review(Long id, QcWorkOrderReviewDTO dto);

    QcWorkOrderVO pause(Long id);

    QcWorkOrderVO resume(Long id);

    QcWorkOrderVO cancel(Long id);

    void deleteById(Long id);
}
