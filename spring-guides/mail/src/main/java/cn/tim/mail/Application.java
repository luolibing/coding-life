package cn.tim.mail;

import cn.tim.mail.service.SendMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by LuoLiBing on 16/8/31.
 */
@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private SendMailService sendMailService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        sendMailService.sendByTemplate();
    }


}
