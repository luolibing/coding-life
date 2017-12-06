package cn.tim.rabbitmq.samples;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;

/**
 * Created by LuoLiBing on 16/4/26.
 * Rpc 服务端
 * 作为rpc_queue的消费者
 * 作为reply_queue的生产者
 */
public class RpcServer {

    private static final String RPC_QUEUE_NAME = "rpc_queue";

    /**
     * 先消费再生产
     * @throws Exception
     */
    public void handle() throws Exception {
        Connection connection = RabbitConnectionFactory.connection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(RPC_QUEUE_NAME, false, false, false, null);
        // 同时只能取1个,这个消费完才能下一个消费
        channel.basicQos(1);

        // 消费rpc_queue队列请求
        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(RPC_QUEUE_NAME, false, consumer);
        System.out.println(" [x] awaiting RPC requests");

        while (true) {
            // 获取下一个投递请求
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            AMQP.BasicProperties props = delivery.getProperties();
            AMQP.BasicProperties replyProperties = new AMQP.BasicProperties
                    .Builder()
                    .correlationId(props.getCorrelationId())
                    .build();
            String message = new String(delivery.getBody());
            System.out.println(" [x] get request message=['" + message + "']");

            // 处理请求
            int n = Integer.parseInt(message);
            String response = Fibonacci.fib(n) + "";

            // 返回结果
            channel.basicPublish("", props.getReplyTo(), replyProperties, response.getBytes());

            // 确认消费收到的请求
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }
    }

    public static void main(String[] args) throws Exception {
        new RpcServer().handle();
    }
}
