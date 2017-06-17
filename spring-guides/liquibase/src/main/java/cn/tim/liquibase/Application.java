package cn.tim.liquibase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by LuoLiBing on 16/6/23.
 * liquibase与flyway有点类似,都是用来做数据库版本控制,数据建模
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
