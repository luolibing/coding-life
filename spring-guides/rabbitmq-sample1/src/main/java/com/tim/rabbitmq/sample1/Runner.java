package com.tim.rabbitmq.sample1;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Created by luolibing on 2017/6/16.
 */
@Component
public class Runner implements CommandLineRunner {

    private final RabbitTemplate rabbitTemplate;
    private final Receiver receiver;
    private final ConfigurableApplicationContext context;

    public Runner(Receiver receiver, RabbitTemplate rabbitTemplate,
                  ConfigurableApplicationContext context) {
        this.receiver = receiver;
        this.rabbitTemplate = rabbitTemplate;
        this.context = context;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Sending message...");
        for(int i = 0; i < 10000; i++) {
            rabbitTemplate.convertAndSend(Application.queueName, "Hello from RabbitMQ!");
//            receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
            TimeUnit.MILLISECONDS.sleep(50);
        }
        context.close();
    }

}