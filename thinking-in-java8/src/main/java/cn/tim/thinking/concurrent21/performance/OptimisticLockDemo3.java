package cn.tim.thinking.concurrent21.performance;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by LuoLiBing on 16/11/21.
 *
 */
public class OptimisticLockDemo3 {

    static class FastSimulation {
        static final int N_ELEMENTS = 100_000;

        static final int N_GENES = 30;

        static final int N_EVOLVERS = 2;

        // Atomic
        static final AtomicInteger[][] GRID = new AtomicInteger[N_ELEMENTS][N_GENES];

        // 原生int
        static final int[][] grid = new int[N_ELEMENTS][N_GENES];

        // 锁数组
        static ReentrantLock[] locks = new ReentrantLock[N_ELEMENTS];

        static {
            for(int i = 0; i < N_ELEMENTS; i++) {
                locks[i] = new ReentrantLock();
            }
        }

        // 计数器1
        static AtomicInteger counter1 = new AtomicInteger(0);

        // 计数器2
        static AtomicInteger counter2 = new AtomicInteger(0);

        static Random rand = new Random(47);

        static class Evolver1 implements Runnable {

            @Override
            public void run() {
                while (!Thread.interrupted()) {
                    int element = rand.nextInt(N_ELEMENTS);
                    for(int i = 0; i < N_GENES; i++) {

                        int previous = element - 1;
                        if(previous < 0) {
                            previous = N_ELEMENTS - 1;
                        }
                        int next = element + 1;
                        if(next >= N_ELEMENTS) {
                            next = 0;
                        }

                        // 获取旧值
                        int oldValue = GRID[element][i].get();
                        // 计算新值
                        int newValue = oldValue + GRID[previous][i].get() + GRID[next][i].get();
                        newValue /= 3;

                        // 乐观替换
                        if(!GRID[element][i].compareAndSet(oldValue, newValue)) {
//                            System.out.println("Old value changed from " + oldValue);
                        }

//                        int oldValue, newValue;
//                        do {
//                            int previous = element - 1;
//                            if(previous < 0) {
//                                previous = N_ELEMENTS - 1;
//                            }
//                            int next = element + 1;
//                            if(next >= N_ELEMENTS) {
//                                next = 0;
//                            }
//
//                            // 获取旧值
//                            oldValue = GRID[element][i].get();
//                            // 计算新值
//                            newValue = oldValue + GRID[previous][i].get() + GRID[next][i].get();
//                            newValue /= 3;
//                        } while (!GRID[element][i].compareAndSet(oldValue, newValue));
                    }
                    counter1.incrementAndGet();
                }
            }
        }

        static class Evolver2 implements Runnable {

            @Override
            public void run() {
                while (!Thread.interrupted()) {
                    int element = rand.nextInt(N_ELEMENTS);
                    locks[element].lock();
                    try {
                        for(int i = 0; i < N_GENES; i++) {
                            int previous = element - 1;
                            if(previous < 0) {
                                previous = N_ELEMENTS - 1;
                            }
                            int next = element + 1;
                            if(next >= N_ELEMENTS) {
                                next = 0;
                            }

                            // 获取旧值
                            int oldValue = grid[element][i];
                            // 计算新值
                            int newValue = oldValue + grid[previous][i] + grid[next][i];
                            newValue /= 3;
                            grid[element][i] = newValue;
                        }
                    } finally {
                        locks[element].unlock();
                    }
                    counter2.incrementAndGet();
                }
            }
        }

        public static void main(String[] args) throws IOException {
            ExecutorService exec = Executors.newCachedThreadPool();
            for(int i = 0; i < N_ELEMENTS; i++) {
                for(int j = 0; j < N_GENES; j++) {
                    GRID[i][j] = new AtomicInteger(rand.nextInt(1000));
                }
            }

            for(int i = 0; i < N_ELEMENTS; i++) {
                for(int j = 0; j < N_GENES; j++) {
                    grid[i][j] = rand.nextInt(1000);
                }
            }

            for(int i = 0; i < N_EVOLVERS; i++) {
                exec.execute(new Evolver1());
                exec.execute(new Evolver2());
            }
            System.in.read();
            exec.shutdownNow();
            System.out.println("Count1 : " + counter1.get());
            System.out.println("Count2 : " + counter2.get());
        }
    }
}
