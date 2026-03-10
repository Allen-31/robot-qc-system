package com.zioneer.robotqcsystem.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis 配置：统一扫描 Mapper 接口
 */
@Configuration
@MapperScan("com.zioneer.robotqcsystem.mapper")
public class MyBatisConfig {
}
