package cn.tim.reactivex;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * User: luolibing
 * Date: 2017/11/16 17:00
 */
public class ReactiveXDemo {

    @Test
    public void helloWorld() {
        Flowable<String> flowable = Flowable.just("Hello World");
        flowable.subscribe(System.out::println);
        flowable.subscribe(s -> {
            System.out.println("flowable subscribe " + s);
        });
    }

    /**
     * 后台线程执行计算与网咯请求，ui线程处理得到的结果
     * @throws InterruptedException
     */
    @Test
    public void schedule() throws InterruptedException {
        Flowable.fromCallable(() -> {
            Thread.sleep(1000);
            return "Done";
        })
        // 后台线程IO/requset
        .subscribeOn(Schedulers.io())
        // UI线程
        .observeOn(Schedulers.single())
        .subscribe(System.out::println, Throwable::printStackTrace);
        Thread.sleep(2000);
    }

    @Test
    public void concurrent() {
        Flowable.range(1, 100)
                .observeOn(Schedulers.computation())
                .map(v -> v * v)
                .blockingSubscribe(System.out::println);
    }

    @Test
    public void extactInt() {
        try {
            int i = new BigDecimal("10000.11").intValueExact();
            System.out.println("intValueExact = " + i);
        } catch (Exception e) {
            e.printStackTrace();
        }

        int i = new BigDecimal("10000.99999999999999").intValue();
        System.out.println("intValue = " + i);
    }
}
