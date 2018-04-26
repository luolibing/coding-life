package cn.tim.thinking.concurrent21.atomic;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.junit.Test;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by LuoLiBing on 16/10/19.
 * Atomic类被设计用来构建java.util.concurrent中的类,因此只有在特殊情况下才在自己的代码中使用它们.通常依赖于锁更安全一些
 */
public class AtomicDemo {

    static class AtomicIntegerTest implements Runnable {

        public static AtomicInteger i = new AtomicInteger();

        public int getValue() {return i.get(); }

        public void evenIncrement() { i.addAndGet(2); }

        @Override
        public void run() {
            for(int i = 0; i < 100; i++) {
                evenIncrement();
            }
        }

        public static void main(String[] args) throws InterruptedException {
            ExecutorService exec = Executors.newCachedThreadPool();
            for(int i = 0; i < 100; i++) {
                exec.execute(new AtomicIntegerTest());
            }
            exec.shutdown();
            while (!exec.isTerminated()) {
                exec.awaitTermination(100, TimeUnit.MILLISECONDS);
            }
            System.out.println(i.get());
        }
    }


    /**
     * guava缓存
     * @throws InterruptedException
     */
    @Test
    public void test1() throws InterruptedException {
        Cache<Object, Object> cache = CacheBuilder
                .newBuilder()
                .removalListener(
                    (notification) -> System.out.println(notification.getValue()))
                .expireAfterAccess(2, TimeUnit.SECONDS)
                .build();
        cache.put("aaa", "bbb");
        TimeUnit.SECONDS.sleep(10);
    }

    @Test
    public void test2() throws InterruptedException {
        AtomicInteger ato = new AtomicInteger(0);
        for(int i = 0; i < 1000; i++) {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    ato.incrementAndGet();
                }
            }, 1000L);
        }
        Thread.sleep(10000);
        System.out.println(ato.get());
    }
}
