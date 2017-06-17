package cn.tim.demo1.endpoint;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by LuoLiBing on 15/10/21.
 * 端点endpoint
 */
public abstract class EndPoint {


    /**
     * 信道，为会话提供物理传输介质
     */
    protected Channel channel;


    /**
     * 连接，网络连接
     */
    protected Connection connection;


    /**
     * 端点名称
     */
    protected String endPointName;


    public EndPoint(String endpointName) throws IOException, TimeoutException {
        this.endPointName = endpointName;

        // 连接工场
        ConnectionFactory factory = new ConnectionFactory();

        // 主机
        factory.setHost("127.0.0.1");

        //getting a connection
        connection = factory.newConnection();

        //creating a channel
        channel = connection.createChannel();

        // 信道队列定义，在rabbitServer中创建出消息队列
        channel.queueDeclare(endpointName, false, false, false, null);
    }


    /**
     * 关闭channel和connection。并非必须，因为隐含是自动调用的。
     * @throws IOException
     */
    public void close() throws IOException, TimeoutException {
        this.channel.close();
        this.connection.close();
    }
}
