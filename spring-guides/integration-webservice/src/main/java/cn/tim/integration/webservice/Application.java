package cn.tim.integration.webservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import java.io.IOException;

/**
 * Created by LuoLiBing on 16/1/22.
 */
@Configuration
@SpringBootApplication
@ImportResource("integration.xml")
public class Application {

    public static void main(String[] args) throws IOException {
        final ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);
        System.out.println("Hit Enter to terminate");
        System.in.read();
        ctx.close();
    }
}
