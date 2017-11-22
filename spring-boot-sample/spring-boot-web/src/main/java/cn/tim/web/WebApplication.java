package cn.tim.web;

import cn.tim.web.cycle.B1;
import de.codecentric.boot.admin.config.EnableAdminServer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by luolibing on 2017/5/12.
 */
@RestController
@EnableAdminServer
@SpringBootApplication
public class WebApplication implements CommandLineRunner, ApplicationContextAware {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

    @Autowired
    private B1 b1;

    @GetMapping("/hello")
    public Map<String, Object> hello() {
        //return Collections.singletonMap("hello", "world");
        b1.sayHello();
        throw new RuntimeException("aaaaaaaaaaaaa");
    }

    @PostMapping("/hello")
    public Object hello1(@RequestBody Map<String, Object> body) {
        return body;
    }

    @Autowired
    private Hello hello;

    @Override
    public void run(String... strings) throws Exception {
        hello.say();
        Child child = applicationContext.getBean(Child.class);
        child.handle();
    }

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @GetMapping("/")
    public void accept(HttpServletRequest request) {
        System.out.println(request);
        request.getParameterMap();
    }
}
