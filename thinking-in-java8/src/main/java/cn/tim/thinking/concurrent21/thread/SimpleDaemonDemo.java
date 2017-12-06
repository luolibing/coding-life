package cn.tim.thinking.concurrent21.thread;

import org.junit.Test;

import java.util.concurrent.*;

/**
 * Created by LuoLiBing on 16/10/14.
 * 后台线程
 *
 * 后台线程
 * 1 是指程序运行的时候在后台提供一种通用的线程,并且这种线程并不属于程序中不可或缺的部分.当所有非后台线程结束时,程序也就终止了,同时杀掉所有的后台线程,只要有任何非后台线程还在运行,程序就不会终止.
 * 2 test是后台线程,main是非后台线程.线程会继承父线程的daemon属性,后台线程创建出来的线程也是后台线程.
 * 3 只有在启动前设置daemon才能生效,通过isDaemon()来判断是否是后台线程,可以自定义ThreadFactory来实现自定义线程,或者自定义ExecuteService
 * 4
 */
public class SimpleDaemonDemo {

    public static class SimpleDaemons implements Runnable {

        @Override
        public void run() {

            try {
                while (true) {
                    TimeUnit.MILLISECONDS.sleep(100);
                    System.out.println(Thread.currentThread() + " " + this);
                }
            } catch (InterruptedException e) {
                System.out.println("sleep() interrupted");
            }
        }
    }

    /**
     * test 方法是一个后台线程,执行完之后就会强制退出
     * @throws InterruptedException
     */
    @Test
    public void test1() throws InterruptedException {
        for(int i = 0; i < 10; i++) {
            Thread daemon = new Thread(new SimpleDaemons());
            // 必须在线程启动前,将其设置为
            daemon.setDaemon(true);
            daemon.start();
        }
        System.out.println("All daemons started");
        TimeUnit.MILLISECONDS.sleep(175);
    }

    /**
     * 通过自定义线程工厂类,自定义创建的线程属性
     */
    @Test
    public void test2() {
        ExecutorService exec = Executors.newFixedThreadPool(10, new DaemonThreadFactory());
        for(int i = 0; i < 10; i++) {
            exec.execute(new SimpleDaemons());
        }
        exec.shutdown();
    }

    // 线程池
    static class DaemonThreadFactory implements ThreadFactory {

        @Override
        public Thread newThread(Runnable r) {
            Thread th = new Thread(r);
            th.setDaemon(true);
            return th;
        }
    }

    /**
     * 后台线程池
     */
    class DaemonThreadPoolExecutor extends ThreadPoolExecutor {
        public DaemonThreadPoolExecutor() {
            super(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<>(), new DaemonThreadFactory());
        }
    }

    /**
     * 继承线程池类
     */
    @Test
    public void test3() {
        DaemonThreadPoolExecutor exec = new DaemonThreadPoolExecutor();
        for(int i = 0; i < 10; i++) {
            exec.execute(new SimpleDaemons());
        }
        exec.shutdown();
    }

    /**
     * main 是一个非后台线程
     * @param args
     * @throws InterruptedException
     */
    public static void main1(String[] args) throws InterruptedException {
        ExecutorService exec = Executors.newFixedThreadPool(10, new DaemonThreadFactory());
        for(int i = 0; i < 10; i++) {
            exec.submit(new SimpleDaemons());
        }
        System.out.println("All daemons started");
        TimeUnit.MILLISECONDS.sleep(100);
    }

    static class Daemon implements Runnable {

        private Thread[] t = new Thread[10];

        @Override
        public void run() {
            for(int i = 0, len = t.length; i < len; i++) {
                t[i] = new Thread(new DaemonSpawn());
                t[i].start();
                System.out.println("DaemonSpawn " + i + " started. " + "t[" + i + "].isDaemon()=" + t[i].isDaemon() + ".");
            }
            for(int i = 0, len = t.length; i < len; i++) {
                System.out.println("t[" + i + "].isDaemon()=" + t[i].isDaemon() + ".");
            }

            while (true) {
                Thread.yield();
            }
        }
    }

    static class DaemonSpawn implements Runnable {

        @Override
        public void run() {
            while (true) {
                Thread.yield();
            }
        }
    }

    public static void main2(String[] args) throws InterruptedException {
        Thread th = new Thread(new Daemon());
        th.setDaemon(true);
        th.start();
        System.out.println("d is daemon=" + th.isDaemon());
        TimeUnit.SECONDS.sleep(1);
    }

    /**
     * 后台线程不会执行finally语句
     */
    static class FinalyDaemon implements Runnable {

        @Override
        public void run() {
            System.out.println("starting ADaemon");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("后台线程执行finally吗?");
            }
        }
    }

    public static void main(String[] args) {
        Thread thread = new Thread(new FinalyDaemon());
        thread.setDaemon(true);
        thread.start();
    }
}
