package cn.tim.thinking.concurrent21.end;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by LuoLiBing on 16/11/8.
 */
public class GardenDemo1 {

    // 共享的主计数器
    static class Count {
        private int count;
        private Random rand = new Random(100);
        // 使用同步保护count
        public synchronized int increment() {
            int temp = count;
            if(rand.nextBoolean()) {
                // 使用Thread.yield()在做多线程并发测试时,增大发生错误的几率
                Thread.yield();
            }
            return (count = ++ temp);
        }
        public synchronized int getValue() {
            return count;
        }
    }

    // 入口
    static class Entrance implements Runnable {
        private static Random rand = new Random(100);
        private static Count count = new Count();
        private final CountDownLatch latch;
        private static List<Entrance> entrances = new ArrayList<>();
        private int number = 0;
        private final int id;
        private static volatile boolean canceled = false;

        // 关闭所有的入口
        public static void cancel() { canceled = true; }

        public Entrance(int id, CountDownLatch latch) {
            this.id = id;
            entrances.add(this);
            this.latch = latch;
        }

        @Override
        public void run() {
            while (!canceled) {
                synchronized (this) {
                    // 添加自身的计数器
                    ++number;
                }
                // 总计数器自增
                System.out.println(this + " Total: " + count.increment());
                try {
                    TimeUnit.MILLISECONDS.sleep(rand.nextInt(200));
                } catch (InterruptedException e) {
                    System.out.println("sleep interrupted");
                    break;
                }
            }
            latch.countDown();
            System.out.println("Stopping " + this);
        }

        public synchronized int getValue() { return number; }

        public String toString() {
            return "Entrance " + id + ": " + getValue();
        }

        public static int getTotalCount() {
            return count.getValue();
        }

        // 计算所有公园人数总和
        public static int sumEntrances() {
            int sum = 0;
            for(Entrance entrance : entrances) {
                sum += entrance.getValue();
            }
            return sum;
        }
    }


    static class OrnametalGarden {
        public static void main(String[] args) throws InterruptedException {
            ExecutorService exec = Executors.newCachedThreadPool();
            final int SIZE = 10;
            CountDownLatch latch = new CountDownLatch(SIZE);
            List<Future<?>> futures = new ArrayList<>();
            for(int i = 0; i < SIZE; i++) {
                futures.add(exec.submit(new Entrance(i, latch)));
            }
            TimeUnit.SECONDS.sleep(5);
            // 关闭所有入口
            Entrance.cancel();
            exec.shutdown();

            latch.await();
            System.out.println("Total: " + Entrance.getTotalCount());

            System.out.println();
            System.out.println("Entrance list:");
            for(Entrance entrance : Entrance.entrances) {
                System.out.println(entrance);
            }
        }
    }
}
