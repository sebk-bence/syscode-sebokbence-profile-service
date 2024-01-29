package com.sc.sebokbence.scprofileservice.config;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.*;
import io.swagger.v3.oas.models.servers.*;
import org.springframework.context.annotation.*;

@Configuration
public class OpenApiConfig {
  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI()
        .info(
            new Info()
                .title("Profile API")
                .description("SysCode Interview Profile API - Sebők Bence")
                .version("v0.0.1")
        )
        .addServersItem(
            new Server()
                .url("http://localhost:8080")
                .description("Localhost")
        );
  }
}
