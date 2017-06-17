package cn.tim.rabbitmq.samples;

import com.rabbitmq.client.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeoutException;

/**
 * Created by LuoLiBing on 16/4/23.
 * direct exchange 直接路由
 *
 */
public class DirectExchangeSample {

    private final static Logger logger = LoggerFactory.getLogger(DirectExchangeSample.class);

    private final static String EXCHANGE_NAME = "direct";

    private final static String[] severities = new String[] {"info", "error", "warn"};

    @Test
    public void send() throws IOException, TimeoutException {
        // 1 连接创建通道
        Connection connection = RabbitConnectionFactory.connection();
        Channel channel = connection.createChannel();

        // 2 定义一个direct exchange
        channel.exchangeDeclare(EXCHANGE_NAME, ExchangeTypes.DIRECT);

        // 3 发布使用对应的routing key,不同的严重程度
        String message = "HELLO! Direct!!!" + System.currentTimeMillis();
        channel.basicPublish(EXCHANGE_NAME, getSeverity(), null, message.getBytes());

        System.out.println(" [x] sent '" + message + "'");

        // 4 释放资源
        RabbitConnectionFactory.release(channel, connection);
    }

    public DirectExchangeSample basicReceive() throws IOException, TimeoutException {
        // 1 连接创建通道
        Connection connection = RabbitConnectionFactory.connection();
        Channel channel = connection.createChannel();

        // 2 定义一个direct exchange
        channel.exchangeDeclare(EXCHANGE_NAME, ExchangeTypes.DIRECT);

        // 3 创建出个临时queue
        String queueName = channel.queueDeclare().getQueue();
        for(String severity: severities) {
            channel.queueBind(queueName, EXCHANGE_NAME, severity);
        }

        System.out.println(" waiting for message...");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                logger.info(" [x] Received '" + message + "'");
            }
        };
        channel.basicConsume(queueName, true, consumer);
        return this;
    }


    public DirectExchangeSample errorReceive() throws IOException, TimeoutException {
        // 1 连接创建通道
        Connection connection = RabbitConnectionFactory.connection();
        Channel channel = connection.createChannel();

        // 2 定义一个direct exchange
        channel.exchangeDeclare(EXCHANGE_NAME, ExchangeTypes.DIRECT);

        // 3 创建出个临时queue
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, "error");

        System.out.println(" waiting for message...");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                logger.error(" [x] Received '" + message + "'");
            }
        };
        channel.basicConsume(queueName, true, consumer);
        return this;
    }


    private String getSeverity() {
        return severities[new Random().nextInt(3)];
    }


    public static void main(String[] args) throws IOException, TimeoutException {
        new DirectExchangeSample().basicReceive().errorReceive();
    }
}
