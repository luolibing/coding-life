package cn.tim.thinking.concurrent21.resource;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by LuoLiBing on 16/10/17.
 */
public class EvenCheckerDemo {

    static abstract class IntGenerator {
        private volatile boolean canceled = false;
        public abstract int next();
        public void cancel() { canceled = true; }
        public boolean isCanceled() { return canceled; }
    }

    static class EventChecker implements Runnable {
        private IntGenerator generator;
        private final int id;

        public EventChecker(IntGenerator generator, int id) {
            this.generator = generator;
            this.id = id;
        }

        @Override
        public void run() {
            while (!generator.isCanceled()) {
                int val = generator.next();
                while (val % 2 != 0) {
                    System.out.println(val + " not even!");
                    generator.cancel();
                }
            }
        }

        public static void test(IntGenerator generator, int count) {

            System.out.println("Press Control-C to exit");
            ExecutorService exec = Executors.newCachedThreadPool();
            for(int i = 0; i < count; i++) {
                exec.execute(new EventChecker(generator, i));
            }
            exec.shutdown();
        }

        public static void test(IntGenerator gp) {
            test(gp, 10);
        }
    }

    static class EvenGenerator extends IntGenerator {

        private int currentEvenValue = 0;

        @Override
        public int next() {
            // 正常情况下单线程不会出现问题, 每次都是+2,自然都是偶数
            // 而如果多线程情况下,currentEvenValue++的过程中,其他的线程调用next()这个时候顺序就会错乱
            // java自增本身就需要多个操作步骤,并且自增操作过程中任务可能会被线程机制挂起,也就是说递增不是原子性的操作
            // 因此,如果不保护任务,及时单一的递增也不是安全的.
            ++ currentEvenValue;
            ++ currentEvenValue;
            return currentEvenValue;
        }

        public static void main(String[] args) {
            EventChecker.test(new EvenGenerator(), 10);
        }
    }


}
