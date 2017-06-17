package cn.tim.mongo.simple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by LuoLiBing on 16/6/15.
 */
@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {

    }
}
