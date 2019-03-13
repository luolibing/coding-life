package com.tim.integraction.router;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

/**
 * Created by luolibing on 2019/3/5.
 */
@EnableIntegration
@SpringBootApplication
public class IntegractionApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(IntegractionApplication.class, args);
    }

    @Autowired
    @Qualifier("routeChannel1")
    private MessageChannel inputChannel;

    @Override
    public void run(String... strings) throws Exception {
        Message<String> message1 = MessageBuilder.withPayload("jack").build();
        inputChannel.send(message1);
        Message<String> message2 = MessageBuilder.withPayload("rose").build();
        inputChannel.send(message2);
    }
}
