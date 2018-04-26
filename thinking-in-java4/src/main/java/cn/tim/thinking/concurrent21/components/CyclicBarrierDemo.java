package cn.tim.thinking.concurrent21.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by LuoLiBing on 16/11/8.
 * CyclicBarrier适用的情况: 创建一组任务,并行地执行工作,然后再进行下一步之前等待,直到所有任务都完成(类似join),使得所有并行任务都将在栅栏处列队,因此可以一直地向前移动.非常想CountDownLatch,只是CountDownLatch只触发一次事件,而CyclicBarrier可以多次重用
 *
 * CyclicBarrier:
 * 1 允许一组线程彼此等待全部到达指定的公共集合点,CyclicBarrier在一些指定数量线程必须互相等待的场景十分有用,之所以称为cyclic的barrier,是因为线程被释放之后可以重复利用.
 * 2 支持一个可选的Runnable Command当每次都到达栅栏点是执行,当最后一个线程到达栅栏点后,并且没有任何线程被释放之前执行.这个barrier action十分有用,在所有线程继续开始之前更新共享状态
 * 3 使用Barrier的一个典型场景,分解合并操作,将一个大的任务分解为小任务,然后再合并整个这些子任务产生的结果.但是需要一个出口
 * 4 如果屏障方法不依赖于await的各个线程,那么await中的任何一个线程都可以执行屏障动作,可以根据await()中的索引值来判断由谁来执行屏障动作. barrier.await() == 0
 *
 */
public class CyclicBarrierDemo {


    static class Horse implements Runnable {
        private static int counter = 0;
        private final int id = counter++;
        // 多少步
        private int strides = 0;
        private static Random rand = new Random(47);
        private static CyclicBarrier barrier;
        public Horse(CyclicBarrier b) {
            barrier = b;
        }

        public synchronized int getStrides() {
            return strides;
        }

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    synchronized (this) {
                        // 随机向前走0,1,2步
                        strides += rand.nextInt(3);
                    }
                    // 等待所有horse都走了一次之后再一起走下一步
                    barrier.await();
                }
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }

        public String toString() {
            return "Horse " + Thread.currentThread().getId() + " ";
        }

        public String tracks() {
            StringBuilder s = new StringBuilder();
            for(int i = 0; i < getStrides() - 1; i++) {
                s.append("*");
            }
            s.append(id);
            return s.toString();
        }
    }


    public static class HorseRace {
        final static int FINISH_LINE = 75;

        private List<Horse> horses = new ArrayList<>();

        private ExecutorService exec = Executors.newCachedThreadPool();

        private CyclicBarrier barrier;

        public HorseRace(int nHorses, final int pause) {
            // new CyclicBarrier(count,(runnable)) runnable栅栏动作,当计数值到达0时自动执行,这是CyclicBarrier和CountDownLatch之间的另外一个区别
            barrier = new CyclicBarrier(10, () -> {
                StringBuilder s = new StringBuilder();
                for(int i = 0; i < FINISH_LINE; i++) {
                    s.append("=");
                }
                System.out.print(s);
                System.out.println();
                System.out.println();

                for(Horse horse : horses) {
                    System.out.println(horse.tracks());
                }

                for(Horse horse : horses) {
                    if(horse.getStrides() >= FINISH_LINE) {
                        System.out.println(horse + "won!");
                        exec.shutdownNow();
                        return;
                    }
                }

                try {
                    TimeUnit.MILLISECONDS.sleep(pause);
                } catch (InterruptedException e) {
                    System.out.println("barrier-action sleep interrupted");
                }
            });

            for(int i = 0; i < nHorses; i++) {
                Horse horse = new Horse(barrier);
                horses.add(horse);
                exec.execute(horse);
            }
        }

        public static void main(String[] args) {
            int nHorses = 10;
            int pause = 200;
            new HorseRace(nHorses, pause);
        }
    }


    static class Solver {
        final int N;
        final float[][] data;
        final CyclicBarrier barrier;
        boolean done = false;

        class Worker implements Runnable {
            int myRow;
            Worker(int row) {
                myRow = row;
            }
            @Override
            public void run() {
                while (!done()) {
                    processRow(myRow);
                    try {
                        barrier.await();
                    } catch (InterruptedException | BrokenBarrierException e) {
                        return;
                    }
                }
            }

            public boolean done() {
                return done;
            }

            private void processRow(int myRow) {
                for(int i = 0; i < data[myRow].length; i++) {
                    data[myRow][i] = data[myRow][i] * 2;
                }
                System.out.println("Process " + myRow);
            }
        }

        public Solver(float[][] matrix) throws InterruptedException {
            data = matrix;
            N = matrix.length;
            Runnable barrierAction = () -> {

                for(int i = 0; i < data.length; i++) {
                    float sum = 0;
                    for(int j = 0; j < data[i].length; j++) {
                        sum += data[i][j];
                        data[i][j] = 1;
                    }
                    data[i][0] = sum;
                    if(Float.compare(sum, 1000000f) == 1) {
                        done = true;
                    }
                }
                print();
            };
            barrier = new CyclicBarrier(N, barrierAction);

            List<Thread> threads = new ArrayList<>(N);
            for(int i = 0; i < N; i++) {
                Thread thread = new Thread(new Worker(i));
                threads.add(thread);
                thread.start();
            }

            for(Thread thread : threads) {
                thread.join();
            }
        }

        private void print() {
            System.out.println("------------------------");
            for(int i = 0; i < data.length; i++) {
                for(int j = 0; j < data[i].length; j++) {
                    System.out.print(data[i][j] + " ");
                }
                System.out.println();
            }
        }

        public static void main(String[] args) throws InterruptedException {
            float[][] matrix = new float[][]{{1, 1, 1, 1, 1}, {1, 1, 1, 1, 1}, {1, 1, 1, 1, 1}, {1, 1, 1, 1, 1}, {1, 1, 1, 1, 1}};
            new Solver(matrix);
        }
    }

}
