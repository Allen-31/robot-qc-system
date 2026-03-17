package com.zioneer.robotqcsystem.common.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

/**
 * Security helper for resolving current operator username.
 */
public final class SecurityUserUtils {

    private static final String ANONYMOUS_USER = "anonymousUser";

    private SecurityUserUtils() {
    }

    public static String currentUsernameOr(String fallback) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return fallback;
        }
        String name = authentication.getName();
        if (!StringUtils.hasText(name) || ANONYMOUS_USER.equals(name)) {
            return fallback;
        }
        return name.trim();
    }
}
