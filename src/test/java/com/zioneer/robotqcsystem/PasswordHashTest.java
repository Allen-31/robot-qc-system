package com.zioneer.robotqcsystem;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * 生成 admin123 的 BCrypt 哈希，用于 data.sql 或直接更新数据库
 */
class PasswordHashTest {

    private static final String ADMIN_PASSWORD = "admin123";
    private static final String CURRENT_HASH = "$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy";

    @Test
    void generateAndVerifyAdminPasswordHash() throws Exception {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        boolean currentMatches = encoder.matches(ADMIN_PASSWORD, CURRENT_HASH);
        String newHash = encoder.encode(ADMIN_PASSWORD);
        String out = "当前 data.sql 中的 hash 是否等于 admin123: " + currentMatches + "\n"
                + "admin123 的正确 BCrypt hash: " + newHash + "\n";
        Path p = Path.of("build", "admin123_hash.txt");
        Files.createDirectories(p.getParent());
        Files.writeString(p, out);
    }
}
