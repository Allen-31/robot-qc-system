package com.zioneer.robotqcsystem.service.auth.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zioneer.robotqcsystem.domain.dto.LoginRequest;
import com.zioneer.robotqcsystem.domain.vo.LoginResponse;
import com.zioneer.robotqcsystem.integration.keycloak.KeycloakAdminClient;
import com.zioneer.robotqcsystem.service.auth.KeycloakLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * BFF 登录实现：调用 Keycloak token 接口（Resource Owner Password），解析 JWT 返回用户信息。
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class KeycloakLoginServiceImpl implements KeycloakLoginService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final KeycloakAdminClient keycloakAdmin;

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuerUri;

    @Value("${app.keycloak.client-id:robot-qc-frontend}")
    private String clientId;

    @Value("${app.keycloak.client-secret:}")
    private String clientSecret;

    @Override
    public LoginResponse login(LoginRequest request) {
        String tokenUrl = issuerUri + "/protocol/openid-connect/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "password");
        body.add("client_id", clientId);
        if (StringUtils.hasText(clientSecret)) {
            body.add("client_secret", clientSecret);
        }
        body.add("username", request.getUsername());
        body.add("password", request.getPassword());

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(
                    tokenUrl,
                    new HttpEntity<>(body, headers),
                    Map.class
            );
            Map<String, Object> tokenResponse = response.getBody();
            if (tokenResponse == null || !tokenResponse.containsKey("access_token")) {
                throw new BadCredentialsException("用户名或密码错误");
            }
            String accessToken = (String) tokenResponse.get("access_token");
            LoginResponse.UserInfoVO userInfo = parseUserInfoFromToken(accessToken);
            return LoginResponse.builder()
                    .token(accessToken)
                    .user(userInfo)
                    .build();
        } catch (HttpClientErrorException e) {
            String errorBody = e.getResponseBodyAsString();
            log.warn("Keycloak 登录失败 username=[{}] status=[{}] body=[{}]",
                    request.getUsername(), e.getStatusCode(), errorBody != null ? errorBody : "");
            if (e.getStatusCode().value() == 404 && errorBody != null && errorBody.contains("Realm does not exist")) {
                throw new BadCredentialsException("Keycloak Realm 未初始化，请先执行 scripts/keycloak-setup.ps1");
            }
            if (e.getStatusCode().value() == 400 && errorBody != null && errorBody.contains("Account is not fully set up")) {
                try {
                    Map<String, Object> user = keycloakAdmin.getUserByUsername(request.getUsername());
                    if (user != null && user.get("id") != null) {
                        String userId = (String) user.get("id");
                        keycloakAdmin.setUserPassword(userId, request.getPassword(), false);
                        keycloakAdmin.clearUserRequiredActions(userId);
                        log.info("已为用户 [{}] 重设永久密码并清除 Required Actions，正在重试登录", request.getUsername());
                        try {
                            Thread.sleep(800);
                        } catch (InterruptedException ie) {
                            Thread.currentThread().interrupt();
                        }
                        ResponseEntity<Map> retryResponse = restTemplate.postForEntity(
                                tokenUrl, new HttpEntity<>(body, headers), Map.class);
                        Map<String, Object> retryBody = retryResponse.getBody();
                        if (retryBody != null && retryBody.containsKey("access_token")) {
                            String accessToken = (String) retryBody.get("access_token");
                            LoginResponse.UserInfoVO userInfo = parseUserInfoFromToken(accessToken);
                            return LoginResponse.builder().token(accessToken).user(userInfo).build();
                        }
                        throw new BadCredentialsException("账户未完全设置，已自动修复，请重新登录");
                    }
                } catch (BadCredentialsException b) {
                    throw b;
                } catch (HttpClientErrorException retryEx) {
                    log.warn("重试登录仍失败: {}", retryEx.getResponseBodyAsString());
                    throw new BadCredentialsException("账户未完全设置，已自动修复，请重新登录");
                } catch (Exception ex) {
                    log.warn("修复 Required Actions 失败", ex);
                }
            }
            throw new BadCredentialsException("用户名或密码错误");
        } catch (ResourceAccessException e) {
            log.warn("无法连接 Keycloak，请确认已启动: {}", e.getMessage());
            throw new BadCredentialsException("认证服务暂时不可用，请确认 Keycloak 已启动（默认端口 8081）");
        }
    }

    private LoginResponse.UserInfoVO parseUserInfoFromToken(String accessToken) {
        String payloadJson;
        try {
            String[] parts = accessToken.split("\\.");
            if (parts.length < 2) {
                return fallbackUserInfo();
            }
            byte[] payloadBytes = Base64.getUrlDecoder().decode(parts[1]);
            payloadJson = new String(payloadBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.debug("JWT payload 解析失败", e);
            return fallbackUserInfo();
        }

        try {
            JsonNode root = objectMapper.readTree(payloadJson);
            String username = root.has("preferred_username")
                    ? root.get("preferred_username").asText()
                    : root.has("sub") ? root.get("sub").asText() : "unknown";
            List<String> roles = new ArrayList<>();
            if (root.has("realm_access") && root.get("realm_access").has("roles")) {
                for (JsonNode r : root.get("realm_access").get("roles")) {
                    roles.add(r.asText());
                }
            }
            return LoginResponse.UserInfoVO.builder()
                    .code(username)
                    .displayName(username)
                    .roles(roles)
                    .build();
        } catch (Exception e) {
            log.debug("解析 JWT 用户信息失败", e);
            return fallbackUserInfo();
        }
    }

    private LoginResponse.UserInfoVO fallbackUserInfo() {
        return LoginResponse.UserInfoVO.builder()
                .code("unknown")
                .displayName("unknown")
                .roles(Collections.emptyList())
                .build();
    }
}
