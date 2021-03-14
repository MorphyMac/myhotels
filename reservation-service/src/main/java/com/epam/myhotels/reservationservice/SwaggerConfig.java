package com.epam.myhotels.reservationservice;


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
        return new Docket(DocumentationType.SWAGGER_2).groupName("Reservation-Service-API").apiInfo(apiInfo())
                                                      .securityContexts(Collections.singletonList(securityContext()))
                                                      .securitySchemes(Collections.singletonList(apiKey())).select()
                                                      .apis(RequestHandlerSelectors
                                                              .withClassAnnotation(RestController.class))
                                                      .paths(PathSelectors.any()).build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Reservation Service API").description("Service for managing reservations")
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
        registry.addViewController("/docs/v2/api-docs").setViewName("forward:/v2/api-docs");
        registry.addRedirectViewController("/docs/swagger-resources/configuration/ui", "/swagger-resources" +
                "/configuration/ui");
        registry.addRedirectViewController("/docs/swagger-resources/configuration/security", "/swagger-resources" +
                "/configuration/security");
        registry.addRedirectViewController("/docs/swagger-resources", "/swagger-resources");
        registry.addRedirectViewController("/docs/swagger-resources/configuration/ui", "/swagger-resources" +
                "/configuration/ui");
        registry.addRedirectViewController("/docs", "/docs/swagger-ui.html");
        registry.addRedirectViewController("/docs/", "/docs/swagger-ui.html");
    }
}