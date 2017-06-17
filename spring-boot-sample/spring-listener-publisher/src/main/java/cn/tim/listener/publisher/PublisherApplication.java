package cn.tim.listener.publisher;

import cn.tim.listener.publisher.entity.Message;
import cn.tim.listener.publisher.event.CreateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.event.ApplicationEventMulticaster;

/**
 * Created by luolibing on 2017/4/12.
 * 默认的applicationPublisher是同步方式，所有listener使用的都是主线程，只要其中一个listener出现异常会阻断所有的listener.
 * 而且效率很低下。
 *
 * https://www.keyup.eu/en/blog/101-synchronous-and-asynchronous-spring-events-in-one-application
 */
@SpringBootApplication
public class PublisherApplication implements CommandLineRunner, ApplicationEventPublisherAware {

    public static void main(String[] args) {
        SpringApplication.run(PublisherApplication.class, args);
    }

    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private ApplicationEventMulticaster applicationEventMulticaster;

    @Override
    public void run(String... strings) throws Exception {
        System.out.println("Main Thread id = " + Thread.currentThread().getId());
//        for(int i = 0; i < 100; i++) {
//            new Message(applicationEventPublisher)
//                    .save()
//                    .send()
//                    .destroy();
//        }
        for(int i = 0; i < 100; i++) {
            Message message = new Message();
            applicationEventMulticaster.multicastEvent(new CreateEvent(message));
        }
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
