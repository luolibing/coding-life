package cn.tim.swagger;

import com.google.common.collect.Sets;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * User: luolibing
 * Date: 2017/6/2 15:40
 */
@SpringBootApplication
@Configuration
@EnableSwagger2
public class SwaggerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SwaggerApplication.class, args);
    }

    @Bean
    public Docket petApi() {

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build()
                .consumes(Sets.newHashSet("application/json"))
                .produces(Sets.newHashSet("application/json"))
                .pathMapping("/")
                .genericModelSubstitutes(ResponseEntity.class)
                .useDefaultResponseMessages(false)
                .enableUrlTemplating(true)
                ;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("REST API")
                .description("前后端分离的协议在这里定义")
                .termsOfServiceUrl("http://localhost:8080")
                .version("1.0")
                .build();
    }
}
