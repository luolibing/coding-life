package cn.tim.thinking.concurrent21.executors;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by TIM on 2017/2/26.
 */
public class ExecutorsDemo {

    @Test
    public void reject() throws InterruptedException {
        long threadId = Thread.currentThread().getId();
        System.out.println("currentTHread: " + threadId);
        // 当队列满的时候， 直接在调用者线程中调用Runnable.run()方法， 所以这个时候如果有个异常， 线程因为异常退出， 后面的任务也执行不了了
        // 而线程中的任务抛出异常之后， 线程被终止回收， 线程池会重新创建线程补上。
        ThreadPoolExecutor executor =
                new ThreadPoolExecutor(2, 2, 1000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(2), new ThreadPoolExecutor.CallerRunsPolicy());
        for(int i = 0; i < 100; i++) {
            final int finalI = i;
            executor.execute(() -> {
                try {
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getId() + ": " + finalI);
                    if(threadId == Thread.currentThread().getId()) {
                        throw new RuntimeException("interruptedException!!");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
            Thread.sleep(100);
        }
    }

    /**
     * 无界队列线程池
     * @throws InterruptedException
     */
    @Test
    public void linkedQueue() throws InterruptedException {
        Random rand = new Random();
        ThreadPoolExecutor exec = new ThreadPoolExecutor(2, 5, 0, TimeUnit.MICROSECONDS, new LinkedBlockingQueue<>());
        for(int i = 0 ; i < 1000000; i++) {
            exec.submit(() -> {
                try {
                    System.out.println(exec.getPoolSize());
                    Thread.sleep(rand.nextInt(100));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        exec.shutdown();
        while (!exec.isTerminated()) {
            Thread.sleep(100);
        }
    }

    /**
     * 有界队列线程池在队列满的时候会扩充到最大线程数， 然后如果继续添加的话， 会启动拒绝策略, 默认为拒绝抛出异常
     * @throws InterruptedException
     */
    @Test
    public void arrayQueue() throws InterruptedException {
        Random rand = new Random();
        ThreadPoolExecutor exec = new ThreadPoolExecutor(2, 5, 0, TimeUnit.MICROSECONDS, new ArrayBlockingQueue<>(100), new ThreadPoolExecutor.CallerRunsPolicy());
        for(int i = 0 ; i < 1000000; i++) {
            exec.submit(() -> {
                try {
                    System.out.println(exec.getPoolSize());
                    Thread.sleep(rand.nextInt(100));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        exec.shutdown();
        while (!exec.isTerminated()) {
            Thread.sleep(100);
        }
    }

    /**
     * allowCoreThreadTimeOut属性， 允许当数量为core时， 超过超时时间再继续降低
     * @throws InterruptedException
     */
    @Test
    public void allowCoreThreadTimeOut() throws InterruptedException {
        Random rand = new Random();
        ThreadPoolExecutor exec = new ThreadPoolExecutor(3, 5, 200, TimeUnit.MICROSECONDS, new ArrayBlockingQueue<>(100), new ThreadPoolExecutor.CallerRunsPolicy());
        exec.allowCoreThreadTimeOut(true);
        for(int i = 0 ; i < 2000; i++) {
            exec.submit(() -> {
                try {
                    Thread.sleep(rand.nextInt(100));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(exec.getPoolSize());
            });
        }
//        exec.shutdown();
        while (!exec.isTerminated()) {
            System.out.println(exec.getPoolSize());
            Thread.sleep(100);
        }
    }


    @Test
    public void emptyPool() {
        ThreadPoolExecutor exec = new ThreadPoolExecutor(3, 5, 200, TimeUnit.MICROSECONDS, new ArrayBlockingQueue<>(100), new ThreadPoolExecutor.CallerRunsPolicy());
        System.out.println(exec.getPoolSize());
    }
}
