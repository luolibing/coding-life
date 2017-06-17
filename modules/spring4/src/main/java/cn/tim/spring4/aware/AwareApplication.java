package cn.tim.spring4.aware;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by LuoLiBing on 16/6/17.
 */
@SpringBootApplication
public class AwareApplication implements CommandLineRunner {

    @Autowired
    ApplicationEventPublisherAwareTest applicationEventPublisherAwareTest;

    @Autowired
    NotificationPublisherAwareTest notificationPublisherAwareTest;

    public static void main(String[] args) {
        SpringApplication.run(AwareApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        applicationEventPublisherAwareTest.notifyMessage();
        //notificationPublisherAwareTest.publishJmx();
    }
}
