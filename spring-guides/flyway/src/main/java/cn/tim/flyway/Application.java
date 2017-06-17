package cn.tim.flyway;

import cn.tim.flyway.entity.jpa.PersonJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by LuoLiBing on 16/1/12.
 * 数据库版本控制,使用/resources/db/migration/V1_init.sql,采用追加的方式
 */
@SpringBootApplication
public class Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    PersonJpaRepository personJpaRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("count=" + personJpaRepository.count());
    }
}
