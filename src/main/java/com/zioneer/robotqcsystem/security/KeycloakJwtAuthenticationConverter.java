package com.zioneer.robotqcsystem.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 将 Keycloak JWT 转为本系统使用的 Authentication（principal = 用户编码，authorities = 角色）
 * 解析 realm_access.roles；若使用 Client 角色可在 resolveAuthorities 中增加 resource_access 解析。
 */
public class KeycloakJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt jwt) {
        String userCode = jwt.getClaimAsString("preferred_username");
        if (userCode == null) {
            userCode = jwt.getSubject();
        }
        Collection<GrantedAuthority> authorities = resolveAuthorities(jwt);
        return new JwtAuthenticationToken(jwt, authorities, userCode);
    }

    /**
     * Keycloak 标准 JWT 中 realm_access 为 { "roles": ["admin", ...] }。
     * 若使用 Client 角色，可解析 resource_access.&lt;client-id&gt;.roles 与 realm 合并。
     */
    private Collection<GrantedAuthority> resolveAuthorities(Jwt jwt) {
        List<String> realmRoles = Optional.ofNullable(jwt.getClaim("realm_access"))
                .filter(ra -> ra instanceof Map)
                .map(ra -> (Map<?, ?>) ra)
                .map(m -> m.get("roles"))
                .filter(r -> r instanceof List)
                .map(r -> (List<?>) r)
                .map(list -> list.stream().map(String::valueOf).collect(Collectors.toList()))
                .orElse(Collections.emptyList());
        return realmRoles.stream()
                .map(r -> new SimpleGrantedAuthority("ROLE_" + r))
                .collect(Collectors.toList());
    }
}
