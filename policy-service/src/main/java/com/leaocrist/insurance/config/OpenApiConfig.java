package com.leaocrist.insurance.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI insurancePlatformOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("Insurance Platform API")
                        .version("1.0.0")
                        .description("API for managing customers, policies, and insurance risks."));
    }
}
