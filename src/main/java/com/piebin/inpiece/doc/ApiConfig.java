package com.piebin.inpiece.doc;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(servers = {@Server(url = "/")})
public class ApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("InPiece Backend")
                        .description("Spring Boot API")
                        .version("v0.0.1"));
    }

    // API
    @Bean
    public GroupedOpenApi defaultAPI() {
        return GroupedOpenApi.builder()
                .group("Default API")
                .pathsToMatch("/**")
                .build();
    }

    @Bean
    public GroupedOpenApi accountAPI() {
        return GroupedOpenApi.builder()
                .group("Account API")
                .pathsToMatch("/api/account/**")
                .build();
    }

    @Bean
    public GroupedOpenApi contestAPI() {
        return GroupedOpenApi.builder()
                .group("Contest API")
                .pathsToMatch("/api/contest/**")
                .build();
    }

    @Bean
    public GroupedOpenApi teamAPI() {
        return GroupedOpenApi.builder()
                .group("Team API")
                .pathsToMatch("/api/team/**")
                .build();
    }

    @Bean
    public GroupedOpenApi teamRecruitAPI() {
        return GroupedOpenApi.builder()
                .group("Team Recruit API")
                .pathsToMatch("/api/team/recruit/**")
                .build();
    }

    @Bean
    public GroupedOpenApi teamProjectAPI() {
        return GroupedOpenApi.builder()
                .group("Team Project API")
                .pathsToMatch("/api/team/project/**")
                .build();
    }
}
