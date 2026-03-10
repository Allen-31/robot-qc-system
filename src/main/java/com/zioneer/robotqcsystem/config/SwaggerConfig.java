package com.zioneer.robotqcsystem.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger / OpenAPI 文档配置
 * 启动后访问: http://localhost:8080/swagger-ui.html
 * 需认证接口可在页面顶部点击 Authorize 输入 Bearer &lt;token&gt;
 */
@Configuration
public class SwaggerConfig {

    private static final String BEARER_AUTH = "BearerAuth";

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("机器人质检管理系统 API")
                        .description("Robot QC System 后台接口文档，支持在线调试。登录后可在 Authorize 中填写 Bearer &lt;token&gt; 调用需认证接口。")
                        .version("0.0.1")
                        .contact(new Contact()
                                .name("RobotQcSystem")
                                .url("")))
                .addSecurityItem(new SecurityRequirement().addList(BEARER_AUTH))
                .components(new Components()
                        .addSecuritySchemes(BEARER_AUTH,
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("登录后获得的 JWT，填写时只需填 token 部分")));
    }
}
