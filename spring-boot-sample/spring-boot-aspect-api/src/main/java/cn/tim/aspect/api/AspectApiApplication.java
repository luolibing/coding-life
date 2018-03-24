package cn.tim.aspect.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * User: luolibing
 * Date: 2018/3/24 18:12
 */
@EnableAspectJAutoProxy(proxyTargetClass = true)
@SpringBootApplication
public class AspectApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AspectApiApplication.class, args);
    }
}
