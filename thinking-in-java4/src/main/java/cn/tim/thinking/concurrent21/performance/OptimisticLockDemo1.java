package cn.tim.thinking.concurrent21.performance;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

/**
 * Created by LuoLiBing on 16/11/21.
 * 容器性能测试方法
 *
 * 从免锁容器中读取,比起synchronized对应物快许多,因为获取和释放锁的开销被省略掉了.
 * CopyOnWriteArrayList在没有写者的时候,明显比synchronizedList快很多,因为读的时候无锁的,并且在写入的时候速度明显的比SynchronizedList快
 * ConcurrentHashMap性能明显强于Collections.synchronizedMap(),添加读者时的影响设置不如CopyOnWriteArrayList明显,因为ConcurrentHashMap使用了一种不同的计数,粒度更小
 */
public class OptimisticLockDemo1 {

    public abstract static class Tester<C> {
        // 测试重复次数
        static int testReps = 10;

        // 循环次数
        static int testCycles = 1000;

        // 容器大小
        static int containerSize = 1000;

        // 容器
        C testContainer;

        // id
        String testId;

        // 读者个数
        int nReaders;

        // 写者个数
        int nWriters;

        volatile long readResult = 0;

        volatile long readTime = 0;

        volatile long writeTime = 0;

        CountDownLatch endLatch;

        static ExecutorService exec = Executors.newCachedThreadPool();

        // 写入的数据
        Integer[] writeData;


        // 初始化容器
        abstract C containerInitializer();

        // 开始读取与写入
        abstract void startReadersAndWriters();

        public Tester(String testId, int nReaders, int nWriters) {
            this.testId = testId + " " + nReaders + "r " + nWriters + "w";
            this.nReaders = nReaders;
            this.nWriters = nWriters;
            writeData = Generated.array(containerSize);
            // 循环测试
            for(int i = 0; i < testReps; i++) {
                runTest();
                readTime = 0;
                writeTime = 0;
            }
        }

        // 执行测试
        private void runTest() {
            endLatch = new CountDownLatch(nReaders + nWriters);
            // 生成对应的容器
            testContainer = containerInitializer();
            startReadersAndWriters();
            try {
                endLatch.await();
            } catch (InterruptedException ex) {
                System.out.println("endLatch interrupted");
            }
            // 执行完所有读者与写者之后,进行结果的输出
            System.out.println(String.format("%-27s %14d %14d\n", testId, readTime, writeTime));
            if(readTime != 0 && writeTime != 0) {
                System.out.println(String.format("%-27s %14d\n", "readTime + writeTime =", readTime + writeTime));
            }
        }

        abstract class TestTask implements Runnable {
            abstract void test();
            abstract void putResults();
            long duration;

            // 测试任务,记录测试时长
            @Override
            public void run() {
                long startTime = System.nanoTime();
                test();
                duration = System.nanoTime() - startTime;
                synchronized (Tester.this) {
                    putResults();
                }
                endLatch.countDown();
            }

        }

        public static void initMain(Integer tps, Integer tcs, int size) {
            testReps = tps;
            testCycles = tcs;
            containerSize = size;
            System.out.println(String.format("%-27s %14s %14s\n", "Type", "Read time", "Write time"));
        }
    }


    static class Generated {

        private static Random rand = new Random(47);

        public static Integer[] array(int size) {
            Integer[] result = new Integer[size];
            for(int i = 0; i < size; i++) {
                result[i] = rand.nextInt(size);
            }
            return result;
        }
    }

    // List测试的抽象类
    static abstract class ListTest extends Tester<List<Integer>> {

        public ListTest(String testId, int nReaders, int nWriters) {
            super(testId, nReaders, nWriters);
        }

        // 读者
        class Reader extends TestTask {

            long result = 0;

            @Override
            void test() {
                for(long i = 0; i < testCycles; i++) {
                    for(int index = 0; index < containerSize; index++) {
                        result += testContainer.get(index);
                    }
                }
            }

            @Override
            void putResults() {
                readResult += result;
                readTime += duration;
            }
        }

        // 写者
        class Writer extends TestTask {

            @Override
            void test() {
                for(long i = 0; i < testCycles; i++) {
                    for(int index = 0; index < containerSize; index++) {
                        testContainer.set(index, writeData[index]);
                    }
                }
            }

            @Override
            void putResults() {
                writeTime += duration;
            }
        }

        @Override
        void startReadersAndWriters() {
            for(int i = 0; i < nReaders; i++) {
                exec.execute(new Reader());
            }

            for(int i = 0; i < nWriters; i++) {
                exec.execute(new Writer());
            }
        }
    }

    // 通过使用Collections.synchronizedList()生成的List集合
    static class SynchronizedArrayListTest extends ListTest {

        public SynchronizedArrayListTest(int nReaders, int nWriters) {
            super("SynchronizedArrayList", nReaders, nWriters);
        }

        @Override
        List<Integer> containerInitializer() {
            return Collections.synchronizedList(new ArrayList<>(new CountingIntegerList(containerSize)));
        }
    }

    // CopyOnWriteArrayList生成的List集合
    static class CopyOnWriteArrayListTest extends ListTest {

        @Override
        List<Integer> containerInitializer() {
            return new CopyOnWriteArrayList<>(new CountingIntegerList(containerSize));
        }

        public CopyOnWriteArrayListTest(int nReaders, int nWriters) {
            super("CopyOnWriteArrayList", nReaders, nWriters);
        }
    }

    static class CountingIntegerList extends ArrayList<Integer> {
        private static Random rand = new Random(47);

        public CountingIntegerList(int size) {
            for(int i = 0; i < size; i++) {
                add(rand.nextInt(size));
            }
        }
    }

    static class ListComparisons {
        public static void main(String[] args) throws IOException {
            Tester.initMain(3, 100, 100);
            new SynchronizedArrayListTest(10, 0);
            new SynchronizedArrayListTest(10, 1);
            new SynchronizedArrayListTest(5, 5);

            new CopyOnWriteArrayListTest(10, 0);
            new CopyOnWriteArrayListTest(9, 1);
            new CopyOnWriteArrayListTest(5, 5);
            Tester.exec.shutdown();
        }
    }

    /************* Map测试 ****************/

    static abstract class MapTest extends Tester<Map<Integer, Integer>> {

        public MapTest(String testId, int nReaders, int nWriters) {
            super(testId, nReaders, nWriters);
        }

        class Reader extends TestTask {

            long result = 0;

            @Override
            void test() {
                for(int i = 0; i < testCycles; i++) {
                    for(int index = 0; index < containerSize; index++) {
                        result += testContainer.get(index);
                    }
                }
            }

            @Override
            void putResults() {
                readResult += result;
                readTime += duration;
            }
        }

        class Writer extends TestTask {

            @Override
            void test() {
                for(int i = 0; i < testCycles; i++) {
                    for(int index = 0; index < containerSize; index++) {
                        testContainer.put(index, writeData[index]);
                    }
                }
            }

            @Override
            void putResults() {
                writeTime += duration;
            }
        }
    }

    static class SynchronizedHashMapTest extends MapTest {

        public SynchronizedHashMapTest(int nReaders, int nWriters) {
            super("SynchronizedHashMap", nReaders, nWriters);
        }

        @Override
        Map<Integer, Integer> containerInitializer() {
            return Collections.synchronizedMap(new MapData(containerSize));
        }

        @Override
        void startReadersAndWriters() {
            for(int i = 0; i < nReaders; i++) {
                exec.execute(new Reader());
            }

            for(int i = 0; i < nWriters; i++) {
                exec.execute(new Writer());
            }
        }
    }

    static class ConcurrentHashMapTest extends MapTest {

        @Override
        Map<Integer, Integer> containerInitializer() {
            return new ConcurrentHashMap<>(new MapData(containerSize));
        }

        @Override
        void startReadersAndWriters() {
            for(int i = 0; i < nReaders; i++) {
                exec.execute(new Reader());
            }

            for(int i = 0; i < nWriters; i++) {
                exec.execute(new Writer());
            }
        }

        public ConcurrentHashMapTest(int nReaders, int nWriters) {
            super("ConcurrentHashMap", nReaders, nWriters);
        }
    }

    static class MapData extends HashMap<Integer, Integer> {
        private static Random rand = new Random(47);

        public MapData(int size) {
            for(int i = 0; i < size; i++) {
                put(i, rand.nextInt(size));
            }
        }
    }

    static class MapComparisons {
        public static void main(String[] args) {
            Tester.initMain(3, 100, 100);
            new SynchronizedHashMapTest(10, 0);
            new SynchronizedHashMapTest(10, 1);
            new SynchronizedHashMapTest(5, 5);

            new ConcurrentHashMapTest(10, 0);
            new ConcurrentHashMapTest(9, 1);
            new ConcurrentHashMapTest(5, 5);
            Tester.exec.shutdown();
        }
    }
}
