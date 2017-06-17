package cn.tim.thinking.concurrent21.end;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by LuoLiBing on 17/2/21.
 */
public class ThreadWaitNotify {

    private boolean isFirst = true;

    public synchronized void print1() throws InterruptedException {
        while (!Thread.interrupted()) {
            while (!isFirst) {
                wait();
            }
            System.out.println(1);
            isFirst = false;
            notifyAll();
        }
    }

    public synchronized void print2() throws InterruptedException {
        while (!Thread.interrupted()) {
            while (isFirst) {
                wait();
            }
            System.out.println(2);
            isFirst = true;
            notifyAll();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadWaitNotify print = new ThreadWaitNotify();
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.submit(() -> {
            try {
                print.print1();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        exec.submit(() -> {
            try {
                print.print2();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread.sleep(3000);
        exec.shutdown();
    }
}
