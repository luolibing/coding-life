package cn.tim.rabbitmq.samples;

import com.rabbitmq.client.*;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by LuoLiBing on 16/4/18.
 * http://www.rabbitmq.com/tutorials/tutorial-one-java.html
 * rabbitmq简单应用 hello world!
 */
public class HelloWorldSample {

    private final static String QUEUE_NAME = "hello";

    /**
     * 生产者发送消息
     */
    @Test
    public void send() throws IOException, TimeoutException {
        // 1 获取连接,创建出通道
        Connection connection = RabbitConnectionFactory.connection();
        Channel channel = connection.createChannel();
        // 2 定义出队列queue
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 3 发布消息
        String message = "Hello World " + System.currentTimeMillis();
        // 使用默认的exchange路由
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");
        // 4 释放连接
        RabbitConnectionFactory.release(channel, connection);
    }

    /**
     * 消费者接收,每个消费者都是一个线程,监听着队列是否有消息接收到
     */
    @Test
    public void receive() throws IOException, TimeoutException {
        // 1 获取连接,创建出通道
        Connection connection = RabbitConnectionFactory.connection();
        Channel channel = connection.createChannel();
        // 2 定义出队列queue
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 3 定义出消费者
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                String message = new String(body, "UTF-8");
                System.out.println(" [x] received '" + message + "'");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        // 队列名,ack自动确认
        channel.basicConsume(QUEUE_NAME, true, consumer);
        // 不能释放掉channel和connection
        //release(channel, connection);
    }

    public static void main(String[] args) throws IOException, TimeoutException {
        new HelloWorldSample().receive();
    }
}
