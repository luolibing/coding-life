package cn.tim.multiconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

/**
 * Created by LuoLiBing on 16/3/22.
 * service multiconfig start方式启动
 * spring boot 启动方式是在jar包同目录下,配置了一个*.conf,多配置
 * JAVA_OPTS="$JAVA_OPTS -Dspring.profiles.active=sandbox"
 * export JAVA_OPTS
 *
 */
@RestController
@SpringBootApplication
public class Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    private Config config;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(config.getTests());
    }

    @RequestMapping(value = "/index")
    public Object index()  {
        return Collections.singletonMap("main", "index");
    }
}
