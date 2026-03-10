package com.zioneer.robotqcsystem.common.constant;

/**
 * 通用常量
 */
public final class CommonConstant {

    private CommonConstant() {
    }

    /** 分页默认页码 */
    public static final int DEFAULT_PAGE_NUM = 1;
    /** 分页默认每页条数 */
    public static final int DEFAULT_PAGE_SIZE = 20;
    /** 分页最大每页条数 */
    public static final int MAX_PAGE_SIZE = 500;

    /** Redis 键前缀 */
    public static final String REDIS_KEY_PREFIX = "robot_qc:";
    /** 默认缓存过期时间(秒) */
    public static final long DEFAULT_CACHE_SECONDS = 3600L;
}
