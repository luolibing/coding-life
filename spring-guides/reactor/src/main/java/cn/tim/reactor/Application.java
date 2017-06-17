package cn.tim.reactor;

import cn.tim.reactor.publisher.Publisher;
import cn.tim.reactor.receiver.Receiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import reactor.Environment;
import reactor.bus.EventBus;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static reactor.bus.selector.Selectors.$;

/**
 * Created by LuoLiBing on 15/10/26.
 */
@SpringBootApplication
public class Application implements CommandLineRunner {

    private final static int NUMBER_OF_QUOTES = 10;

    @Bean
    Environment environment() {
        return Environment.initializeIfEmpty().assignErrorJournal();
    }

    @Bean
    EventBus createEventBus(Environment env) {
        return EventBus.create(env, Environment.THREAD_POOL);
    }

    @Autowired
    private EventBus eventBus;

    @Autowired
    private Receiver receiver;

    @Autowired
    private Publisher publisher;

    @Bean
    public CountDownLatch countDownLatch() {
        return new CountDownLatch(NUMBER_OF_QUOTES);
    }

    public static void main(String[] args) throws InterruptedException {
        ApplicationContext app = SpringApplication.run(Application.class, args);
        app.getBean(CountDownLatch.class).await(1, TimeUnit.SECONDS);
        app.getBean(Environment.class).shutdown();
    }

    @Override
    public void run(String... args) throws Exception {
        // 注册消费者
        eventBus.on($("quotes"), receiver);
        // 发布事件
        publisher.publishQuotes(NUMBER_OF_QUOTES);
    }
}
