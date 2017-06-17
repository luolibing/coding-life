package cn.tim.thinking.concurrent21.resource;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by LuoLiBing on 16/10/17.
 *
 * 显示的Lock对象
 *
 * Lock对象必须被显示地创建,锁定和释放,因此它与内奸的锁形式相比,代码缺乏优雅性,但是对于解决某些问题来说,它更加灵活,可以在try-finally中释放锁,然后做一些清理工作,这在synchronize
 * 一般lock.lock和unlock方法,必须在try - finally中,而且return语句必须在try子句中,以确保unlock()不会太早发生,从而将数据暴露给第二个任务
 *
 * lock还可以使用tryLock()或者使用带超时时间的tryLock()方法,显式Lock相对于内建的synchronized锁来说,具备更细粒度的控制力.例如遍历链表的交替锁.
 *
 */
public class ReentrantLockDemo {

    class MutexEvenGenerator extends EvenCheckerDemo.IntGenerator {

        private int currentEvenValue = 0;

        private Lock lock = new ReentrantLock();

        @Override
        public int next() {
            lock.lock();
            try {
                ++ currentEvenValue;
                ++ currentEvenValue;
                return currentEvenValue;
            } finally {
                lock.unlock();
            }
        }
    }

    static class AttemptLocking {
        private ReentrantLock lock = new ReentrantLock();

        // 尝试获取锁
        public void untimed() {
            boolean captured = lock.tryLock();
            try {
                System.out.println("tryLock(): " + captured);
            } finally {
                if(captured) {
                    lock.unlock();
                }
            }
        }

        public void timed() {
            boolean captured = false;
            try {
                captured = lock.tryLock(2, TimeUnit.SECONDS);
                System.out.println("tryLock(2, TimeUnit.SECONDS); " + captured);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                if(captured) {
                    lock.unlock();
                }
            }
        }

        public static void main(String[] args) {
            AttemptLocking al = new AttemptLocking();
            al.untimed();
            al.timed();

            // 占用锁,不释放
            new Thread() {
                {setDaemon(true);}

                @Override
                public void run() {
                    al.lock.lock();
                    System.out.println("acquired");
                }
            }.start();

            Thread.yield();
            // 导致获取不到锁
            al.untimed();
            al.timed();
        }
    }
}