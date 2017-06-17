package cn.tim.thinking.concurrent21.lock;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by LuoLiBing on 16/10/25.
 *
 * ReentrantLock 可重入锁
 * 可重入锁拥有互斥的基本环节,和隐式监视和同步synchronized的方法,同时还扩展了一下其他的方法.
 * 一个ReentrantLock锁被最后成果锁住但还没有解锁的线程所拥有,当锁没有被其他线程占有时,调用lock会立即获取到锁并且返回.如果锁已经被其他线程占用会阻塞住,直到可用或者线程中断为止
 * 可以通过调用isHeldByCurrentThread来判断锁是否被当前线程占有,getHoldCount看锁的占有层次
 *
 * 公平锁fairness的优缺点
 * http://ifeve.com/reentrantlock-and-fairness/
 *
 * ReentrantLock支持公平锁机制,如果设置为true,则支持FIFO以来保证公平.设置为false,lock不保证任何特定的顺序
 * 在多线程环境下使用公平锁会拉低总体吞吐量,通常会比使用不公平锁慢很多,但是公平锁能减少差异性和避免饥饿的发生.
 *
 * 局限性
 * 但是公平锁并不能保证线程调度的公平性,使用公平锁的多个线程中的一个可能会连续多次获得他,而其他活动线程却没有进展,而不是目前持有锁的线程. 原因是公平锁调度机制与操作系统线程调度机制的差异导致这种情况的发生
 * 还有不定时的tryLock方法不会遵守公平机制,如果锁是可用的,它将获取到,即使其他线程正在等待
 *
 * 推荐调用完lock.lock()之后,紧跟着一个try-finally块
 *
 * 除了实现了Lock接口之外,这个类还定义了一系列的public和protected方法检查锁的状态,有一些方法只是用来作为监控
 *
 * ReentrantLock类
 * 属性
 *  Sync    同步器实现所有实现机
 *          同步机制包括公平同步FairSync与非公平同步NonfairSync
 *
 * 方法
 *  lock()  获取锁,分公平锁与非公平锁,非公平锁会先compareAndSetState(0,1)判断锁是否被其他线程占用,没有的话直接设置锁当前占有线程为当前锁,并且设置state=1,
 *          否则继续判断占有锁的线程是否是当前锁,是的话锁的state++,否则返回false,进入等待区挂起等待,直到被重新恢复为止. 公平锁与非公平锁的区别在在恢复的时候选择的策略不同
 *  lockInterruptibly() 获取锁,当线程被中断时抛出线程中断异常
 *  tryLock()   只有当锁没有被占用时锁住并且返回true,否则直接返回false
 *  tryLock(timeout.timeunit)   带超时时间的尝试锁
 *
 *  unlock()    解锁,如果当前线程占有锁,则将持有锁总数state自减,之后如果state=0,表示锁已经被释放. 如果当前线程不占有锁不存在释放,调用将抛出IllegalMonitorStateException异常
 *              解锁直接调用Sync的release方法,会先自减state,判断state自减后的值,如果为0表明锁已经释放,可以从等待的线程中挑一个进行unpark恢复执行,否则只是将锁减一
 *
 *  newCondition    返回一个条件实例, Condition实现了跟Object在使用内置锁时的wait,notify和notifyAll的所有功能.
 *                  当锁不被任何线程占有,调用任何await()或者signal()等任何方法,都会抛出IllegalMonitorStateException异常.
 *                  await() 当调用await()方法时,锁会被释放,线程被挂起,但是挂起之前会保存lock hold count,并且在恢复的时候,会重新设置锁持有数量state. 当线程被中断时,调用wait会抛出异常,之后会清除Interrupte的状态
 *
 *                  所有等待的线程被全部唤醒的顺序按照FIFO的顺序进行
 *                  默认情况下,线程重新取得锁的顺序与最初获得锁的顺序一样.但是在公平锁中更倾向于等待时间最长的线程
 *  getHoldCount()  获取锁被持有的个数,也就是state
 *  isHeldByCurrentThread() 判断锁是否被当前线程所持有
 *  isLocked()      是否被锁住
 *  isFair()        判断是否是公平锁
 *  getOwner()      获取持有锁的线程
 *  hasQueuedThreads() 判断是否有线程排队等待
 *  hasQueuedThread(th)判断制定线程是否在等待队列中
 *  hasWaiters(Condition) 判断是否有线程在等待condition条件
 *  getWaitQueueLength(Condition) 获取等待condition队列得长度
 *  getQueuedThreads()  获取等待锁的所有线程集合
 *  getWaitingThreads(condition) 获取等待condition线程集合
 *
 *
 *
 */
public class ReentrantLockDemo {

    private Lock lock = new ReentrantLock();

    @Test
    public void test1() {
        lock.lock();
    }

    class Person {
        private Lock lock = new ReentrantLock();

        private int age = 0;

        public void grow() {
            try {
                TimeUnit.MILLISECONDS.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.lock();
            System.out.println("isHeldByCurrentThread=" + ((ReentrantLock) lock).isHeldByCurrentThread());
            System.out.println("HoldCount=" + ((ReentrantLock) lock).getHoldCount());
            try {
                age++;
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } finally {
                lock.unlock();
            }
        }

        public int getAge() {
            lock.lock();
            try {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return age;
            } finally {
                lock.unlock();
            }
        }
    }

    /**
     * lockSupport类
     */
    @Test
    public void lockSupport() {
        Person p = new Person();
        new Thread(p::grow).start();
        new Thread(() -> System.out.println("age= " + p.getAge())).start();

        try {
            TimeUnit.SECONDS.sleep(20);
            System.out.println("age=" + p.getAge());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private ReentrantLock lock1 = new ReentrantLock();

    private Condition condition1 = lock1.newCondition();

    @Test
    public void lockInterruptibly() throws InterruptedException {

        Thread th = new Thread(() -> {
            for(int i = 0; i < 100000; i++) {

            }
            System.out.println("CurrentThread interrupted=" + Thread.currentThread().isInterrupted());
            // 即使处于interrupt状态,照样会被锁住,线程并不会被中断
             lock1.lock();
            try {
//                lock1.lockInterruptibly();
                System.out.println("before await()");
                condition1.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("lock1 has locked!!!");
        });
        th.start();

        System.out.println("before thread interrupted!");
        Thread.sleep(3000);
        th.interrupt();
        System.out.println("after thread interrupted!");
        while (th.isAlive()) {
            TimeUnit.MILLISECONDS.sleep(50);
        }
        System.out.println("finish");
    }

    @Test
    public void tryLock() {
        lock1.lock();

        System.out.println("tryLock=" + lock1.tryLock());
    }

    class ConditionHold {

        private Lock lock = new ReentrantLock();

        private Condition condition = lock.newCondition();

        private int num = 0;

        public void f1() throws InterruptedException {
            lock.lock();
            try {
                while (num < 1000) {
                    condition.await();
                    System.out.println("ThreadId = " + Thread.currentThread().getId());
                }
            } finally {
                lock.unlock();
            }
        }

        public void f2() {
            lock.lock();
            try {
                num++;
                condition.signalAll();
            } finally {
                lock.unlock();
            }
        }
    }

    @Test
    public void signalAll() throws InterruptedException {
        ConditionHold ch = new ConditionHold();
        Runnable r = () -> {
            try {
                while (!Thread.interrupted()) {
                    ch.f1();
                }
            } catch (InterruptedException e) {
                System.out.println("Interrupted");
            }
            System.out.println("finish");
        };
        ExecutorService exec = Executors.newCachedThreadPool();
        for(int i = 0; i < 10; i++) {
            exec.execute(r);
        }

        exec.shutdown();

        ReentrantLock rlk = (ReentrantLock)ch.lock;
        // 获取锁持有个数
        System.out.println("getHoldCount= " + rlk.getHoldCount());
        // 是否被当前线程所占有
        System.out.println("isHeldByCurrentThread = " + rlk.isHeldByCurrentThread());
        // 是否已经被锁住
        System.out.println("isLocked = " + rlk.isLocked());
        // 是否公平
        System.out.println("isFair = " + rlk.isFair());
        // 是否有等待线程在等待这个锁
        System.out.println("hasQueuedThreads = " + rlk.hasQueuedThreads());
        // 判断ch.condition条件上是否有等待队列
        // System.out.println("hasWaiters = " + rlk.hasWaiters(ch.condition));
        System.out.println("getQueueLength = " + rlk.getQueueLength());
        // 获取ch.condition条件上等待队列得长度
        // System.out.println("getWaitQueueLength = " + rlk.getWaitQueueLength(ch.condition));
        ch.f2();
        while (!exec.isTerminated()) {
            exec.awaitTermination(50, TimeUnit.MILLISECONDS);
        }
    }
}
