package cn.tim.thinking.concurrent21.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by LuoLiBing on 16/11/9.
 * DelayQueue
 * 无界BlockingQueue,优先级队列的一种变体,用于放置实现了Delayed接口的对象,其中的对象只有到期时才能从队列中取走
 * 这种队列是有序的,队头对象延迟到期的时间最长,如果没有任何延迟到期,那么久不会有任何头元素,poll()返回null
 *
 */
public class DelayQueueDemo {

    // 延迟任务
    static class DelayedTask implements Runnable, Delayed {

        private static int counter = 0;

        private final int id = counter ++;

        private final int delta;

        private final long trigger;

        protected static List<DelayedTask> sequence = new ArrayList<>();

        public DelayedTask(int delayInMilliseconds) {
            delta = delayInMilliseconds;
            trigger = System.nanoTime() + TimeUnit.NANOSECONDS.convert(delta, TimeUnit.MILLISECONDS);
            sequence.add(this);
        }

        // 延迟时长
        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(trigger - System.nanoTime(), TimeUnit.NANOSECONDS);
        }

        @Override
        public int compareTo(Delayed arg) {
            DelayedTask that = (DelayedTask) arg;
            if(trigger < that.trigger) return -1;
            if(trigger > that.trigger) return 1;
            return 0;
        }

        @Override
        public void run() {
            System.out.println(this + " ");
        }

        public String toString() {
            return String.format("[%1$-4d]", delta) + " Task " + id;
        }

        public String summary() {
            return "(" + id + ":" + delta + ")";
        }

        // 结束任务
        public static class EndSentinel extends DelayedTask {

            private ExecutorService exec;

            public EndSentinel(int delay, ExecutorService e) {
                super(delay);
                exec = e;
            }

            @Override
            public void run() {
                for(DelayedTask pt : sequence) {
                    System.out.println(pt.summary() + " ");
                }
                System.out.println();
                System.out.println(this + " Calling shutdownNow()");
                exec.shutdown();
            }
        }

        // 消费者线程
        public static class DelayedTaskConsumer implements Runnable {

            private DelayQueue<DelayedTask> queue;

            public DelayedTaskConsumer(DelayQueue<DelayedTask> q) {
                this.queue = q;
            }

            @Override
            public void run() {
                try {
                    while (!Thread.interrupted()) {
                        queue.take().run();
                    }
                } catch (InterruptedException e) {

                }
                System.out.println("Finished DelayedTaskConsumer");
            }
        }

        public static class DelayQueueDemo1 {
            public static void main(String[] args) {
                Random rand = new Random(47);
                ExecutorService exec = Executors.newCachedThreadPool();
                DelayQueue<DelayedTask> queue = new DelayQueue<>();
                for(int i = 0; i < 20; i++) {
                    queue.put(new DelayedTask(rand.nextInt(5000)));
                }
                queue.add(new DelayedTask.EndSentinel(5000, exec));
                exec.execute(new DelayedTaskConsumer(queue));
            }
        }
    }
}
