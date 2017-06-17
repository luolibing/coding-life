package cn.tim.thinking.concurrent21.consumer;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by LuoLiBing on 16/10/31.
 * 不同的线程之间通过queue进行通信
 *
 */
public class BlockingQueueDemo2 {

    // 吐司
    static class Toast {
        // 状态
        public enum Status { DRY, BUTTERED, JAMMED }

        private Status status = Status.DRY;

        private final int id;

        public Toast(int idn) {
            id = idn;
        }

        public void butter() {
            status = Status.BUTTERED;
        }

        public void jam() {
            status = Status.JAMMED;
        }

        public Status getStatus() {
            return status;
        }

        public int getId() {
            return id;
        }

        public String toString() {
            return "Toast " + id + ": " + status;
        }
    }


    static class ToastQueue extends LinkedBlockingQueue<Toast> {

    }


    /**
     * 吐司生产机
     */
    static class Toaster implements Runnable {

        private ToastQueue toastQueue;

        private int count = 0;

        private Random rand = new Random(47);

        public Toaster(ToastQueue tq) {
            this.toastQueue = tq;
        }

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    TimeUnit.MILLISECONDS.sleep(100 + rand.nextInt(500));
                    // 创建吐司
                    Toast t = new Toast(count++);
                    System.out.println(t);
                    toastQueue.put(t);
                }
            } catch (InterruptedException e) {
                System.out.println("Toast interrupted");
            }
            System.out.println("Toaster off");
        }
    }


    /**
     * 涂黄油
     */
    static class Butterer implements Runnable {

        // 干的吐司,涂了黄油的吐司
        private ToastQueue dryQueue, butteredQueue;

        public Butterer(ToastQueue dry, ToastQueue buttered) {
            this.dryQueue = dry;
            this.butteredQueue = buttered;
        }

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    // 从干吐司队列中取出
                    Toast t = dryQueue.take();
                    // 涂黄油
                    t.butter();
                    System.out.println(t);
                    // 将涂了黄油的吐司加入到涂了黄油的队列中
                    butteredQueue.put(t);
                }
            } catch (InterruptedException e) {
                System.out.println("Butterer interrupted");
            }
            System.out.println("Butterer interrupted");
        }
    }


    /**
     * 涂果酱
     */
    static class Jammer implements Runnable {

        private ToastQueue butteredQueue, finishedQueue;

        public Jammer(ToastQueue buttered, ToastQueue finished) {
            this.butteredQueue = buttered;
            this.finishedQueue = finished;
        }

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    // 从butteredQueue中取出
                    Toast toast = butteredQueue.take();
                    // 涂果酱
                    toast.jam();
                    System.out.println(toast);
                    // 加入到完成队列中
                    finishedQueue.put(toast);
                }
            } catch (InterruptedException e) {
                System.out.println("Jammer interrupted");
            }
            System.out.println("Jammer off");
        }
    }


    /**
     * 吃
     */
    static class Eater implements Runnable {

        private ToastQueue finishedQueue;

        private int counter = 0;

        public Eater(ToastQueue finished) {
            this.finishedQueue = finished;
        }

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    Toast toast = finishedQueue.take();
                    if(toast.getId() != counter ++ || toast.getStatus() != Toast.Status.JAMMED) {
                        System.out.println(">>>> Error: " + toast);
                        System.exit(1);
                    } else {
                        System.out.println("Chomp! " + toast);
                    }
                }
            } catch (InterruptedException e) {
                System.out.println("Eater interrupted");
            }
            System.out.println("Eater off");
        }
    }


    static class ToastOMatic {
        public static void main(String[] args) throws InterruptedException {
            ToastQueue dryQueue         = new ToastQueue(),
                       butteredQueue    = new ToastQueue(),
                       finishedQueue    = new ToastQueue();
            ExecutorService exec = Executors.newCachedThreadPool();
            exec.execute(new Toaster(dryQueue));
            exec.execute(new Butterer(dryQueue, butteredQueue));
            exec.execute(new Jammer(butteredQueue, finishedQueue));
            exec.execute(new Eater(finishedQueue));
            TimeUnit.SECONDS.sleep(5);
            exec.shutdownNow();
        }
    }
}
