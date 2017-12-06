package cn.tim.thinking.concurrent21.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by LuoLiBing on 16/11/10.
 * 优先级队列
 */
public class PriorityBlockingQueueDemo {

    /**
     * 优先级队列任务,因为需要排序,所以需要实现compareTo
     */
    static class PrioritizedTask implements Runnable, Comparable<PrioritizedTask> {

        private Random rand = new Random(47);

        private static int counter = 0;

        private final int id = counter++;

        private final int priority;

        // sequence保留了任务加入的顺序
        protected static List<PrioritizedTask> sequence = new ArrayList<>();

        public PrioritizedTask(int priority) {
            this.priority = priority;
            sequence.add(this);
        }

        /**
         * 越大的数优先级越高
         * @param o
         * @return
         */
        @Override
        public int compareTo(PrioritizedTask o) {
            return priority < o.priority ? 1: (priority > o.priority ? -1 : 0);
        }

        @Override
        public void run() {
            try {
                TimeUnit.MILLISECONDS.sleep(rand.nextInt(250));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(this);
        }

        public String toString() {
            return String.format("[%1$-3d]", priority) + " Task " + id;
        }

        public String summary() {
            return "(" + id + ":" + priority + ")";
        }


        /**
         * 结束哨兵: 到达末尾,结束掉ExecuteService
         */
        static class EndSentinel extends PrioritizedTask {

            private ExecutorService exec;

            public EndSentinel(ExecutorService e) {
                super(-1);
                exec = e;
            }

            @Override
            public void run() {
                int count = 0;
                for(PrioritizedTask pt : sequence) {
                    System.out.println(pt.summary());
                    if(++count % 5 == 0) {
                        System.out.println();
                    }
                }
                System.out.println();
                System.out.println(this + " Calling shutdownNow()");
                exec.shutdownNow();
            }
        }
    }

    /**
     * 优先级队列生产者,不停地往队列里面添加
     */
    static class PrioritizedTaskProducer implements Runnable {

        private Random rand = new Random(47);

        private Queue<Runnable> queue;

        private ExecutorService exec;

        public PrioritizedTaskProducer(Queue<Runnable> q, ExecutorService e) {
            queue = q;
            exec = e;
        }

        @Override
        public void run() {
            for(int i = 0; i < 20; i++) {
                // 随机低优先级任务
                queue.add(new PrioritizedTask(rand.nextInt(10)));
                Thread.yield();
            }

            try {
                for(int i = 0; i < 10; i++) {
                    // 固定高优先级10的任务
                    TimeUnit.MILLISECONDS.sleep(250);
                    queue.add(new PrioritizedTask(10));
                }

                // 由低到高优先级
                for(int i = 0; i < 10; i++) {
                    queue.add(new PrioritizedTask(i));
                }

                // 最低优先级的结束哨兵任务
                queue.add(new PrioritizedTask.EndSentinel(exec));
            } catch (InterruptedException e) {

            }
            System.out.println("Finished PrioritizedTaskProducer");
        }
    }

    /**
     * 优先级队列消费者
     */
    static class PrioritizedTaskConsumer implements Runnable {

        private PriorityBlockingQueue<Runnable> q;

        public PrioritizedTaskConsumer(PriorityBlockingQueue<Runnable> q) {
            this.q = q;
        }

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    // 根据优先级从优先级队列里面取出任务运行
                    q.take().run();
                }
            } catch (InterruptedException e) {

            }
            System.out.println("Finished PrioritizedTaskConsumer");
        }
    }

    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        PriorityBlockingQueue<Runnable> queue = new PriorityBlockingQueue<>();
        exec.execute(new PrioritizedTaskProducer(queue, exec));
        exec.execute(new PrioritizedTaskConsumer(queue));
    }
}
