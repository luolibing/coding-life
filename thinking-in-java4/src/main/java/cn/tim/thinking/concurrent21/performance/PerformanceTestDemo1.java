package cn.tim.thinking.concurrent21.performance;

import org.junit.Test;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by LuoLiBing on 16/11/18
 * 危险的微基准测试
 */
public class PerformanceTestDemo1 {

    abstract class Incrementable {
        protected long counter = 0;
        public abstract void increment();
    }

    class SynchronizeingTest extends Incrementable {

        @Override
        public synchronized void increment() {
            ++counter;
        }
    }

    class LockTest extends Incrementable {

        private ReentrantLock lock = new ReentrantLock();

        @Override
        public void increment() {
            lock.lock();
            try {
                ++ counter;
            } finally {
                lock.unlock();
            }
        }
    }

    class MockTest {
        public long test(Incrementable incrementable) {
            long start = System.nanoTime();
            for(int i = 0; i < 100000000; i++) {
                incrementable.increment();
            }
            return System.nanoTime() - start;
        }
    }

    /**
     * 微基准测试: 是在隔离的\脱离上下文环境的情况下对某个特性进行性能测试,这样的测试存在很多问题.
     */
    @Test
    public void test1() {
        MockTest mockTest = new MockTest();
        long syncTime = mockTest.test(new SynchronizeingTest());
        long lockTime = mockTest.test(new LockTest());
        System.out.println("syncTime = " + syncTime);
        System.out.println("lockTime = " + lockTime);
    }
}

