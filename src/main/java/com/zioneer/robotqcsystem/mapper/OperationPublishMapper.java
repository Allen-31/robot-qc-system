package com.zioneer.robotqcsystem.mapper;

import com.zioneer.robotqcsystem.domain.entity.OperationPublish;
import com.zioneer.robotqcsystem.domain.entity.OperationPublishDevice;
import com.zioneer.robotqcsystem.domain.entity.OperationPublishTargetRobot;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 发布任务 Mapper。
 */
@Mapper
public interface OperationPublishMapper {

    /**
     * 查询发布任务。
     *
     * @param id 发布任务ID
     * @return 发布任务
     */
    OperationPublish selectById(@Param("id") Long id);

    /**
     * 统计发布任务列表。
     *
     * @param keyword 关键字
     * @param status 状态
     * @param strategy 策略
     * @param packageName 安装包名称
     * @param creator 创建人
     * @return 总数
     */
    long countList(@Param("keyword") String keyword,
                   @Param("status") String status,
                   @Param("strategy") String strategy,
                   @Param("packageName") String packageName,
                   @Param("creator") String creator);

    /**
     * 查询发布任务列表。
     *
     * @param keyword 关键字
     * @param status 状态
     * @param strategy 策略
     * @param packageName 安装包名称
     * @param creator 创建人
     * @param offset 偏移量
     * @param pageSize 页大小
     * @return 列表
     */
    List<OperationPublish> selectList(@Param("keyword") String keyword,
                                      @Param("status") String status,
                                      @Param("strategy") String strategy,
                                      @Param("packageName") String packageName,
                                      @Param("creator") String creator,
                                      @Param("offset") int offset,
                                      @Param("pageSize") int pageSize);

    /**
     * 插入发布任务。
     *
     * @param publish 发布任务
     * @return 影响行数
     */
    int insert(OperationPublish publish);

    /**
     * 更新发布任务。
     *
     * @param publish 发布任务
     * @return 影响行数
     */
    int updateById(OperationPublish publish);

    /**
     * 更新发布任务状态。
     *
     * @param id 发布任务ID
     * @param status 状态
     * @param completedAt 完成时间
     * @param updatedAt 更新时间
     * @return 影响行数
     */
    int updateStatus(@Param("id") Long id,
                     @Param("status") String status,
                     @Param("completedAt") LocalDateTime completedAt,
                     @Param("updatedAt") LocalDateTime updatedAt);

    /**
     * 插入设备进度。
     *
     * @param device 设备进度
     * @return 影响行数
     */
    int insertDevice(OperationPublishDevice device);

    /**
     * 查询设备进度。
     *
     * @param id 设备进度ID
     * @return 设备进度
     */
    OperationPublishDevice selectDeviceById(@Param("id") Long id);

    /**
     * 统计设备进度列表。
     *
     * @param publishId 发布任务ID
     * @return 总数
     */
    long countDeviceList(@Param("publishId") Long publishId);

    /**
     * 查询设备进度列表。
     *
     * @param publishId 发布任务ID
     * @param offset 偏移量
     * @param pageSize 页大小
     * @return 设备列表
     */
    List<OperationPublishDevice> selectDeviceList(@Param("publishId") Long publishId,
                                                  @Param("offset") int offset,
                                                  @Param("pageSize") int pageSize);

    /**
     * 批量查询设备进度。
     *
     * @param publishIds 发布任务ID列表
     * @return 设备列表
     */
    List<OperationPublishDevice> selectDevicesByPublishIds(@Param("publishIds") List<Long> publishIds);

    /**
     * 更新设备进度状态。
     *
     * @param id 设备进度ID
     * @param status 状态
     * @param progress 进度
     * @param updatedAt 更新时间
     * @param completedAt 完成时间
     * @return 影响行数
     */
    int updateDeviceStatus(@Param("id") Long id,
                           @Param("status") String status,
                           @Param("progress") Integer progress,
                           @Param("updatedAt") LocalDateTime updatedAt,
                           @Param("completedAt") LocalDateTime completedAt);

    /**
     * 批量取消设备进度。
     *
     * @param publishId 发布任务ID
     * @param updatedAt 更新时间
     * @return 影响行数
     */
    int cancelDevicesByPublishId(@Param("publishId") Long publishId,
                                 @Param("updatedAt") LocalDateTime updatedAt);

    /**
     * 删除任务下所有设备进度。
     *
     * @param publishId 发布任务ID
     * @return 影响行数
     */
    int deleteDevicesByPublishId(@Param("publishId") Long publishId);

    /**
     * 按机器人编码/分组/类型解析发布目标。
     *
     * @param robotCodes 机器人编码
     * @param robotGroups 机器人分组
     * @param robotTypes 机器人类型
     * @return 匹配到的目标机器人
     */
    List<OperationPublishTargetRobot> selectTargetRobots(@Param("robotCodes") List<String> robotCodes,
                                                         @Param("robotGroups") List<String> robotGroups,
                                                         @Param("robotTypes") List<String> robotTypes);
}
