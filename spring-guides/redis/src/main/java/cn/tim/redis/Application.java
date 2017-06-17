package cn.tim.redis;

import cn.tim.redis.receiver.Receiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * Created by LuoLiBing on 15/11/9.
 * 通过redis发送消息与接收消息
 */
@SpringBootApplication
public class Application {

    private final static Logger logger = LoggerFactory.getLogger(Application.class);

    /**
     * redisMessage 监听容器
     * @param connectionFactory
     * @param listenerAdapter
     * @return
     */
    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                            MessageListenerAdapter listenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        // 添加监听适配器，主题匹配大量通道，这是将消息适配到Receiver
        container.addMessageListener(listenerAdapter, new PatternTopic("chat"));
        return container;
    }

    /**
     * 消息适配器，添加接收者Receiver
     * @param receiver
     * @return
     */
    @Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver) {
        // 默认调用的方法名位 receiveMessage
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

    @Bean
    Receiver receiver(CountDownLatch latch) {
        return new Receiver(latch);
    }

    @Bean
    CountDownLatch latch() {
        return new CountDownLatch(1);
    }


    /**
     * redis模板
     * @param connectionFactory
     * @return
     */
    @Bean
    StringRedisTemplate template(RedisConnectionFactory connectionFactory) {
        return new StringRedisTemplate(connectionFactory);
    }

    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);
        StringRedisTemplate template = ctx.getBean(StringRedisTemplate.class);
        CountDownLatch latch = ctx.getBean(CountDownLatch.class);

        logger.info("Sending message...");
        // 发送消息到chat当中
        Map<String, String> message = new HashMap<>();
        message.put("message", "hello!");
        message.put("date", "20151111");
        template.convertAndSend("onekey", message);

        latch.await();

        System.exit(0);
    }
}
