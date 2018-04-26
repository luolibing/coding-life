package cn.tim.thinking.concurrent21.deadlock;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by LuoLiBing on 16/11/7.
 */
public class DeadLockDemo2 {

    // 筷子表示资源,只能被一个线程所持有,互斥
    public static class Chopstick {
        private static int count = 0;

        private final int id = count++;

        private boolean taken = false;

        public synchronized void take() throws InterruptedException {
            while (taken) {
                wait();
            }
            taken = true;
        }

        public synchronized void drop() {
            taken = false;
            notifyAll();
        }

        public String toString() {
            return "Chopstock " + id;
        }
    }

    public static class ChopstickQueue extends LinkedBlockingQueue<Chopstick> {}

    public static class ChopstickBin {
        private ChopstickQueue queue = new ChopstickQueue();

        public Chopstick take() throws InterruptedException {
            return queue.take();
        }

        public void put(Chopstick chopstick) throws InterruptedException {
            queue.put(chopstick);
        }
    }

    static class Philosopher implements Runnable {

        private ChopstickBin chopstickBin;

        private final int id;

        private final int ponderFactor;

        private Random rand = new Random(47);

        // 休息,只有当ponderFactor等于0的时候才开始休息
        private void pause() throws InterruptedException {
            if(ponderFactor == 0) return;
            TimeUnit.MILLISECONDS.sleep(rand.nextInt(ponderFactor * 250));
        }

        public Philosopher(ChopstickBin chopstickBin, int ident, int ponder) {
            this.chopstickBin = chopstickBin;
            this.id = ident;
            this.ponderFactor = ponder;
        }

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    System.out.println(this + " thinking");
                    // 思考休息
                    pause();
                    // 先拿第一个
                    Chopstick first = chopstickBin.take();
                    System.out.println(this + " has " + first + " waiting for another one");
                    // 再拿左边的筷子
                    Chopstick second = chopstickBin.take();
                    // 两个都拿到就餐
                    System.out.println(this + " has " + second);
                    System.out.println(this + " eating");
                    // 暂停
                    pause();
                    // 依次放弃右边左边
                    chopstickBin.put(second);
                    chopstickBin.put(first);
                }
            } catch (InterruptedException e) {
                System.out.println(this + " exiting via interrupt");
            }
        }

        public String toString() {
            return "Philosopher " + id;
        }
    }

    static class DeadlockingDiningPhilosophers {
        public static void main(String[] args) throws IOException, InterruptedException {
            int ponder = 0;
            int size = 5;
            ExecutorService exec = Executors.newCachedThreadPool();
            ChopstickBin bin = new ChopstickBin();
            for(int i = 0; i < size; i++) {
                bin.put( new Chopstick());

            }
            for(int i = 0; i < size; i++) {
                exec.execute(new Philosopher(bin, i, ponder));
            }
            System.out.println("Press 'Enter' to quit");
            System.in.read();
            exec.shutdownNow();
        }
    }
}
