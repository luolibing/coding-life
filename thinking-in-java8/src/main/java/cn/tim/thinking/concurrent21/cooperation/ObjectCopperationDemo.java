package cn.tim.thinking.concurrent21.cooperation;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by LuoLiBing on 16/10/22.
 * 多线程互斥与协作
 * 当任务协作时,关键问题是这些任务之间的握手.为了实现握手,我们使用了相同的基础特性:互斥.互斥确保只有一个任务可以响应某个信号,这样可以根除任何可能得竞争条件.在互斥之上,我们为任务添加可以挂起自己的途径,直到某些外部条件发生变化(例如需要的管道已经到位,数据已到位)
 * 任务之间的握手,可以通过Object的wait()和notify()来安全地实现
 *
 * wait和sleep
 * wait()会在等待外部世界发生改变的时候将任务挂起,任务上的锁被释放,等待notify()或notifyAll()发生,表示发生了某些感兴趣的事物,任务才被唤醒去检查所发生的改变.像是在声明: 我已经刚刚做完能做的事情,因此我要在这里等待,希望其他的synchronized操作在条件适合的情况下能够执行,并且完成之后合适的时候通知我,使我能够继续执行
 * sleep()或者调用yield()的时候锁并没有释放
 *
 * 一般情况下会使用while()来包围wait(),原因有(本质就是要检查所感兴趣的特定条件)
 * 1 你可能有多个任务出于相同的原因在等待同一个锁,而第一个唤醒任务可能会改变这种状态.如果这样那么这个任务应该再次挂起,等到感兴趣的条件发生变化(while(条件)就是感兴趣的条件)
 * 2 这个任务从其wait()中被唤醒的时刻,有可能会有某个其他的任务已经做出了改变,从而使这个任务此时不能执行
 * 3 也有可能某些任务处于不同的原因在等待你的对象上的锁
 *
 * wait(millisecond) 和sleep(millisecond)意思相近,在制定时间内暂停
 * 1 wait期间对象锁是释放的
 * 2 可以通过notify(),notifyAll或者时间到期,从wait()中回复执行
 *
 * wait(),notify()和notifyAll()属于Object的一部分
 * 实际上,只能在同步控制方法或或同步控制块里调用wait(),notify()和notifyAll(因为这里才有锁,sleep()可以在非同步控制方法里调用,因为不用操作锁)
 * 在非同步环境下调用这些方法,会抛出IllegalMonitorStateException异常(当前线程不是拥有者).因为wait()调用资源并没有锁,不存在竞争的情况
 *
 *
 */
public class ObjectCopperationDemo {

    @Test
    public void waitTest1() {
        List<String> list = new ArrayList<>();
        try {
            list.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 共享资源: car   先打蜡后抛光,任务之间通过wait 和notifyAll进行交互通信,任务交替执行
     */
    static class Car {
        // 打蜡
        private boolean waxOn = false;

        // 打蜡
        public synchronized void waxed() {
            waxOn = true;
            notifyAll();
        }

        // 抛光,并通知其他程序
        public synchronized void buffed() throws InterruptedException {
            waxOn = false;
            notifyAll();
        }

        // 等待打蜡
        public synchronized void waitForWaxing() throws InterruptedException {
            while (!waxOn) {
                wait();
            }
        }

        // 等待抛光
        public synchronized void waitForBuffing() throws InterruptedException {
            while (waxOn) {
                wait();
            }
        }
    }

    // 打蜡
    static class WaxOn implements Runnable {

        private Car car;

        public WaxOn(Car car) {
            this.car = car;
        }

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    System.out.println("Wax on!");
                    TimeUnit.MILLISECONDS.sleep(200);
                    car.waxed();
                    car.waitForBuffing();
                }
            } catch (InterruptedException e) {
                System.out.println("Exiting via interrupt");
            }
            System.out.println("Ending Wax On task");
        }
    }

    static class WaxOff implements Runnable {

        private Car car;

        public WaxOff(Car car) {
            this.car = car;
        }

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    // 判断是否可以抛光
                    car.waitForWaxing();
                    System.out.println("Wax off!");
                    TimeUnit.MILLISECONDS.sleep(20);
                    car.buffed();
                }
            } catch (InterruptedException e) {
                System.out.println("Exiting via interrupt");
            }
            System.out.println("Ending Wax Off task");
        }
    }

    /**
     * 抛光任务最先获取到car的锁,然后通过判断当前应该做的工序,调用wait()释放锁等待,打蜡任务稍后进入到程序,直接获取锁打蜡,
     * 然后改变waxOn状态并且通知抛光任务,抛光任务wait解除阻塞,判断waxOn字段,退出while(),执行抛光,如此反复.
     * 改变状态值必须处于同步控制当中
     */
    static class WaxOMatic {
        public static void main(String[] args) throws InterruptedException {
            Car car = new Car();
            ExecutorService exec = Executors.newCachedThreadPool();
            exec.execute(new WaxOff(car));
            exec.execute(new WaxOn(car));
            TimeUnit.SECONDS.sleep(5);
            // 尝试中断所有正在执行的任务
            exec.shutdownNow();
        }
    }

    static class Runnable2 implements Runnable {

        private final Runnable1 runnable;

        public Runnable2(Runnable1 runnable) {
            this.runnable = runnable;
        }

        @Override
        public void run() {
            try {
                System.out.println("Runnable1 start");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (runnable) {
                runnable.notifyAll();
            }
        }
    }

    static class Runnable1 implements Runnable {

        @Override
        public synchronized void run() {
            try {
                System.out.println("Runnable2 waiting");
                wait();
                System.out.println("Runnable2 finish");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class RunnableDemo {
        public static void main(String[] args) {
            ExecutorService exec = Executors.newCachedThreadPool();
            Runnable1 r1 = new Runnable1();
            exec.execute(r1);
            Runnable2 r2 = new Runnable2(r1);
            exec.execute(r2);
            exec.shutdown();
        }
    }

    static class BusyWaitObject {
        private volatile boolean wait = false;
//        public void waiting() {
//            long start = System.currentTimeMillis();
//            while (!wait) {
////                System.out.println(wait);
//            }
//            this.wait = false;
//            System.out.println("time= " + (System.currentTimeMillis() - start));
//        }
//
//        public void doing() {
//            this.wait = true;
//        }

        public synchronized void waiting() {
            long start = System.currentTimeMillis();
            try {
                while (!wait) {
                    wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.wait = false;
            System.out.println("time= " + (System.currentTimeMillis() - start));
        }

        public synchronized void doing() {
            this.wait = true;
            notify();
        }
    }

    static class BusyWait1 implements Runnable {

        private BusyWaitObject object;

        public BusyWait1(BusyWaitObject object) {
            this.object = object;
        }

        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(1);
                object.doing();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class BusyWait2 implements Runnable {

        private BusyWaitObject object;

        public BusyWait2(BusyWaitObject object) {
            this.object = object;
        }

        @Override
        public void run() {
            object.waiting();
        }
    }

    static class BusyWait {
        public static void main(String[] args) {
            ExecutorService exec = Executors.newCachedThreadPool();
            BusyWaitObject obj = new BusyWaitObject();
            exec.execute(new BusyWait1(obj));
            exec.execute(new BusyWait2(obj));
            exec.shutdown();
        }
    }

    static class SharedMonitor {
        private boolean condition = false;

        public void turnTrue() {
            System.out.println("turn condition to true");
            condition = true;
        }
    }

    static class MissNotify1 implements Runnable {
        private final SharedMonitor sharedMonitor;

        public MissNotify1(SharedMonitor sharedMonitor) {
            this.sharedMonitor = sharedMonitor;
        }

        @Override
        public void run() {
            synchronized (sharedMonitor) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 设置条件为true
                sharedMonitor.turnTrue();
                sharedMonitor.notify();
                System.out.println("after notify");
            }
        }
    }

    static class MissNotify2 implements Runnable {

        private final SharedMonitor sharedMonitor;

        public MissNotify2(SharedMonitor sharedMonitor) {
            this.sharedMonitor = sharedMonitor;
        }

        @Override
        public void run() {
            // 条件不符合进入循环
            while (!sharedMonitor.condition) {
                System.out.println("point 1");
                // 获取锁
                synchronized (sharedMonitor) {
                    try {
                        System.out.println("waiting");
                        // 因为判断条件与条件改变不是处于同步中,所以可能会出现判断通过之后,条件改变,
                        // 完美错过notify,wait放弃锁阻塞住,notify不会再次调用,发生死锁
                        sharedMonitor.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    static class MissNotify3 implements Runnable {

        private final SharedMonitor sharedMonitor;

        public MissNotify3(SharedMonitor sharedMonitor) {
            this.sharedMonitor = sharedMonitor;
        }

        @Override
        public void run() {
            // 先锁住sharedMonitor对象
            synchronized (sharedMonitor) {
                while (!sharedMonitor.condition) {
                    System.out.println("point 1");
                    try {
                        System.out.println("waiting");
                        // 不会再因为先进入循环后,条件改变然后wait阻塞住,条件改变并不能导致循环退出
                        sharedMonitor.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("finished");
            }
        }
    }

    // 错失信号
    static class MissNotify {
        public static void main(String[] args) {
            ExecutorService exec = Executors.newCachedThreadPool();
            SharedMonitor s = new SharedMonitor();
            MissNotify1 mn1 = new MissNotify1(s);
//            MissNotify2 mn2 = new MissNotify2(s);
            MissNotify3 mn3 = new MissNotify3(s);
            exec.execute(mn1);
//            exec.execute(mn2);
            exec.execute(mn3);
            exec.shutdown();
        }
    }
}
