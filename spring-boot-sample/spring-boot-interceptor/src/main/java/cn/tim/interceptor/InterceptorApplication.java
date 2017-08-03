package cn.tim.interceptor;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * User: luolibing
 * Date: 2017/8/3 14:09
 */
@Aspect
@EnableAspectJAutoProxy(exposeProxy = true)
@SpringBootApplication
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

    @Override
    public void run(String... args) throws Exception {
        service.sayGood();
    }
}
