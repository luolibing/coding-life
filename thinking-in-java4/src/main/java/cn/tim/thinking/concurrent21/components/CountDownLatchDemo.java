package cn.tim.thinking.concurrent21.components;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by LuoLiBing on 16/11/7.
 * CountDownLatch是一个允许一个或多个线程等待其他一个或多个线程完成之后再执行的一种同步辅助工具.
 * CountDownLatch通过给定一个count进行初始化,调用await()将阻塞住当前线程,直到count等于0为止.
 * 而通过调用countDown()方法,将使得count递减,当到0时将释放这个latch上的所有await()阻塞.
 * CountDownLatch是一个一次性事件,count不能重置,如果需要能够重置的版本,考虑使用CyclicBarrier,
 *
 * CountDownLatch是一多功能同步工具,可以用于许多不同的目的
 * 1 初始化为count=1的CountDownLatch可以作为一个on/off开关,或者门: 所有调用await()的线程都在门口等着,直到一个线程调用countDown(),大门打开
 * 2 初始化为count=N的CountDownLatch可以使得一个线程等到N个线程执行完他们的工作,或者一些工作执行了N次.
 *
 * CountDownLatch一个有用的属性是不要求线程调用countDown等待计数达到0,然后继续.它只是阻止任何线程继续过去等待,知道所有线程可以通过
 *
 *
 */
public class CountDownLatchDemo {

    // 执行的任务
    static class TaskPortion implements Runnable {
        private static int counter = 0;

        private final int id = counter++;

        private static Random rand = new Random(47);

        private final CountDownLatch latch;

        public TaskPortion(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                doWork();
                // 执行完之后countDown()减1
                latch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void doWork() throws InterruptedException {
            TimeUnit.MILLISECONDS.sleep(rand.nextInt(2000));
            System.out.println(this + " completed");
        }

        public String toString() {
            return String.format("%1$-3d ", id);
        }
    }

    // 等待的线程
    static class WaitingTask implements Runnable {

        private static int counter = 0;

        private final int id = counter++;

        private final CountDownLatch latch;

        public WaitingTask(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                latch.await();
                System.out.println("Latch barrier passed for " + this);
            } catch (InterruptedException e) {
                System.out.println(this + " interrupted");
            }
        }

        public String toString() {
            return String.format("WaitingTask %1$-3d ", id);
        }
    }

    static class CountDownLatchTest {
        public static void main(String[] args) {
            final int SIZE = 100;
            // 通过CountDownLatch锁存器同步waiting线程
            CountDownLatch latch = new CountDownLatch(SIZE);
            ExecutorService exec = Executors.newCachedThreadPool();
            for(int i = 0; i < 10; i++) {
                exec.execute(new WaitingTask(latch));
            }
            for(int i = 0; i < 100; i++) {
                exec.execute(new TaskPortion(latch));
            }
            exec.shutdown();
        }
    }


    /**
     * 典型用例1
     * 两个锁存器,第一个用于开始,同步所有任务的开始,另一个用于同步结束
     */
    static class Driver {
        public static void main(String[] args) throws InterruptedException {
            final int N = 10;
            // 两个锁存器,一个用于开始信号,一个用于结束信号
            CountDownLatch startSignal = new CountDownLatch(1);
            CountDownLatch doneSignal = new CountDownLatch(N);

            // 所有等待线程都阻塞在await()上,准备就绪
            for(int i = 0; i < N; i++) {
                new Thread(new Worker(startSignal, doneSignal)).start();
            }
            // 初始化Driver()
            initializeDriver();
            // 开始信号,所有await()线程同时执行
            startSignal.countDown();
            // Driver做一些自己的事情
            doSomethingElse();
            // 等待所有线程执行完毕
            doneSignal.await();
            System.out.println("Completed All");
        }

        public static void initializeDriver() {
            try {
                TimeUnit.MILLISECONDS.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Driver initialize!!!");
        }

        public static void doSomethingElse() {
            System.out.println("Driver do something else");
        }
    }


    static class Worker implements Runnable {

        private final static Random rand = new Random(100);

        private static int counter = 0;

        private int id = counter ++;

        private final CountDownLatch startSignal;

        private final CountDownLatch doneSignal;

        public Worker(CountDownLatch startSignal, CountDownLatch doneSignal) {
            this.startSignal = startSignal;
            this.doneSignal = doneSignal;
        }

        @Override
        public void run() {
            try {
                startSignal.await();
                doWork();
                doneSignal.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void doWork() throws InterruptedException {
            System.out.println("Worker-" + id + " doWork");
            TimeUnit.MILLISECONDS.sleep(rand.nextInt(2000));
        }
    }

    /**
     * 另外一个典型的应用: 将一个问题拆分成N个部分,通过每个Runnable来描述各个部分,并将这些任务加入到Executor队列中.当所有子部分完成时,协调线程将通过await()
     */
    static class Driver2 {
        public static void main(String[] args) throws InterruptedException {
            final int N = 100;
            CountDownLatch doneSignal = new CountDownLatch(N);
            ExecutorService exec = Executors.newCachedThreadPool();
            for(int i = 0 ; i < N; i++) {
                exec.execute(new WorkerRunnable(doneSignal, i));
            }
            doneSignal.await();
        }
    }

    static class WorkerRunnable implements Runnable {

        private final static Random rand = new Random(100);

        private final CountDownLatch doneSignal;

        private final int i;

        WorkerRunnable(CountDownLatch doneSignal, int i) {
            this.doneSignal = doneSignal;
            this.i = i;
        }

        @Override
        public void run() {
            try {
                doWork();
                doneSignal.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void doWork() throws InterruptedException {
            System.out.println("Worker-" + i + " doWork");
            TimeUnit.MILLISECONDS.sleep(rand.nextInt(2000));
        }
    }

}
