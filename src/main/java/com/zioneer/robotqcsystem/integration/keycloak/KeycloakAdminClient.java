package com.zioneer.robotqcsystem.integration.keycloak;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Keycloak Admin REST API 客户端：获取 admin token，调用用户/角色等管理接口。
 */
@Slf4j
@Component
public class KeycloakAdminClient {

    private final RestTemplate restTemplate;

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuerUri;

    @Value("${keycloak.admin.username:admin}")
    private String adminUsername;

    @Value("${keycloak.admin.password:admin}")
    private String adminPassword;

    private String cachedToken;
    private long tokenExpireAt;

    public KeycloakAdminClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private String getServerUrl() {
        if (issuerUri == null || !issuerUri.contains("/realms/")) {
            return "http://localhost:8081";
        }
        return issuerUri.substring(0, issuerUri.indexOf("/realms/"));
    }

    private String getRealm() {
        if (issuerUri == null || !issuerUri.contains("/realms/")) {
            return "robot-qc";
        }
        String after = issuerUri.substring(issuerUri.indexOf("/realms/") + 8);
        int slash = after.indexOf('/');
        return slash > 0 ? after.substring(0, slash) : after;
    }

    public String getAdminToken() {
        if (cachedToken != null && System.currentTimeMillis() < tokenExpireAt) {
            return cachedToken;
        }
        String serverUrl = getServerUrl();
        String tokenUrl = serverUrl + "/realms/master/protocol/openid-connect/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "password");
        body.add("client_id", "admin-cli");
        body.add("username", adminUsername);
        body.add("password", adminPassword);

        ResponseEntity<Map> response = restTemplate.postForEntity(
                tokenUrl, new HttpEntity<>(body, headers), Map.class);
        Map<String, Object> tokenResponse = response.getBody();
        if (tokenResponse == null || !tokenResponse.containsKey("access_token")) {
            throw new IllegalStateException("Keycloak admin token 获取失败");
        }
        cachedToken = (String) tokenResponse.get("access_token");
        int expiresIn = tokenResponse.containsKey("expires_in")
                ? ((Number) tokenResponse.get("expires_in")).intValue() : 60;
        tokenExpireAt = System.currentTimeMillis() + (expiresIn - 10) * 1000L;
        return cachedToken;
    }

    private HttpHeaders authHeaders() {
        HttpHeaders h = new HttpHeaders();
        h.setBearerAuth(getAdminToken());
        h.setContentType(MediaType.APPLICATION_JSON);
        return h;
    }

    public int countUsers(String search, Boolean enabled) {
        String serverUrl = getServerUrl();
        String realm = getRealm();
        String url = serverUrl + "/admin/realms/" + realm + "/users/count?";
        if (search != null && !search.isBlank()) {
            url += "search=" + search + "&";
        }
        if (enabled != null) {
            url += "enabled=" + enabled + "&";
        }
        try {
            ResponseEntity<Integer> r = restTemplate.exchange(
                    url, HttpMethod.GET, new HttpEntity<>(authHeaders()), Integer.class);
            return r.getBody() != null ? r.getBody() : 0;
        } catch (Exception e) {
            log.warn("countUsers failed", e);
            return 0;
        }
    }

    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> listUsers(int first, int max, String search, Boolean enabled) {
        String serverUrl = getServerUrl();
        String realm = getRealm();
        StringBuilder url = new StringBuilder(serverUrl + "/admin/realms/" + realm + "/users?first=" + first + "&max=" + max);
        if (search != null && !search.isBlank()) {
            url.append("&search=").append(search);
        }
        if (enabled != null) {
            url.append("&enabled=").append(enabled);
        }
        try {
            ResponseEntity<List> r = restTemplate.exchange(
                    url.toString(), HttpMethod.GET, new HttpEntity<>(authHeaders()), List.class);
            return r.getBody() != null ? r.getBody() : Collections.emptyList();
        } catch (Exception e) {
            log.warn("listUsers failed", e);
            return Collections.emptyList();
        }
    }

    public Map<String, Object> getUserByUsername(String username) {
        if (username == null || username.isBlank()) return null;
        List<Map<String, Object>> list = listUsers(0, 1, username, null);
        for (Map<String, Object> u : list) {
            if (username.equals(u.get("username"))) return u;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> getUserById(String userId) {
        if (userId == null || userId.isBlank()) return null;
        String serverUrl = getServerUrl();
        String realm = getRealm();
        String url = serverUrl + "/admin/realms/" + realm + "/users/" + userId;
        try {
            ResponseEntity<Map> r = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(authHeaders()), Map.class);
            return r.getBody();
        } catch (Exception e) {
            log.warn("getUserById failed", e);
            return null;
        }
    }

    /** 清除用户的必做操作，使密码模式登录可立即通过（否则 Keycloak 可能因未完成 Required Actions 返回 400） */
    public void clearUserRequiredActions(String userId) {
        Map<String, Object> user = getUserById(userId);
        if (user == null) return;
        user.remove("credentials");
        user.remove("createdTimestamp");
        user.put("requiredActions", Collections.emptyList());
        String username = (String) user.get("username");
        if (username == null) username = "user";
        if (!StringUtils.hasText((String) user.get("firstName"))) user.put("firstName", username);
        if (!StringUtils.hasText((String) user.get("lastName"))) user.put("lastName", "User");
        updateUser(userId, user);
    }

    public String createUser(Map<String, Object> userRep) {
        String serverUrl = getServerUrl();
        String realm = getRealm();
        String url = serverUrl + "/admin/realms/" + realm + "/users";
        HttpEntity<Map<String, Object>> req = new HttpEntity<>(userRep, authHeaders());
        ResponseEntity<Void> r = restTemplate.postForEntity(url, req, Void.class);
        if (r.getStatusCode().is2xxSuccessful() && r.getHeaders().getLocation() != null) {
            String path = r.getHeaders().getLocation().getPath();
            return path.substring(path.lastIndexOf('/') + 1);
        }
        return null;
    }

    public void updateUser(String userId, Map<String, Object> userRep) {
        String serverUrl = getServerUrl();
        String realm = getRealm();
        String url = serverUrl + "/admin/realms/" + realm + "/users/" + userId;
        restTemplate.put(url, new HttpEntity<>(userRep, authHeaders()));
    }

    public void deleteUser(String userId) {
        String serverUrl = getServerUrl();
        String realm = getRealm();
        String url = serverUrl + "/admin/realms/" + realm + "/users/" + userId;
        restTemplate.exchange(url, HttpMethod.DELETE, new HttpEntity<>(authHeaders()), Void.class);
    }

    public void setUserPassword(String userId, String newPassword, boolean temporary) {
        String serverUrl = getServerUrl();
        String realm = getRealm();
        String url = serverUrl + "/admin/realms/" + realm + "/users/" + userId + "/reset-password";
        Map<String, Object> body = Map.of(
                "type", "password",
                "value", newPassword,
                "temporary", temporary
        );
        restTemplate.put(url, new HttpEntity<>(body, authHeaders()));
    }

    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getUserRealmRoleMappings(String userId) {
        String serverUrl = getServerUrl();
        String realm = getRealm();
        String url = serverUrl + "/admin/realms/" + realm + "/users/" + userId + "/role-mappings/realm";
        try {
            ResponseEntity<List> r = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(authHeaders()), List.class);
            return r.getBody() != null ? r.getBody() : Collections.emptyList();
        } catch (Exception e) {
            log.warn("getUserRealmRoleMappings failed", e);
            return Collections.emptyList();
        }
    }

    public void addRealmRolesToUser(String userId, List<Map<String, Object>> roles) {
        if (roles == null || roles.isEmpty()) return;
        String serverUrl = getServerUrl();
        String realm = getRealm();
        String url = serverUrl + "/admin/realms/" + realm + "/users/" + userId + "/role-mappings/realm";
        restTemplate.postForObject(url, new HttpEntity<>(roles, authHeaders()), Void.class);
    }

    public void removeRealmRolesFromUser(String userId, List<Map<String, Object>> roles) {
        if (roles == null || roles.isEmpty()) return;
        String serverUrl = getServerUrl();
        String realm = getRealm();
        String url = serverUrl + "/admin/realms/" + realm + "/users/" + userId + "/role-mappings/realm";
        restTemplate.exchange(url, HttpMethod.DELETE, new HttpEntity<>(roles, authHeaders()), Void.class);
    }

    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> listRealmRoles() {
        String serverUrl = getServerUrl();
        String realm = getRealm();
        String url = serverUrl + "/admin/realms/" + realm + "/roles";
        try {
            ResponseEntity<List> r = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(authHeaders()), List.class);
            return r.getBody() != null ? r.getBody() : Collections.emptyList();
        } catch (Exception e) {
            log.warn("listRealmRoles failed", e);
            return Collections.emptyList();
        }
    }

    public void createRealmRole(String name, String description) {
        String serverUrl = getServerUrl();
        String realm = getRealm();
        String url = serverUrl + "/admin/realms/" + realm + "/roles";
        Map<String, Object> body = new java.util.HashMap<>();
        body.put("name", name);
        if (description != null) body.put("description", description);
        restTemplate.postForObject(url, new HttpEntity<>(body, authHeaders()), Void.class);
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> getRealmRoleByName(String roleName) {
        String serverUrl = getServerUrl();
        String realm = getRealm();
        String url = serverUrl + "/admin/realms/" + realm + "/roles/" + roleName;
        try {
            ResponseEntity<Map> r = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(authHeaders()), Map.class);
            return r.getBody();
        } catch (HttpClientErrorException.NotFound e) {
            return null;
        }
    }

    public void updateRealmRoleById(String roleId, String name, String description) {
        String serverUrl = getServerUrl();
        String realm = getRealm();
        String url = serverUrl + "/admin/realms/" + realm + "/roles-by-id/" + roleId;
        Map<String, Object> body = new java.util.HashMap<>();
        body.put("name", name);
        if (description != null) body.put("description", description);
        restTemplate.put(url, new HttpEntity<>(body, authHeaders()));
    }

    public void deleteRealmRoleByName(String roleName) {
        String serverUrl = getServerUrl();
        String realm = getRealm();
        String url = serverUrl + "/admin/realms/" + realm + "/roles/" + roleName;
        restTemplate.exchange(url, HttpMethod.DELETE, new HttpEntity<>(authHeaders()), Void.class);
    }
}
