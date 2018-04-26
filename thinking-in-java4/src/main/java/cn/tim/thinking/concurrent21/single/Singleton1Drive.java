package cn.tim.thinking.concurrent21.single;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by LuoLiBing on 17/2/28.
 */
public class Singleton1Drive {

    private volatile Singleton1 instance = null;

    private CountDownLatch latch;

    public Singleton1Drive(CountDownLatch latch) {
        this.latch = latch;
    }

    /***
     * double check在某些语言在某些硬件平台的实现可能是不安全的.
     * Obj obj = new Obj(); 这段代码会被分为1 分配内存 2 初始化 3 赋值
     * 然而由于编译器生成的代码允许指令重排, 所以有可能会改成132.
     * 如果一个线程先判断obj == null,然后获取锁,并对其进行实例化. 有可能会先将分配了内存但是还没有初始化的对象赋值给obj.
     * 这个时候如果另一个线程判断obj, 会认为它不为null, 并且进行调用, 这个时候会出现无法预知的情况(例如字段没有赋值, 都是默认值等情况)
     * java5通过volatile修复了这个问题, 该关键词禁止了指令重排.
     *
     */
    public Singleton1 getInstance() {
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(instance == null) {
            synchronized (this) {
                if(instance == null) {
                    instance = new Singleton1();
                }
            }
        }
        return instance;
    }

    /**
     * 另一种方式: 通过静态内部类来实现
     */
    private static class Helper {
        private static Singleton1 singleton1 = new Singleton1();
    }

    public static Singleton1 getInstance1() {
        return Helper.singleton1;
    }

    public static void main(String[] args) {
        for(int j = 0; j < 100; j++) {
            CountDownLatch latch = new CountDownLatch(1);
            Singleton1Drive singleton = new Singleton1Drive(latch);
            ExecutorService exec = Executors.newFixedThreadPool(10);
            for (int i = 0; i < 10; i++) {
                exec.execute(() -> {
                    Singleton1 instance = singleton.getInstance();
                    if(instance.id == 0) {
                        System.out.println("aaaaaaaaaaaaaaaaaaa");
                    }

                });
            }
            latch.countDown();
        }
    }
}
