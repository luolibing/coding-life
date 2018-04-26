package cn.tim.thinking.concurrent21.performance;

import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by LuoLiBing on 16/11/22.
 * 活动对象
 * 每个对象都维护着它自己的工作器线程和消息队列,并且所有对这种对象的请求都将进入队列排队,任何时刻都都只能运行其中的一个.
 * 有了活动对象,我们就可以串行化消息而不是方法,这意味着不再需要防备一个任务在循环的中间被中断这种问题
 *
 * 活动对象的好处
 * 1 每个对象都可以拥有自己的工作线程
 * 2 每个对象都维护队它自己的域的全部控制权
 * 3 所有在活动对象之间的通信都将以在这些对象之间的消息形式发生
 * 4 活动对象之间的所有消息都要排队
 *
 */
public class ActiveObjectDemo1 {

    static class ActiveObject {

        // 任何时刻只有一个线程运行的调度器,请求都排队
        private ExecutorService exec = Executors.newSingleThreadExecutor();

        private Random rand = new Random(47);

        private void pause(int factor) {
            try {
                TimeUnit.MILLISECONDS.sleep(100 + rand.nextInt(factor));
            } catch (InterruptedException e) {
                System.out.println("sleep() interrupted");
            }
        }

        public Future<Integer> calculateInt(final int x, final int y) {
            return exec.submit(() -> {
                System.out.println("Starting " + x + " + " + y);
                pause(500);
                return x + y;
            });
        }

        public Future<Float> calculateFloat(final float x, final float y) {
            return exec.submit(() -> {
                System.out.println("Starting " + x + " + " + y);
                pause(500);
                return x + y;
            });
        }

        public void printDocument(final String s) {
            exec.execute(() -> {
                System.out.println("printing document " + s);
                pause(1000);
                System.out.println("document " + s + " printed");
            });
        }

        public void shutdown() {
            exec.shutdown();
        }

        public static void main(String[] args) {
            ActiveObject activeObject = new ActiveObject();
            List<Future<?>> results = new CopyOnWriteArrayList<>();
            for(float f = 0.0f; f < 1.0f; f+=0.2f) {
                results.add(activeObject.calculateFloat(f, f));
            }

            for(int i = 0; i < 5; i++) {
                results.add(activeObject.calculateInt(i, i));
                activeObject.printDocument("Doc_" + i);
            }
            System.out.println("All asynch call made");
            while (results.size() > 0) {
                for(Future<?> future : results) {
                    try {
                        System.out.println(future.get());
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                    results.remove(future);
                }
            }
            activeObject.shutdown();
        }
    }
}
