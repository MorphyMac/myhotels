package com.epam.myhotels.hotels.configs;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {

    @Bean
    public Docket getDocket() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("Hotel-Service-API").apiInfo(apiInfo())
                                                      .securityContexts(Collections.singletonList(securityContext()))
                                                      .securitySchemes(Collections.singletonList(apiKey())).select()
                                                      .apis(RequestHandlerSelectors
                                                              .withClassAnnotation(RestController.class))
                                                      .paths(PathSelectors.any()).build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Hotel Service API").description("Service for managing hotels and rooms")
                                   .build();
    }

    private ApiKey apiKey() {
        return new ApiKey("JWT", "Authorization", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Collections.singletonList(new SecurityReference("JWT", authorizationScopes));
    }


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        String swaggerResources = "/swagger-resources";
        registry.addViewController("/docs/v2/api-docs").setViewName("forward:/v2/api-docs");
        registry.addRedirectViewController("/docs/swagger-resources/configuration/ui", swaggerResources +
                "/configuration/ui");
        registry.addRedirectViewController("/docs/swagger-resources/configuration/security", swaggerResources +
                "/configuration/security");
        registry.addRedirectViewController("/docs/swagger-resources", swaggerResources);
        registry.addRedirectViewController("/docs/swagger-resources/configuration/ui", swaggerResources +
                "/configuration/ui");
        registry.addRedirectViewController("/docs", "/docs/swagger-ui.html");
        registry.addRedirectViewController("/docs/", "/docs/swagger-ui.html");
    }
}