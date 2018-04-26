package cn.tim.thinking.concurrent21.critical;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by LuoLiBing on 16/10/20.
 *
 * 1 synchronized块必须给定一个在其上进行同步的对象,并且最合理的方式是,使用synchronized(this).
 *      在这种方式中,如果获得临界资源的锁,那么该对象其他的synchronized方法和临界区都不能被调用了.
 * 2 在另一个对象上同步,必须确保所有相关的任务都是在同一个对象上同步,如果不在同一个对象上同步,不同的同步可以并行运行
 */
public class SynchronizedDemo2 {

    static class DualSynch {

        private Object syncObject = new Object();

        public synchronized void f() {
            for(int i = 0; i < 5; i++) {
                System.out.println("f()");
                Thread.yield();
            }
        }

        public void g() {
            synchronized (syncObject) {
                for(int i = 0; i < 5; i++) {
                    System.out.println("g()");
                    Thread.yield();
                }
            }
        }

        // f()方法在this上同步,g()方法在syncObject上同步, 两者并不互斥,两个同步是相互独立的.
        public static void main(String[] args) {
            DualSynch ds = new DualSynch();
            new Thread(ds::f).start();
            ds.g();
        }
    }

    static class ThreadSync1 {
        private final Object syncObject;
        public ThreadSync1(Object syncObject) {
            this.syncObject = syncObject;
        }

        public void f() {
            synchronized (syncObject) {
                for(int i = 0; i < 5; i++) {
                    System.out.println("f()");
                    Thread.yield();
                }
            }
        }

        public void g() {
            synchronized (syncObject) {
                for(int i = 0; i < 5; i++) {
                    System.out.println("g()");
                    Thread.yield();
                }
            }
        }

        public void h() {
            synchronized (syncObject) {
                for(int i = 0; i < 5; i++) {
                    System.out.println("h()");
                    Thread.yield();
                }
            }
        }

        public static void main(String[] args) {
            Object syncObj = new Object();
            ThreadSync1 threadSync = new ThreadSync1(syncObj);
            new Thread(threadSync::g).start();
            new Thread(threadSync::h).start();
            new Thread(threadSync::f).start();
        }
    }

    static class ThreadSync2 {
        private final Object syncObject1;
        private final Object syncObject2;
        private final Object syncObject3;
        public ThreadSync2(Object syncObject1, Object syncObject2, Object syncObject3) {
            this.syncObject1 = syncObject1;
            this.syncObject2 = syncObject2;
            this.syncObject3 = syncObject3;
        }

        public void f() {
            synchronized (syncObject1) {
                for(int i = 0; i < 5; i++) {
                    System.out.println("f()");
                    Thread.yield();
                }
            }
        }

        public void g() {
            synchronized (syncObject2) {
                for(int i = 0; i < 5; i++) {
                    System.out.println("g()");
                    Thread.yield();
                }
            }
        }

        public void h() {
            synchronized (syncObject3) {
                for(int i = 0; i < 5; i++) {
                    System.out.println("h()");
                    Thread.yield();
                }
            }
        }

        public static void main(String[] args) {
            Object syncObj1 = new Object();
            Object syncObj2 = new Object();
            Object syncObj3 = new Object();
            ThreadSync2 threadSync = new ThreadSync2(syncObj1, syncObj2, syncObj3);
            new Thread(threadSync::g).start();
            new Thread(threadSync::h).start();
            new Thread(threadSync::f).start();
        }
    }

    static class ThreadSync3 {
        private final Lock syncObject1 = new ReentrantLock();
        private final Lock syncObject2 = new ReentrantLock();
        private final Lock syncObject3 = new ReentrantLock();

        public void f() {
            syncObject1.lock();
            try {
                for(int i = 0; i < 5; i++) {
                    System.out.println("f()");
                    Thread.yield();
                }
            } finally {
                syncObject1.unlock();
            }
        }

        public void g() {
            syncObject2.lock();
            try {
                for(int i = 0; i < 5; i++) {
                    System.out.println("g()");
                    Thread.yield();
                }
            } finally {
                syncObject2.unlock();
            }
        }

        public void h() {
            syncObject3.lock();
            try {
                for(int i = 0; i < 5; i++) {
                    System.out.println("h()");
                    Thread.yield();
                }
            } finally {
                syncObject3.unlock();
            }
        }

        public static void main(String[] args) {
            ThreadSync3 threadSync = new ThreadSync3();
            new Thread(threadSync::g).start();
            new Thread(threadSync::h).start();
            new Thread(threadSync::f).start();
        }
    }
}
