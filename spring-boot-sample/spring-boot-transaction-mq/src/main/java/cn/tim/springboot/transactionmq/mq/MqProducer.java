package cn.tim.springboot.transactionmq.mq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by luolibing on 2017/6/18.
 */
@Component
public class MqProducer {

    private final RabbitTemplate rabbitTemplate;

    public MqProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public <T> void send(String queueName, T t) throws Exception {

        System.out.println("Sending message...");
        rabbitTemplate.convertAndSend(queueName, t);
    }
}
