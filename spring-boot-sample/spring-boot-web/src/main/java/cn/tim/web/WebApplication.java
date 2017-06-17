package cn.tim.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by luolibing on 2017/5/12.
 */
@RestController
@SpringBootApplication
public class WebApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

    @GetMapping("/hello")
    public Map<String, Object> hello() {
        //return Collections.singletonMap("hello", "world");
        throw new RuntimeException("aaaaaaaaaaaaa");
    }

    @Autowired
    private Hello hello;

    @Override
    public void run(String... strings) throws Exception {
        hello.say();
    }
}
