package com.github.vskrahul.moviecatalogservice.config;

import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.StringVendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket swaggerConfiguration() {
        return new Docket(DocumentationType.SWAGGER_2)
                    .select()
                    .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                    .build()
                    .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo("movie-catalog-service"
                ,"Movie Catalog Service API"
                ,"v1"
                ,"Terms of Service"
                ,new Contact("Rahul Vishvakarma", "https://github.com/vskrahul", "vsk.rahul@gmail.com")
                ,"MIT License"
                ,"https://opensource.org/licenses/MIT"
                , Lists.newArrayList(new StringVendorExtension("MIT", "MIT"))
        );
    }
}
