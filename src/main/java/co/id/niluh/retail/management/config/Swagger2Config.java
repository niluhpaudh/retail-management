package co.id.niluh.retail.management.config;

import co.id.niluh.retail.management.config.prop.ConfigProperties;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@Configuration
@EnableSwagger2
public class Swagger2Config {
    public static final String AUTHORIZATION_OPEN_HEADER = "x-password";
    public static final String USER_OPEN_HEADER = "x-user";
    public static final String AUTHORIZATION_OPEN_API_PATH = "/api/open/.*";
    
    @Autowired
    private ConfigProperties info;

    @Bean
    public Docket api() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiEndPointsInfo())
                .pathMapping("/")
                .forCodeGeneration(true)
                .securityContexts(Lists.newArrayList(securityContext()))
                .securitySchemes(Lists.newArrayList(apiKey(), userKey()))
                .useDefaultResponseMessages(false);

        return docket.select()
                .apis(RequestHandlerSelectors.basePackage("co.id.niluh.retail.management.api.open"))
                .paths(PathSelectors.regex("/api/open/.*"))
                .build().apiInfo(apiEndPointsInfo());
    }

    private ApiKey apiKey() {
        return new ApiKey("KEY", AUTHORIZATION_OPEN_HEADER, "password");
    }

	private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex(AUTHORIZATION_OPEN_API_PATH))
                .build();
    }

    private ApiKey userKey() {
        return new ApiKey("USER", USER_OPEN_HEADER, "header");
    }
    
    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(new SecurityReference("KEY", authorizationScopes), new SecurityReference("USER", authorizationScopes));
    }

    private ApiInfo apiEndPointsInfo() {
        return new ApiInfoBuilder().title("Retail Management")
                .description("Springboot Project Test")
                .contact(new Contact("Niluh Putu A", "-", "niluhpaudh@gmail.com"))
                .version("1.0.0").build();
    }
}