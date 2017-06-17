package cn.tim.thinking.concurrent21.deadlock;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by LuoLiBing on 16/11/5.
 * 死锁: 哲学家就餐问题
 * 1 任何两个哲学家都不能成功take()同一根筷子,互斥
 * 2 持有一个等待获取另外一个,在另外一个没获取之前不会主动放弃已有的一个
 * 3 如果一跟筷子被某个哲学家获得,那么另外一个哲学家必须wait(),直到这根筷子的持有者调用drop()可用为止.不可强占
 * 4 循环等待获取
 *
 * 死锁的4个条件
 * 1 互斥条件
 * 2 至少有一个任务它必须持有一个资源且正在等待获取另一个当前被别的任务持有的资源.
 * 3 资源不能被任务抢占,任务必须把资源释放当做普通事件.
 * 4 必须有循环等待,形成一个环
 * 要发生死锁,4个条件必须全部满足; 要防止死锁,只需要破坏其中一个即可,在程序中,防止死锁最容易的方法是破坏第4个条件.
 *
 *
 */
public class DeadLockDemo1 {

    // 筷子表示资源,只能被一个线程所持有,互斥
    public static class Chopstick {
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
    }

    static class Philosopher implements Runnable {

        private Chopstick left;

        private Chopstick right;

        private final int id;

        private final int ponderFactor;

        private Random rand = new Random(47);

        // 休息,只有当ponderFactor等于0的时候才开始休息
        private void pause() throws InterruptedException {
            if(ponderFactor == 0) return;
            TimeUnit.MILLISECONDS.sleep(rand.nextInt(ponderFactor * 250));
        }

        public Philosopher(Chopstick left, Chopstick right, int ident, int ponder) {
            this.left = left;
            this.right = right;
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
                    System.out.println(this + " grabbing right");
                    // 先拿右边的
                    right.take();
                    System.out.println(this + " grabbing left");
                    // 再拿左边的筷子
                    left.take();
                    // 两个都拿到就餐
                    System.out.println(this + " eating");
                    // 暂停
                    pause();
                    // 依次放弃右边左边
                    right.drop();
                    left.drop();
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
        public static void main(String[] args) throws IOException {
            // 死锁因子,如果花在思考上的时间非常少,那么在chopstick上产生竞争越多,死锁发生得越快
            int ponder = 1;
            int size = 5;
            ExecutorService exec = Executors.newCachedThreadPool();
            Chopstick[] chopsticks = new Chopstick[size];
            for(int i = 0; i < size; i++) {
                chopsticks[i] = new Chopstick();
            }
            for(int i = 0; i < size; i++) {
                exec.execute(new Philosopher(chopsticks[i], chopsticks[(i + 1) % size], i, ponder));
            }
            System.out.println("Press 'Enter' to quit");
            System.in.read();
            exec.shutdownNow();
        }
    }

    // 通过破坏第4个条件防止死锁,最后哲学家先拿左边再拿右边
    static class FixedDiningPhilosophers {
        public static void main(String[] args) throws IOException {
            int ponder = 0;
            int size = 5;
            ExecutorService exec = Executors.newCachedThreadPool();
            Chopstick[] chopsticks = new Chopstick[size];
            for(int i = 0; i < size; i++) {
                chopsticks[i] = new Chopstick();
            }
            for(int i = 0; i < size; i++) {
                if(i == size -1) {
                    exec.execute(new Philosopher(chopsticks[0], chopsticks[i], i, ponder));
                } else {
                    exec.execute(new Philosopher(chopsticks[i], chopsticks[(i + 1) % size], i, ponder));
                }
            }
            System.out.println("Press 'Enter' to quit");
            System.in.read();
            exec.shutdownNow();
        }
    }
}
