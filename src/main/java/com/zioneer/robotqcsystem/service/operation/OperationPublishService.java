package com.zioneer.robotqcsystem.service.operation;

import com.zioneer.robotqcsystem.common.page.PageResult;
import com.zioneer.robotqcsystem.domain.dto.OperationPublishCreateDTO;
import com.zioneer.robotqcsystem.domain.dto.OperationPublishDeviceQuery;
import com.zioneer.robotqcsystem.domain.dto.OperationPublishQuery;
import com.zioneer.robotqcsystem.domain.dto.OperationPublishUpdateDTO;
import com.zioneer.robotqcsystem.domain.vo.OperationPublishCancelResultVO;
import com.zioneer.robotqcsystem.domain.vo.OperationPublishCreateResultVO;
import com.zioneer.robotqcsystem.domain.vo.OperationPublishDeviceCancelResultVO;
import com.zioneer.robotqcsystem.domain.vo.OperationPublishDeviceVO;
import com.zioneer.robotqcsystem.domain.vo.OperationPublishVO;

/**
 * 发布管理服务。
 */
public interface OperationPublishService {

    /**
     * 发布任务列表。
     *
     * @param query 查询参数
     * @return 分页结果
     */
    PageResult<OperationPublishVO> page(OperationPublishQuery query);

    /**
     * 创建发布任务。
     *
     * @param dto 创建请求
     * @return 创建结果
     */
    OperationPublishCreateResultVO create(OperationPublishCreateDTO dto);

    /**
     * 更新发布任务。
     *
     * @param id 任务ID
     * @param dto 更新请求
     */
    void update(Long id, OperationPublishUpdateDTO dto);

    /**
     * 发布任务设备进度列表。
     *
     * @param publishId 任务ID
     * @param query 查询参数
     * @return 分页结果
     */
    PageResult<OperationPublishDeviceVO> devicePage(Long publishId, OperationPublishDeviceQuery query);

    /**
     * 取消发布任务。
     *
     * @param id 任务ID
     * @return 取消结果
     */
    OperationPublishCancelResultVO cancel(Long id);

    /**
     * 取消单个设备升级。
     *
     * @param publishId 任务ID
     * @param deviceId 设备进度ID
     * @return 取消结果
     */
    OperationPublishDeviceCancelResultVO cancelDevice(Long publishId, Long deviceId);
}
