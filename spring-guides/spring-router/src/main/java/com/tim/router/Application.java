package com.tim.router;

import com.tim.router.biz.BizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import javax.annotation.Resource;

@EnableAspectJAutoProxy
@SpringBootApplication
public class Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @Resource(name = "aService")
    private BizService aService;

    @Override
    public void run(String... strings) throws Exception {
        aService.work("hahah");
    }
}
