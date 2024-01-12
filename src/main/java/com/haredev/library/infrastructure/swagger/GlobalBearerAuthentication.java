package com.haredev.library.infrastructure.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class GlobalBearerAuthentication {

    @Bean
    public OpenAPI openApiGlobalBearerAuthentication() {
        final String securitySchemeName = "Bearer Authentication";
        return new OpenAPI().addSecurityItem(new SecurityRequirement().
                        addList(securitySchemeName))
                .components(new Components().addSecuritySchemes
                        (securitySchemeName, createAPIKeyScheme()));
    }

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme()
                .name("Bearer Authentication")
                .type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }
}
