package cn.tim.thinking.concurrent21.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by LuoLiBing on 16/10/12.
 * 线程优先级
 *
 * 线程的优先级将该线程的重要性传递给调度器,尽管CPU处理现有线程集的顺序不确定的,但是调度器将倾向于让优先级最高的线程先执行.
 * 优先级高的线程,执行频率会比低优先级的执行频率高
 * 在绝大多数时间里,所有线程都应该以默认的优先级运行,试图操纵线程优先级通常是一种错误.
 *
 * 优先级
 * Jdk有10个优先级,而Thread中只使用了3个优先级,MAX_PRIORITY(10),NORM_PRIORITY(5)和MIN_PRIORITY(1)
 *
 */
public class PrioritiesDemo {

    static class SimplePriorities implements Runnable {

        private static int count = 0;

        private int countDown = 5;

        private volatile double d;

        private int id = count ++;

        private int priority;

        public SimplePriorities(int priority) {
            this.priority = priority;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 通过Thread.currentThread()方法来获取驱动该任务的线程引用
            Thread.currentThread().setPriority(priority);
            while (true) {
                for(int i = 1; i < 100000; i++) {
                    d += (Math.PI + Math.E) / (double) i;
                    if(i % 1000 == 0) {
                        Thread.yield();
                    }
                }
                System.out.println(this);
                if(--countDown == 0) return;
            }
        }

        @Override
        public String toString() {
            return "id:" + id + ", " + Thread.currentThread() + ": " + countDown;
        }
    }

    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        for(int i = 0; i < 5; i++) {
            exec.execute(new SimplePriorities(Thread.MIN_PRIORITY));
        }

        exec.execute(new SimplePriorities(Thread.MAX_PRIORITY));
        exec.shutdown();
    }
}
