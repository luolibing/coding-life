package cn.tim.thinking.concurrent21.thread.exception;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Created by LuoLiBing on 16/10/17.
 * 捕获异常
 * 1 由于线程的本质,使得我们不能捕获从线程中逃逸的异常
 * 2 java SE 5中可以使用Executor来解决异常捕获问题,使用try-catch语句块并不能捕获Executor里面的异常
 * 3 为了解决这个问题,我们要修改Executor产生线程的方式.Thread.UncaughtExceptionHandler是java se5中的新街口,它允许你在每个Thread对象上附着异常处理器
 *      Thread.UncaughtExceptionHandler.uncaughtException()会在线程因未捕获的异常而临近死亡时被调用.为了使用它,我们自定义一个ThreadFactory,在每个新创建的Thread上附着UncaughtExceptionHandler
 * 4 还有一种通用的方法,设置全局的默认UncaughtExceptionHandler, Thread.setDefaultUncaughtExceptionHandler()
 */
public class ExceptionThreadDemo {

    static class ExceptionThread implements Runnable {

        @Override
        public void run() {
            throw new RuntimeException();
        }

        public static void main(String[] args) {
            try {
                ExecutorService exec = Executors.newCachedThreadPool();
                exec.execute(new ExceptionThread());
            } catch (Exception e) {
                System.err.println("Exception has been handled!");
            }
        }
    }

    static class ExceptionThread2 implements Runnable {

        @Override
        public void run() {
            Thread th = Thread.currentThread();
            System.out.println("run() by " + th);
            System.out.println("eh=" + th.getUncaughtExceptionHandler());
            throw new RuntimeException();
        }
    }

    class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

        @Override
        public void uncaughtException(Thread t, Throwable e) {
            System.out.println("caught " + e);
        }
    }

    class HandlerThreadFactory implements ThreadFactory {

        @Override
        public Thread newThread(Runnable r) {
            System.out.println(this + " creating new Thread");
            Thread th = new Thread(r);
            System.out.println("created " + th);
            th.setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
            System.out.println("eh= " + th.getUncaughtExceptionHandler());
            return th;
        }
    }

    @Test
    public void test1() {
        ExecutorService exec = Executors.newCachedThreadPool(new HandlerThreadFactory());
        try {
            exec.execute(new ExceptionThread2());
        } catch (Exception e) {
            System.out.println("caught " + e.getMessage());
        }
    }

    @Test
    public void test2() {
        Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(new ExceptionThread2());
    }

}
