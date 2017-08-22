package cn.tim.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * User: luolibing
 * Date: 2017/8/3 14:09
 */
@Aspect
@EnableAspectJAutoProxy(exposeProxy = true)
@SpringBootApplication
@EnableScheduling
@RestController
public class InterceptorApplication implements CommandLineRunner {

    @AfterReturning(value = "@annotation(Handler)")
    public void proxy() {
        System.out.println("proxy handler");
    }

    public static void main(String[] args) {
        SpringApplication.run(InterceptorApplication.class, args);
    }

    @Autowired
    private Service service;

    @Autowired
    private GLockService gLockService;

    @GetMapping(value = "/jsonDto")
    public JsonDto getOne() {
        JsonDto jsonDto = new JsonDto();
        jsonDto.setPercent(new BigDecimal(0.75));
        return jsonDto;
    }

    @Override
    public void run(String... args) throws Exception {
        JsonDto jsonDto = new JsonDto();
        jsonDto.setPercent(new BigDecimal(0.75));
        String s = new ObjectMapper().writeValueAsString(jsonDto);
        System.out.println(s);
        service.sayGood();
    }
}
