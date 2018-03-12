package cn.tim.web;

import cn.tim.web.cycle.B1;
import cn.tim.web.entity.Person;
import cn.tim.web.entity.PersonView;
import de.codecentric.boot.admin.config.EnableAdminServer;
import org.jdto.DTOBinder;
import org.jdto.spring.SpringDTOBinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.Random;

/**
 * Created by luolibing on 2017/5/12.
 */
@RestController
@EnableAdminServer
@SpringBootApplication
public class WebApplication implements CommandLineRunner, ApplicationContextAware {

    private final static Logger log = LoggerFactory.getLogger(WebApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

    @Autowired
    private B1 b1;

    private int port = new Random().nextInt(10);

    @GetMapping("/hello")
    public Map<String, Object> hello() {
        //return Collections.singletonMap("hello", "world");
        b1.sayHello();
        return Collections.singletonMap("port", port);
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
        MDC.put("name", "luolibing");
        log.info("run aaaaaaaaaaaaa");
        log.info("bbbbbbbbbbbbbbbbb");
    }

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

//    @GetMapping("/")
//    public String accept(HttpServletRequest request) {
//        System.out.println(request);
//        request.getParameterMap();
//    }

    @Bean
    public DTOBinder dtoBinder() {
        return new SpringDTOBinder();
    }


    @GetMapping("/person/{id}")
    public Object person(@PathVariable Long id) {
        Person person = getPerson(id);
        return dtoBinder().bindFromBusinessObject(PersonView.class, person);
    }

    private Person getPerson(Long id) {
        Person p = new Person();
        p.setId(id);
        p.setFirstName("luo");
        p.setSecondName("libing");
        p.setAge(30);
        return p;
    }
}
