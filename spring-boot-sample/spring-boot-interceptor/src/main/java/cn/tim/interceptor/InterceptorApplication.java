package cn.tim.interceptor;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * User: luolibing
 * Date: 2017/8/3 14:09
 */
@Aspect
@EnableAspectJAutoProxy(exposeProxy = true)
@SpringBootApplication
@EnableScheduling
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

    @Override
    public void run(String... args) throws Exception {
        service.sayGood();

//        LongStream.range(0, 500).forEach(i -> gLockService.lockTest(i));
    }
}
