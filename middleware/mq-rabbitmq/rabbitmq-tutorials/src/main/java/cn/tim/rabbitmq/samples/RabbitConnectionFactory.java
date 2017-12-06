package cn.tim.rabbitmq.samples;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by LuoLiBing on 16/4/20.
 */
public class RabbitConnectionFactory {

    /**
     * 获取链接
     * @return
     * @throws IOException
     * @throws TimeoutException
     */
    public static Connection connection() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setUsername("admin");
        factory.setPassword("admin");
        factory.setVirtualHost("local");
        return factory.newConnection();
    }

    /**
     * 释放资源
     * @param channel
     * @param connection
     * @throws IOException
     * @throws TimeoutException
     */
    public static void release(Channel channel, Connection connection) throws IOException, TimeoutException {
        channel.close();
        connection.close();
    }
}
