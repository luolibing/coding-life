package cn.tim.thinking.concurrent21.critical;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by LuoLiBing on 16/10/20.
 * 有时候只是希望防止多个线程同事访问方法内部的部分代码而不是防止整个方法,通过这种方式分离出来的代码段被称为临界区(critical section),使用synchronized()建立
 * synchronized(syncObject) {
 *
 * }
 * 这也被称为同步控制块; 进入此段代码前,必须得到syncObject对象的锁,如果其他线程已经得到这个锁,那么就得等待锁被释放以后,才能进入临界区.同步控制块相对于整个方法同步,具有更高的性能.
 *
 */
public class SynchronizeDemo1 {

    // 非线程安全的类
    static class Pair {
        private int x, y;
        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
        public Pair() {
            this(0, 0);
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public void incrementX() { x++; }

        public void incrementY() { y++; }

        public String toString() {
            return "x: " + x + ", y: " + y;
        }

        public class PairValuesNotEqualException extends RuntimeException {
            public PairValuesNotEqualException() {
                super("Pair values not equals: " + Pair.this);
            }
        }

        public void checkState() {
            if( x != y) {
                throw new PairValuesNotEqualException();
            }
        }
    }

    // 用一个线程安全的类保护pair对象
    static abstract class PairManager {
        AtomicInteger checkCounter = new AtomicInteger(0);
        protected Pair p = new Pair();
        // 使用同步List实现storage管理
        private List<Pair> storage = Collections.synchronizedList(new ArrayList<>());
        // 同步获取Pair实例
        public synchronized Pair getPair() {
            return new Pair(p.getX(), p.getY());
        }

        protected void store(Pair p) {
            // 因为storage是同步集合,所以不会出现什么问题
            storage.add(p);
            try {
                TimeUnit.MILLISECONDS.sleep(50);
            } catch (InterruptedException e) {}
        }

        public abstract void increment();
    }

    // 同步整个方法
    static class PairManage1 extends PairManager {

        @Override
        public synchronized void increment() {
            p.incrementX();
            p.incrementY();
            store(getPair());
        }
    }

    // 使用临界区缩小锁的范围
    static class PairManage2 extends PairManager {

        @Override
        public void increment() {
            Pair temp;
            synchronized (this) {
                p.incrementX();
                p.incrementY();
                temp = getPair();
            }
            store(temp);
        }
    }

    // 显示临界区
    static class PairManage3 extends PairManager {
        private Lock lock = new ReentrantLock();
        @Override
        public void increment() {
            Pair temp;
            lock.lock();
            try {
                p.incrementX();
                p.incrementY();
                temp = getPair();
            } finally {
                lock.unlock();
            }
            store(temp);
        }
    }

    // 操作手
    static class PairManipulator implements Runnable {
        private PairManager pm;
        public PairManipulator(PairManager pm) {
            this.pm = pm;
        }
        @Override
        public void run() {
            while (true) {
                pm.increment();
            }
        }

        @Override
        public String toString() {
            return "Pair: " + pm.getPair() + " checkCounter = " + pm.checkCounter.get();
        }
    }

    // Pair验证
    static class PairChecker implements Runnable {
        private PairManager pm;
        public PairChecker(PairManager pm) {
            this.pm = pm;
        }
        @Override
        public void run() {
            while (true) {
                pm.checkCounter.incrementAndGet();
                pm.getPair().checkState();
            }
        }
    }

    static class CriticalSection {
        static void testApproaches(PairManager pman1, PairManager pman2) {
            ExecutorService exec = Executors.newCachedThreadPool();
            PairManipulator pm1 = new PairManipulator(pman1);
            PairManipulator pm2 = new PairManipulator(pman1);

            PairChecker pcheck1 = new PairChecker(pman2);
            PairChecker pcheck2 = new PairChecker(pman2);

            exec.execute(pm1);
            exec.execute(pm2);
            exec.execute(pcheck1);
            exec.execute(pcheck2);

            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("Sleep interrupted");
            }
            System.out.println("pm1: " + pm1 + "\npm2: " + pm2);
            System.exit(0);
        }

        public static void main(String[] args) {
            PairManager pman1 = new PairManage1(),pman2 = new PairManage2();
            testApproaches(pman1, pman2);
        }
    }
}
