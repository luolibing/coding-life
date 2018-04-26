package cn.tim.thinking.concurrent21.end;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by LuoLiBing on 16/10/21.
 * 如果你尝试着在一个对象上调用其synchronized方法,而这个对象的锁已经被其他任务获得,那么调用任务将被挂起(阻塞),直到这个锁可获得
 */
public class SynchronizedBlockDemo {

    // 同一互斥可以如何能被同一个任务多次获得, 不可中断,那么潜在会被锁住程序的可能
    public synchronized void f1(int count) {
        if(count -- > 0) {
            System.out.println("f1() calling f2() with count " + count);
            f2(count);
        }
    }

    public synchronized void f2(int count) {
        if(count -- > 0) {
            System.out.println("f2() calling f1() with count " + count);
            f1(count);
        }
    }

    public static void main(String[] args) {
        SynchronizedBlockDemo demo = new SynchronizedBlockDemo();
        Thread th = new Thread() {
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // f1()第一次调用获得锁,然后调用f2()又获取一次锁, 因为一个任务已经持有一个锁能继续持有该对象上的其他锁,加锁计数器会递增.
                demo.f1(10);
            }
        };
        th.start();
        th.interrupt();
    }

    /**
     * 在ReentrantLock上阻塞的任务具备可以被中断的能力 lock.lockInterruptibly()
     */
    static class BlockedMutex {
        private Lock lock = new ReentrantLock();

        public BlockedMutex() {
            lock.lock();
        }

        public void f() {
            try {
                // 锁住直到中断出现,出现中断的发出
                lock.lockInterruptibly();
                System.out.println("lock acquired in f()");
            } catch (InterruptedException e) {
                System.out.println("Interrupted from lock acquisition in f()");
            }
        }
    }

    static class Blocked2 implements Runnable {
        BlockedMutex blocked = new BlockedMutex();

        @Override
        public void run() {
            System.out.println("waiting for f() in BlockedMutex");
            blocked.f();
            System.out.println("Broken out of blocked call");
        }
    }

    static class Interrupting2 {
        public static void main(String[] args) throws InterruptedException {
            Thread th = new Thread(new Blocked2());
            th.start();
            TimeUnit.SECONDS.sleep(10);
            System.out.println("Issuing t.interrupt()");
            th.interrupt();
        }
    }


}
