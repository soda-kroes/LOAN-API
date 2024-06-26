package com.java.year3.loan_api.config;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2 // Enable Swagger
public class SwaggerConfiguration {
    @Value("${apiTitle}")
    private String apiTitle;

    @Value("${apiDescription}")
    private String apiDescription;

    @Value("${apiVersion}")
    private String apiVersion;

    @Value("${apiContactName}")
    private String apiContactName;

    @Value("${apiContactEmail}")
    private String apiContactEmail;

    @Value("${apiContactUrl}")
    private String apiContactUrl;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.java.year3.loan_api")) // Specify your base package
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .globalOperationParameters(Collections.singletonList(jwtHeaderParameter()));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(apiTitle)
                .description(apiDescription)
                .version(apiVersion)
                .contact(new Contact(apiContactName, apiContactUrl, apiContactEmail))
                .build();
    }

    private Parameter jwtHeaderParameter() {
        return new ParameterBuilder()
                .name("Authorization")
                .description("JWT authentication token")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false) // Set to true if the header is required
                .defaultValue("Bearer")
                .build();
    }
}