package cn.tim.rabbitmq.samples;

import com.rabbitmq.client.*;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by LuoLiBing on 16/4/18.
 * 任务队列 Task Queues
 * http://www.rabbitmq.com/tutorials/tutorial-two-java.html
 * 1 说明:
 * 将比较耗费时间的任务分发给各个worker
 * task queues 避免立刻执行资源密集型任务,而是由我们来安排任务延后有序执行
 * 我们用一个message来概括一个task任务,并且发送到一个队列里面
 * 一个worker进程运行在后台并且pop出tasks并且最终执行这个任务
 * 当很多worker一起运行时,将共享这个队列,这个概念特别有用尤其是当webapp接收一个简短的http请求还要执行一个复杂的任务时
 *
 * 2 Round-robin dispatching 轮询分发式负载均衡
 *
 * 3 message acknowledgments 消息确认
 * 只有consumer消费完这个消息并且返回ack小心给rabbitmq之后,rabbitmq才将message删除
 * If a consumer dies (its channel is closed, connection is closed, or TCP connection is lost) without sending an ack,
 * RabbitMQ will understand that a message wasn't processed fully and will re-queue it.
 * If there are other consumers online at the same time, it will then quickly redeliver it to another consumer.
 * That way you can be sure that no message is lost, even if the workers occasionally die.
 *
 *
 *
 */
public class WorkQueueSample {

    private final static String QUEUE_NAME = "task";

    /**
     * 发送
     * @throws IOException
     * @throws TimeoutException
     */
    @Test
    public void send() throws IOException, TimeoutException {
        // 1 创建连接,通道
        Connection connection = RabbitConnectionFactory.connection();
        Channel channel = connection.createChannel();

        // 2 定义出队列,如果已经存在则不创建,如果存在且修改其他参数会抛异常
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);

        // 3 发送消息
        String message = getMessage(new String[]{"hello", "world", "luo", "li", "bing"});
        // 使用无名的exchange,消息直接路由到routingKey对应的队列当中
        channel.basicPublish("", "task", null, message.getBytes());

        // 4 回收链接通道
        RabbitConnectionFactory.release(channel, connection);
    }

    /**
     * 发送,队列持久化
     * @throws IOException
     * @throws TimeoutException
     */
    @Test
    public void sendDurable() throws IOException, TimeoutException {
        Boolean durable = true;

        // 1 创建连接,通道
        Connection connection = RabbitConnectionFactory.connection();
        Channel channel = connection.createChannel();

        // 2 加上持久化durable标识,持久化并不能保证不丢数据,还是存在一定的几率在rabbit接收到message,还没来得及持久化的时候奔溃,这样数据就丢了
        // durable并不是十分健壮,需要更健壮的持久化机制,可以参考publisher confirms(发布证实) https://www.rabbitmq.com/confirms.html
        channel.queueDeclare(QUEUE_NAME + 1, durable, false, false, null);

        // 3 发送消息
        String message = getMessage(new String[]{"hello", "world", "luo", "li", "bing"});
        // channel.basicPublish("", "task", null, message.getBytes());
        channel.basicPublish("", "task", MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());

        // 4 回收链接通道
        RabbitConnectionFactory.release(channel, connection);
    }


    /**
     * 接收
     * @throws IOException
     * @throws TimeoutException
     */
    @Test
    public void receive() throws IOException, TimeoutException {

        // 1 创建连接,通道
        Connection connection = RabbitConnectionFactory.connection();
        Channel channel = connection.createChannel();

        // 2 创建出消费者消费
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] received '" + message + "'");
                try {
                    work(message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println(" [x] has Done");
                }
            }
        };
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }

    @Test
    public void receiveAck() throws IOException, TimeoutException {

        // 1 创建连接,通道
        Connection connection = RabbitConnectionFactory.connection();
        Channel channel = connection.createChannel();

        // 2 同一时间只取一个,也可以说在一个worker没有处理完message或者没有acknowledged之前,不发送下一个消息,公正分发(fair dispatch)
        int prefetchCount = 1;
        channel.basicQos(prefetchCount);

        // 3 创建消费者,一旦没有basicAck结果就是不会再继续消费剩下的消息,这个问题是由于channel.basicQos(1);导致的
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] received '" + message + "'");
                try {
                    work(message);
                    int i = 1/0;
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };
        // TODO !!! autoAck有疑问,unack为什么不重新发送给其他的consumer
        channel.basicConsume(QUEUE_NAME, false, consumer);
    }

    private void work(String task) throws InterruptedException {
        for(char ch: task.toCharArray()) {
            if(ch == '.') Thread.sleep(1000);
        }
    }

    private static String getMessage(String[] strings) {
        if(strings.length < 1) {
            return "Hello world!";
        }
        return joinStrings(strings, ".");
    }

    private static String joinStrings(String[] strings, String delimiter) {
        int length = strings.length;
        if (length == 0) return "";
        StringBuilder words = new StringBuilder(strings[0]);
        for (int i = 1; i < length; i++) {
            words.append(delimiter).append(strings[i]);
        }
        return words.toString();
    }

    public static void main(String[] args) throws IOException, TimeoutException {
        // new WorkQueueSample().receive();
        new WorkQueueSample().receiveAck();
    }

}
