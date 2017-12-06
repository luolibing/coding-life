package cn.tim.thinking.concurrent21.consumer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by LuoLiBing on 16/10/24.
 * 显示lock和condition
 *
 * 单个Lock将产生一个Condition对象,这个对象被用来管理任务间的通信,但是这个Condition对象不包含任何有关处理状态的信息,因此你需要管理额外的表示处理状态信息
 * lock.lock之后必须紧跟一个try-finally子句,用来确保所有情况下都可以释放锁,在使用内建版本时,任务在调用await,signal或signalAll之前必须拥有这个锁
 */
public class LockAndConditionDemo {

    /**
     * 共享资源: car
     */
    static class Car {
        // 打蜡
        private boolean waxOn = false;

        // 锁
        private Lock lock = new ReentrantLock();

        // 条件
        private Condition condition = lock.newCondition();


        // 打蜡
        public void waxed() {
            lock.lock();
            try {
                waxOn = true;
                // notifyAll(); 使用signalAll代替notifyAll
                condition.signalAll();
            } finally {
                lock.unlock();
            }
        }

        // 抛光,并通知其他程序
        public void buffed() throws InterruptedException {
            lock.lock();
            try {
                waxOn = false;
                condition.signalAll();
            } finally {
                lock.unlock();
            }
        }

        // 等待打蜡
        public void waitForWaxing() throws InterruptedException {
            lock.lock();
            try {
                while (!waxOn) {
                    condition.await();
                }
            } finally {
                lock.unlock();
            }
        }

        // 等待抛光
        public void waitForBuffing() throws InterruptedException {
            lock.lock();
            try {
                while (waxOn) {
                    condition.await();
                }
            } finally {
                lock.unlock();
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
}
