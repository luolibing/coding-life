package cn.tim.rabbitmq.samples;

import com.rabbitmq.client.*;
import org.junit.Test;
import org.springframework.amqp.core.ExchangeTypes;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeoutException;

/**
 * Created by LuoLiBing on 16/4/25.
 * topic模式
 * http://www.rabbitmq.com/tutorials/tutorial-five-java.html
 *
 * 与redirect类似,不同的是topic可以根据多个条件过滤,有点类似于命名空间 cn.tim.mall.order.logs.error
 * 然后我们可以用 * 和 # 进行匹配
 * * 匹配一个元素
 * # 匹配任意个
 *
 * 于是我们可以用#来模拟fanout的功能
 */
public class TopicSample {

    private final static String EXCHANGE_NAME = "topic_logs";

    private final static String[] routingKeys = new String[]{
            "quick.orange.rabbit",
            "lazy.yellow.dog",
            "stock.red.ship",
            "quick.white.rabbit",
            "lazy.pink.rabbit",
            "quick.brown.fox",
            "lazy.orange.male.rabbit"
    };

    /**
     * 发布消息,与之前的都类似,只是换了一个exchange而已,
     * 从这里可以看出生产者只需要知道往哪个exchange里面发,至于exchange怎么工作不需要关心
     * @throws IOException
     * @throws TimeoutException
     */
    @Test
    public void send() throws IOException, TimeoutException {

        // 1 新建连接通道
        Connection connection = RabbitConnectionFactory.connection();
        Channel channel = connection.createChannel();

        // 2 定义一个topic_logs的topic路由器
        channel.exchangeDeclare(EXCHANGE_NAME, ExchangeTypes.TOPIC);

        // 3 发布消息
        String routingKey = getRouting();
        String message = "hello world " + routingKey + " -> " + System.currentTimeMillis();
        channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes());
        System.out.println("[x] " + message + " has send!!!");

        // 4 释放连接
        RabbitConnectionFactory.release(channel, connection);
    }

    /**
     * 随机路由
     * @return
     */
    private String getRouting() {
        return routingKeys[new Random().nextInt(routingKeys.length)];
    }


    /**
     * 接收
     * @param bindingKeys
     * @throws IOException
     * @throws TimeoutException
     */
    public void receive(String[] bindingKeys) throws IOException, TimeoutException {

        // 1 新建连接通道
        Connection connection = RabbitConnectionFactory.connection();
        Channel channel = connection.createChannel();

        // 2 定义exchange
        channel.exchangeDeclare(EXCHANGE_NAME, ExchangeTypes.TOPIC);

        // 3 定义队列,绑定routingKey,表示这个队列对哪些routingKey感兴趣
        String queueName = channel.queueDeclare().getQueue();
        for(String bindingKey : bindingKeys) {
            channel.queueBind(queueName, EXCHANGE_NAME, bindingKey);
        }

        // 4 定义消费者,发布
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" {" + queueName + "} Received '" + envelope.getRoutingKey() + "':'" + message + "'");
            }
        };
        channel.basicConsume(queueName, true, consumer);
    }

    public static void main(String[] args) throws IOException, TimeoutException {
        TopicSample topic = new TopicSample();
        topic.receive(new String[] {"#"});
        topic.receive(new String[] {"#.rabbit"});
        topic.receive(new String[] {"quick.#", "*.orange.*"});
    }

}
