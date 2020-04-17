/*
package kz.alfabank.frosys.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("kz.alfabank.frosys"))
                .build();
    }

}*/

package kz.frosys.kapp.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${appBaseName}")
    private String appBasePath;

    private static final Logger LOG = LoggerFactory.getLogger(SwaggerConfig.class);

    private static final Contact DEFAULT_CONTACT = new Contact("Alfa-bank", "https://alfa-bank.kz", "katymtayev@alfabank.kz");

    private static final ApiInfo DEFAULT_API_INFO = new ApiInfo(
            "Api for HRR process", "Апи для HRR процесс", "1.0",
            "urn:tos", DEFAULT_CONTACT,
            "GNU AGPLv3", "https://www.gnu.org/licenses/agpl-3.0.ru.html", Collections.emptyList());

    private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES = new HashSet<>(Arrays.asList("application/json"));


    @Bean
    public Docket api() {
        LOG.debug("Starting Swagger Api Doc");
        StopWatch watch = new StopWatch();
        watch.start();
        Docket docket= new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(DEFAULT_API_INFO)
                .produces(DEFAULT_PRODUCES_AND_CONSUMES)
                .consumes(DEFAULT_PRODUCES_AND_CONSUMES)
                .forCodeGeneration(true)
                //.securitySchemes(Collections.singletonList(new BasicAuth("Basic Authorization")))
//                .securityContexts(Collections.singletonList(SecurityContext.builder()
//                        .securityReferences(
//                                Collections.singletonList(SecurityReference.builder()
//                                        .reference("Basic Authorization")
//                                        .scopes(new AuthorizationScope[]{new AuthorizationScope("", "Every call to the API is secured with a basic authorization token.")})
//                                        .build())).build()))
                .genericModelSubstitutes(ResponseEntity.class, Optional.class, CompletableFuture.class)
                .ignoredParameterTypes(java.sql.Date.class)
                .directModelSubstitute(java.time.LocalDate.class, java.sql.Date.class)
                .directModelSubstitute(java.time.ZonedDateTime.class, Date.class)
                .directModelSubstitute(java.time.LocalDateTime.class, Date.class)
                .select()
                //.apis(RequestHandlerSelectors.basePackage(AppConstants.REST_BASE_PACKAGE))
                //.paths(PathSelectors.ant(DEFAULT_INCLUDE_PATTERN))
                .apis(RequestHandlerSelectors.basePackage("kz.frosys.kapp"))
                .paths(PathSelectors.any())
//      .paths(Predicates.not(PathSelectors.regex("/error")))
                .build()
                ;

        if (appBasePath != null && !appBasePath.isEmpty()) {
            docket.pathMapping(appBasePath);
        }

        watch.stop();
        LOG.debug("Started Swagger in {} ms", watch.getTotalTimeMillis());
        return docket;
    }
}
