package cn.timedu.log4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;

/**
 * Created by LuoLiBing on 16/1/13.
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public SecurityProperties securityProperties() {
        SecurityProperties security = new SecurityProperties();
        security.getBasic().setPath(""); // empty so home page is insecured
        return security;
    }
}
