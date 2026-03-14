package com.zioneer.robotqcsystem.domain.dto;

import com.zioneer.robotqcsystem.common.page.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 发布任务设备进度查询参数。
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "发布任务设备进度查询")
public class OperationPublishDeviceQuery extends PageQuery {
}
