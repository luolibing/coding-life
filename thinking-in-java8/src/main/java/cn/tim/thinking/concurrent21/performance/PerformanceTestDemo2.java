package cn.tim.thinking.concurrent21.performance;

import org.junit.Test;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by LuoLiBing on 16/11/18.
 * 性能测试方式:
 * 互斥场景,多个任务尝试着访问互斥代码区
 * 拒绝优化,使用读写任务分别访问,实现预加载一个大型的随机int数组
 *
 * 1 synchronized性能不稳定,而lock和Atomic大体相对稳定,synchronized开销变化范围太大性能 atomic > lock > synchronized
 * 2 互斥方法的方法体应该尽量小,只互斥哪些必须互斥的部分,如果互斥体太大,Lock的性能优势也就会被淹没
 * 3 synchronized代码可读性比Lock强很多,所以在性能不是主要问题时,不必过早优化使用Lock,从而保证代码可读性
 * 4 免锁容器
 *      在非多线程环境下,使用非锁容量.想Vector和Hashtable早期容量大多都有synchronized方法,而在新的java容器类库是不同步的,如果需要使用同步容器,可以使用Collections.synchronizedSet(collection)这种同步容器进行装饰,但是底层还是使用的synchronized
 *      在Java5中开始使用新的多线程容器,通用的策略是:对容器的修改可以与读取操作同时发生,只要读者只能看到完成修改的结果即可.修改时在容器数据结果的某个部分的一个单独的副本(或者整个副本)上执行,修改过程是不可视的.修改完在进行,被修改的数据和主数据结构进行交换,读者就可以看到这个修改了.
 *      CopyOnWriteArrayList,写入将导致创建整个底层数组的副本,而源数组将保留在原地,使得复制的数组在被修改时,读取操作可以安全地执行.当修改完之后,一个原子性的操作替换掉源数组,读者可以读取新的修改.CopyOnWriteArrayList的好处之一就是当多个迭代器同事遍历和修改这个列表时,不会抛出ConcurrentModificationException
 *      CopyOnWriteArraySet,使用CopyOnWriteArrayList来实现免锁行为
 *      ConcurrentHashMap和ConcurrentLinkedQueue使用了类似的计数,允许并发的读取和写入,但是容器中只有部分内容而不是整个容器可以被复制和修改.锁的粒度较小,只锁部分
 *      ConcurrentHashMap的putVal()方法,只会当已经存在某个值的时候才会去锁住要换的这个节点对象,如果不存在会使用一个原子操作U.compareAndSwapObject()保证并发性.
 *
 */
public class PerformanceTestDemo2 {

    abstract static class Accumulator {
        private static long cycles = 50000L;

        private static final int N = Runtime.getRuntime().availableProcessors();

        public static ExecutorService exec = Executors.newFixedThreadPool(N * 2);

        private static CyclicBarrier barrier = new CyclicBarrier(N * 2 + 1);

        protected volatile int index = 0;

        protected volatile long value = 0;

        protected long duration = 0;

        protected String id = "error";

        protected final static int SIZE = 10_000_000;

        protected static int[] preLoaded = new int[SIZE];

        static {
            Random rand = new Random(47);
            for(int i = 0; i < SIZE; i++) {
                preLoaded[i] = rand.nextInt();
            }
        }

        public abstract void accumulate();

        public abstract long read();

        // 修改程序
        private class Modifier implements Runnable {

            @Override
            public void run() {
                for(long i = 0; i < cycles; i++) {
                    accumulate();
                }
                try {
                    barrier.await();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        private class Reader implements Runnable {

            private volatile long value;

            @Override
            public void run() {
                for(long i = 0; i < cycles; i++) {
                    value = read();
                }
                try {
                    barrier.await();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        // 时间测试
        public void timedTest() {
            long start = System.nanoTime();
            for(int i = 0; i < N; i++) {
                exec.execute(new Modifier());
                exec.execute(new Reader());
            }
            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                throw new RuntimeException(e);
            }
            duration = System.nanoTime() - start;
            System.out.println(String.format("%-13s: %13d\n", id, duration));
        }

        // 报告,时间比
        public static void report(Accumulator acc1, Accumulator acc2) {
            System.out.println(String.format("%-22s: %.2f\n",
                    acc1.id + "/" + acc2.id, (double)acc1.duration / (double) acc2.duration));
        }
    }

    // 不加任何锁的基准测试
    static class BaseLine extends Accumulator {

        { id = "BaseLine"; }

        @Override
        public void accumulate() {
            value += preLoaded[index++];
            if(index >= SIZE) index = 0;
        }

        @Override
        public long read() {
            return value;
        }
    }

    static class SynchronizedTest extends Accumulator {

        { id = "synchronized"; }

        @Override
        public synchronized void accumulate() {
            value += preLoaded[index++];
            if(index >= SIZE) index = 0;
        }

        @Override
        public synchronized long read() {
            return value;
        }
    }

    static class LockTest extends Accumulator {

        { id = "lock"; }

        private ReentrantLock lock = new ReentrantLock();

        @Override
        public void accumulate() {
            lock.lock();
            try {
                value += preLoaded[index++];
                if (index >= SIZE) index = 0;
            } finally {
                lock.unlock();
            }
        }

        @Override
        public long read() {
            lock.lock();
            try {
                return value;
            } finally {
                lock.unlock();
            }
        }
    }

    static class AtomicTest extends Accumulator {

        { id = "Atomic"; }

        private AtomicInteger index = new AtomicInteger(0);

        private AtomicLong value = new AtomicLong(0);

        @Override
        public synchronized void accumulate() {
            int i = index.getAndIncrement();

            value.getAndAdd(preLoaded[i]);
            if(++i >= SIZE) {
                index.set(0);
            }
        }

        @Override
        public long read() {
            return value.get();
        }
    }

    //
    static class SynchronizeationComparisons {
        static BaseLine baseLine = new BaseLine();
        static SynchronizedTest synchronizeds = new SynchronizedTest();
        static LockTest lock = new LockTest();
        static AtomicTest atomic = new AtomicTest();

        static void test() {
            System.out.println("========================");
            System.out.println(String.format("%-12s : %13d\n", "Cycles", Accumulator.cycles));
            baseLine.timedTest();
            synchronizeds.timedTest();
            lock.timedTest();
            atomic.timedTest();

            Accumulator.report(synchronizeds, baseLine);
            Accumulator.report(lock, baseLine);
            Accumulator.report(atomic, baseLine);
            Accumulator.report(synchronizeds, lock);
            Accumulator.report(synchronizeds, atomic);
            Accumulator.report(lock, atomic);
        }

        public static void main(String[] args) {
            int iterations = 5;
            System.out.println("Warmup");
            baseLine.timedTest();

            for(int i = 0; i < iterations; i++) {
                test();
                Accumulator.cycles *= 2;
            }
            Accumulator.exec.shutdownNow();
        }
    }

    /**
     * 多线程修改同一个list,会导致很多意向不到的情况
     * @throws InterruptedException
     */
    @Test
    public void copyOnWriteTest1() throws InterruptedException {
        List<Integer> data = Collections.synchronizedList(new ArrayList<>());
        ExecutorService exec = Executors.newCachedThreadPool();
        CountDownLatch latch = new CountDownLatch(10);
        for(int i = 0; i < 10; i++) {
            exec.execute(() -> {
                for(int j = 0; j < 100; j++) {
                    data.add(j);
                }
                latch.countDown();
            });
        }
        latch.await();
        System.out.println(data.size());
    }

    /**
     * copyOnWriteArrayList会每次修改的时候都会复制整个数组,然后在这个副本上进行修改,最后通过一个原子替换操作叫主数据换成修改后的数据.
     * 这样在修改的过程中读取的数据还是之前的数据,这样就不会出现多线程修改容器的错误.
     * @throws InterruptedException
     */
    @Test
    public void copyOnWriteTest2() throws InterruptedException {
        List<Integer> data = new CopyOnWriteArrayList<>();
        ExecutorService exec = Executors.newCachedThreadPool();
        CountDownLatch latch = new CountDownLatch(10);
        for(int i = 0; i < 10; i++) {
            exec.execute(() -> {
                for(int j = 0; j < 100; j++) {
                    data.add(j);
                }
                latch.countDown();
            });
        }
        latch.await();
        System.out.println(data.size());
    }

    @Test
    public void concurrentHashMapTest1() {
        Map<Integer, String> map = new ConcurrentHashMap<>();
        for(int i = 0; i < 1000; i++) {
            map.put(i, "a");
            if(i == 500) {
                System.out.println();
            }
        }

        map.get(1);
    }
}
