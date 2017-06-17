package cn.tim.integration.http;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.integration.config.EnableIntegration;

/**
 * Created by LuoLiBing on 16/1/25.
 * 系统整合,通过http整合
 * 省略了controller层,响应与请求都通过管道进行传输
 *
 */
@SpringBootApplication
@EnableIntegration
@ImportResource("integration-http-int.xml")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
