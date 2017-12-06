package cn.tim.thinking.concurrent21.cooperation;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by LuoLiBing on 16/10/22.
 * notify和notifyAll 只能唤醒在等待这个特定锁的任务.
 *
 * 可能会有多个任务在单个对象上处于wait状态,因此调用notifyAll比notify更安全,如果知道只有一个任务实际处于wait状态,使用notify而不是notifyAll是一种优化
 * 使用notify时,众多等待的同一个锁的任务中只有会被唤醒,因此如果希望使用notify,就必须保证被唤醒的是恰当的任务.
 * 为了使用notify,所有任务必须等待相同的条件,如果有多个任务等待不同的条件,就不知道是否唤醒了恰当的任务,唤醒所有的任务,然后可以根据对应的条件判断是继续执行还是继续wait
 * 最后这些限制对所有可能存在的子类都必须起作用,如果有任何一条不满足,就必须使用notifyAll而不是notify
 *
 */
public class NotifyDemo {

    static class Blocker {
        synchronized void waitingCall() {
            try {
                while (!Thread.interrupted()) {
                    wait();
                    System.out.println(Thread.currentThread());
                }
            } catch (InterruptedException e) {

            }
        }

        synchronized void prod() {
            notify();
        }

        synchronized void prodAll() {
            notifyAll();
        }
    }

    static class Task implements Runnable {
        static Blocker blocker = new Blocker();

        @Override
        public void run() {
            blocker.waitingCall();
        }
    }

    static class Task2 implements Runnable {
        static Blocker blocker = new Blocker();

        @Override
        public void run() {
            blocker.waitingCall();
        }
    }

    static class NotifyVsNotifyAll {
        public static void main(String[] args) throws InterruptedException {
            ExecutorService exec = Executors.newCachedThreadPool();
            for(int i = 0; i < 5; i++) {
                exec.execute(new Task());
            }
            exec.execute(new Task2());
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                boolean prod = true;
                @Override
                public void run() {
                    if(prod) {
                        System.out.print("\nnotify() ");
                        Task.blocker.prod();
                        prod = false;
                    } else {
                        System.out.print("\nnotifyAll() ");
                        Task.blocker.prodAll();
                        prod = true;
                    }
                }
            }, 400, 400);

            TimeUnit.SECONDS.sleep(5);
            timer.cancel();
            System.out.println("\nTimer canceled");

        }
    }
}
