package cn.tim;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by TIM on 2015/8/17.
 */
@SpringBootApplication
//@ImportResource("classpath*:META-INF/spring.xml")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
