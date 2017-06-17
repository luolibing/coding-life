package cn.tim.schedu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by LuoLiBing on 15/9/29.
 */
@SpringBootApplication
// springboot加上这个注解，阻塞住程序，不然只跑一次就会退出程序
@EnableScheduling
public class Application {

    private final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @Scheduled(fixedDelay = 5000)
    public void tick() {
        System.out.println("The time is now " + df.format(new Date()));
    }

    //@Scheduled(cron = "")
    public void tick1() {
        System.out.println("The time is now " + df.format(new Date()));
    }
}
