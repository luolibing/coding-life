package cn.tim.thinking.concurrent21.thread;

import cn.tim.thinking.concurrent21.thread.RunnableDemo;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by LuoLiBing on 16/10/12.
 * 线程睡眠,时钟阻塞,线程调度器可以切换到另一个线程,驱动另一个任务
 */
public class SleepDemo {

    class SleepingTask extends RunnableDemo.LiftOff {
        @Override
        public void run() {
            try {
                while (countDown-- > 0) {
                    System.out.println(status());
                    // 睡眠(阻塞100毫秒),线程调度器可以切换到另一个线程,进而驱动另一个任务
                    TimeUnit.MILLISECONDS.sleep(100);
                }
            } catch (InterruptedException e) {
                System.err.println("Interrupted");
            }
        }
    }

    @Test
    public void sleepingDemo() {
        ExecutorService exec = Executors.newCachedThreadPool();
        for(int i = 0; i < 10; i++) {
            exec.execute(new SleepingTask());
        }
        exec.shutdown();
    }

    static class RandomSleepingTask implements Runnable {

        private static int count = 1;

        private final int id = count ++;

        private static Random random = new Random(67);

        @Override
        public void run() {
            int sleep = random.nextInt(1000);
            System.out.println(id + " Thread sleep " + sleep + " milliseconds!");
            try {
                TimeUnit.MILLISECONDS.sleep(sleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void randomSleepTask() {
        ThreadPoolExecutor exec = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        for(int i = 0; i < 100; i++) {
            exec.execute(new RandomSleepingTask());
        }
    }

}
