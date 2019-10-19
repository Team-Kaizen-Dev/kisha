package com.teamkaizen.kisha.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Michael Ryan A. Paclibar <michael@satellite.com.ph>
 */
@EnableSwagger2
@Profile({"test"})
@Controller
public class SwaggerConfig {

    @RequestMapping("/")
    public String redirectSwaggerMainPage() {
        return "redirect:/swagger-ui.html";
    }

    @Bean
    public Docket documentation() {
        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex("/api/.*")).build().pathMapping("/")
                .apiInfo(metadata());
    }

    private ApiInfo metadata() {
        return new ApiInfoBuilder()
                .title("Kisha Web Service API")
                .description("Use this documentation as a reference how to interact with app's API")
                .build();
    }

}
