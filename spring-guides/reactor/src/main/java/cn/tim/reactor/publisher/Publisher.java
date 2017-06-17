package cn.tim.reactor.publisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.bus.Event;
import reactor.bus.EventBus;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by LuoLiBing on 15/10/27.
 * 生产者
 */
@Service
public class Publisher {

    /**
     * event路由
     */
    @Autowired
    EventBus eventBus;

    @Autowired
    CountDownLatch countDownLatch;

    /**
     * 发布事件
     * @param number
     * @throws InterruptedException
     */
    public void publishQuotes(int number) throws InterruptedException {
        long start = System.currentTimeMillis();
        AtomicInteger counter = new AtomicInteger(1);
        for(int i = 0; i<number; i++) {
            // 发布quotes事件，event为一个自增的数字
            eventBus.notify("quotes", Event.wrap(counter.getAndIncrement()));
        }
        // 等待所有事件执行完
        countDownLatch.await();

        long elapsed = System.currentTimeMillis() - start;
        System.out.println("Elapsed time: " + elapsed + "ms");
        System.out.println("Average time per quote: " + elapsed / number + "ms");
    }

}
