package in.anil.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.function.Predicate;

import static springfox.documentation.builders.PathSelectors.regex;

/**
 *
 * Created by ah00554631 on 6/5/2018.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket apiInf0(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("home-api")
                .select()
                .apis(RequestHandlerSelectors.basePackage("in.anil.webapp.controller"))
                .paths(regex("/home.*"))
//                .paths(regex("/users.*"))
                .build()
                .apiInfo(metaData());
    }

    @Bean
    public Docket apiUserInf0(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("user-api")
                .select()
                .apis(RequestHandlerSelectors.basePackage("in.anil.webapp.controller"))
                .paths(regex("/users.*"))
                .build()
                .apiInfo(metaData());
    }

    @Bean
    public Docket apiFileInf0(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("file-api")
                .select()
                .apis(RequestHandlerSelectors.basePackage("in.anil.webapp.controller"))
                .paths(regex("/files.*"))
                .build()
                .apiInfo(metaData());
    }

    private ApiInfo metaData() {
        ApiInfo apiInfo = new ApiInfo(
                "Spring Boot REST API",
                "Spring Boot REST API for Homes",
                "1.0",
                "Terms of service",
                new Contact("Anil Kumar", "https://springframework.anil/about/", "anilkumarhv04@gmail.com"),
                "Apache License Version 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0",new ArrayList<>());
        return apiInfo;
    }
}
