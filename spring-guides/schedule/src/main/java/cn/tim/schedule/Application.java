package cn.tim.schedule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Created by LuoLiBing on 16/11/21.
 */
@EnableScheduling
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        String[] beans = context.getBeanDefinitionNames();
        for(String bean : beans) {
            System.out.println(bean);
        }
    }

    @Scheduled(cron = "0/10 * * * * *")
    public void schedule() {
        System.out.println("execute");
    }
}
