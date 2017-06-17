package cn.tim.thinking.concurrent21.end;

import java.util.concurrent.TimeUnit;

/**
 * Created by LuoLiBing on 16/10/22.
 * 检查中断
 * 当在线程上调用interrupt()时,中断发生的唯一时刻是在任务要进入阻塞操作中,或者已经在阻塞操作内部时(除了不可中断的IO或被阻塞的synchronized方法之外,在其余的例外情况下,你无可事事)
 * 可以通过interrupted()来检查中断状态,这不仅可以告诉你interrupt()是否被掉用过,而且还可以清楚中断状态.
 *
 */
public class CheckInterruptedDemo {

    static class NeedsCleanUp {
        private final int id;

        public NeedsCleanUp(int id) {
            this.id = id;
            System.out.println("NeedsClean up " + id);
        }

        public void cleanup() {
            System.out.println("Cleaning up " + id);
        }
    }

    static class Blocked3 implements Runnable {

        private volatile double d = 0.0;

        @Override
        public void run() {
            try {
                // 判断是否被中断,并且重置中断状态,如果中断状态为true,则重置中断状态为false
                while (!Thread.interrupted()) {
                    // 第一个监测点
                    NeedsCleanUp n1 = new NeedsCleanUp(1);

                    try {
                        System.out.println("Sleeping");
                        // 进入阻塞,抛出interruptedException异常
                        TimeUnit.SECONDS.sleep(1);
                        // 第二个监测点
                        NeedsCleanUp n2 = new NeedsCleanUp(2);
                        try {
                            System.out.println("Calculating");
                            for (int i = 1; i < 2500000; i++) {
                                d = d + (Math.PI + Math.E) / d;
                            }
                            System.out.println("Finish time-consuming operation");
                        } finally {
                            n2.cleanup();
                        }
                    } finally {
                        n1.cleanup();
                    }
                    System.out.println("isInterrupted=" + Thread.currentThread().isInterrupted());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                // 抛出异常之后中断状态被重置为false
                System.out.println("isInterrupted=" + Thread.currentThread().isInterrupted());
                System.out.println("Exiting via InterruptedException");
            }
        }
    }

    static class InterruptingIdiom {
        public static void main(String[] args) throws InterruptedException {
            Thread th = new Thread(new Blocked3());
            th.start();

            TimeUnit.MILLISECONDS.sleep(1000);
            th.interrupt();
        }
    }
}
