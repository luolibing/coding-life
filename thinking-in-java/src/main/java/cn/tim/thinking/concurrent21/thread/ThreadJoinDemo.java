package cn.tim.thinking.concurrent21.thread;

import java.util.concurrent.TimeUnit;

/**
 * Created by LuoLiBing on 16/10/17.
 * 在A线程方法里调用另外一个线程B.join方法,则A需要等待B执行完成才能继续执行,A线程会被暂时挂起. 可以使用在A上执行中断,继续执行的B的任务
 * CyclicBarrier栅栏可能会比线程类库中的join()更加适合
 */
public class ThreadJoinDemo {

    static class Sleeper extends Thread {
        private int duration;
        public Sleeper(String name, int duration) {
            super(name);
            this.duration = duration;
            start();
        }

        @Override
        public void run() {
            try {
                TimeUnit.MILLISECONDS.sleep(duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("sleep finish");
        }
    }

    static class Joiner extends Thread {
        private Sleeper sleeper;
        public Joiner(Sleeper sleeper) {
            this.sleeper = sleeper;
            start();
        }

        @Override
        public void run() {
            try {
                System.out.println("start joined");
                // join线程被挂起,直到sleeper线程被执行完成才能继续向下执行, 带超时的join
                sleeper.join(10000);
            } catch (InterruptedException e) {
                System.err.println("interrupted");
            }
            System.out.println("finish joined");
        }
    }

    public static void main(String[] args) {
        Sleeper sleep = new Sleeper("sleep", 10000);
        Joiner joiner = new Joiner(sleep);
        sleep.interrupt();
    }

}
