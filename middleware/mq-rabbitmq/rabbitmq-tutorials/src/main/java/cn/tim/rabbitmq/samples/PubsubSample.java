package cn.tim.rabbitmq.samples;

import com.rabbitmq.client.*;
import org.junit.Test;
import org.springframework.amqp.core.ExchangeTypes;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by LuoLiBing on 16/4/21.
 * 发布与订阅模式: producer并不直接把message发送给queue,而是先发给exchange,由exchange来决定发送给那个队列
 * Instead, the producer can only send messages to an exchange.
 * An exchange is a very simple thing. On one side it receives messages from producers and the other side it pushes them to queues.
 * The exchange must know exactly what to do with a message it receives.
 * Should it be appended to a particular queue? Should it be appended to many queues?
 * Or should it get discarded. The rules for that are defined by the exchange type.
 *
 * 可用的exchange模式有: direct,topic,headers,fanout模式
 *
 */
public class PubsubSample {

    private final static String EXCHANGE_NAME = "logs";

    @Test
    public void send() throws IOException, TimeoutException {
        // 1 连接创建通道
        Connection connection = RabbitConnectionFactory.connection();
        Channel channel = connection.createChannel();

        // 2 定义一个exchange
        channel.exchangeDeclare(EXCHANGE_NAME, ExchangeTypes.FANOUT);

        // 3 发布
        String message = "hello! luo li bing" + System.currentTimeMillis();
        channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());

        System.out.println(" [x] sent '" + message + "'");

        // 4 释放资源
        RabbitConnectionFactory.release(channel, connection);

    }


    public void receiveLogs() throws IOException, TimeoutException {
        // 1 连接创建通道
        Connection connection = RabbitConnectionFactory.connection();
        Channel channel = connection.createChannel();

        // 2 定义一个exchange
        channel.exchangeDeclare(EXCHANGE_NAME, ExchangeTypes.FANOUT);

        // 3 创建一个临时的queue,没有任何参数的 non-durable,exclusive,autoDelete queue; 不持久化,独有的,自动删除的,随机的queueName的临时queue
        // 生产者并不需要关心这个消息会发送到那个队列queue,只需要将消息发到exchange中即可
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, "");

        System.out.println(" waiting for message...");

        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
            }
        };
        channel.basicConsume(queueName, true, consumer);
    }


    /**
     * 两个不同的订阅者
     * 生产者只需要往exchange发送message,然后exchange来决定该发给谁
     * @throws IOException
     * @throws TimeoutException
     */
    public void printReceivedLogs() throws IOException, TimeoutException {
        // 1 连接创建通道
        Connection connection = RabbitConnectionFactory.connection();
        Channel channel = connection.createChannel();

        // 2 定义一个exchange
        channel.exchangeDeclare(EXCHANGE_NAME, ExchangeTypes.FANOUT);

        // 3 创建一个临时的queue,没有任何参数的 non-durable,exclusive,autoDelete queue; 不持久化,独有的,自动删除的,随机的queueName的临时queue
        // 生产者并不需要关心这个消息会发送到那个队列queue,只需要将消息发到exchange中即可
        // 发送消息的时候,exchange会将消息发送给他所有的订阅者
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, "");

        System.out.println(" waiting for message...");

        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received xxxx'" + message + "'");
            }
        };
        channel.basicConsume(queueName, true, consumer);
    }


    public static void main(String[] args) throws IOException, TimeoutException {
        final PubsubSample pubsubSample = new PubsubSample();
        pubsubSample.printReceivedLogs();
        pubsubSample.receiveLogs();
    }

}
