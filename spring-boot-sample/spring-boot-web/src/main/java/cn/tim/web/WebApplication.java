package cn.tim.web;

import cn.tim.web.cycle.B1;
import cn.tim.web.cycle.MyStringToEnumConverterFactory;
import cn.tim.web.cycle.MyType;
import de.codecentric.boot.admin.config.EnableAdminServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.ConverterRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

/**
 * Created by luolibing on 2017/5/12.
 */
@RestController
@RequestMapping("")
@EnableAdminServer
@SpringBootApplication
public class WebApplication implements CommandLineRunner, ApplicationContextAware {

    private final static Logger log = LoggerFactory.getLogger(WebApplication.class);

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

//    @PostMapping("")
//    public Map<String, String> index(@RequestBody Map<String, String> requestBody){
//        return requestBody;
//    }

    @GetMapping("/index")
    public Map<String, String> indexGet(){
        return Collections.singletonMap("success", "get");
    }

    @GetMapping("/myType")
    public Object myType(MyType myType) {
        System.out.println(myType);
        return Collections.singletonMap("success","Y");
    }

    @Bean
    public ConverterRegistry initConverter(ConverterRegistry registry) {
        registry.addConverterFactory(new MyStringToEnumConverterFactory());
        return registry;
    }

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return new EmbeddedServletContainerCustomizer(){
            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {
                container.addErrorPages(new ErrorPage(HttpStatus.BAD_REQUEST, "/400"));
                container.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500"));
                container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/404"));
            }
        };
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
}
