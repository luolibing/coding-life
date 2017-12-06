package cn.tim.rabbitmq.samples;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

/**
 * Created by LuoLiBing on 16/4/26.
 * rpc 客户端发送请求,接收response
 * client 作为rpc_queue的生产者
 * client 作为reply_queue的消费者
 */
public class RpcClient {

    private Connection connection;
    private Channel channel;

    /**
     * 回复队列名称
     */
    private String replyQueueName;

    /**
     *
     */
    private QueueingConsumer consumer;

    /**
     * 请求统一发送到rpc_queue队列里面
     */
    private final static String requestQueueName = "rpc_queue";

    public RpcClient() throws IOException, TimeoutException {
        // 创建连接
        connection = RabbitConnectionFactory.connection();
        channel = connection.createChannel();

        // 回复队列
        replyQueueName = channel.queueDeclare().getQueue();
        consumer = new QueueingConsumer(channel);

        channel.basicConsume(replyQueueName, true, consumer);
    }

    /**
     * rpc_queue的生产者,reply_to的消费者
     * @param message
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public String call(String message) throws IOException, InterruptedException {
        String response = null;
        String correlationId = UUID.randomUUID().toString();
        AMQP.BasicProperties properties = new AMQP.BasicProperties
                .Builder()
                // 标记具体哪次请求
                .correlationId(correlationId)
                // 发送到哪个replayQueue队列
                .replyTo(replyQueueName)
                .build();
        channel.basicPublish("", requestQueueName, properties, message.getBytes());

        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            if(delivery.getProperties().getCorrelationId().equals(correlationId)) {
                response = new String(delivery.getBody());
                break;
            }
        }
        return response;
    }

    public void release() throws IOException, TimeoutException {
        RabbitConnectionFactory.release(channel, connection);
    }

    public static void main(String[] args) throws Exception {
        RpcClient rpcClient = new RpcClient();
        String response = rpcClient.call("30");
        System.out.println(" [.] Got '" + response + "'");
//        response = rpcClient.call("40");
//        System.out.println(" [.] Got '" + response + "'");
        rpcClient.release();
    }

}
