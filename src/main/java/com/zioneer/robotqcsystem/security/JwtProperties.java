package com.zioneer.robotqcsystem.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * JWT 配置项
 */
@Data
@Component
@ConfigurationProperties(prefix = "app.jwt")
public class JwtProperties {

    /** 密钥（至少 256 位用于 HS256） */
    private String secret = "RobotQcSystemJwtSecretKeyForSigningTokenAtLeast256BitsRequired";
    /** 访问令牌有效期（秒），默认 24 小时 */
    private long accessTokenSeconds = 86400L;
    /** 记住登录时延长倍数，默认 7 天 */
    private long rememberMultiplier = 7L;
    /** 签发者 */
    private String issuer = "RobotQcSystem";
}
