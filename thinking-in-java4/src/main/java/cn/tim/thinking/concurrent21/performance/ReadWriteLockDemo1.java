package cn.tim.thinking.concurrent21.performance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by LuoLiBing on 16/11/21.
 * ReadWriteLock读写锁对向数据结构相对不频繁地写入,但是有多个任务要经常读取这个数据结构的这类情况进行了优化.
 * ReadWriteLock使得你可以同时又多个读取者,只要他们都不试图写入即可.如果写锁已经被其他任务持有,那么任何读者都不能访问,直至这个写锁被释放为止.
 * ReadWriteLock能否提高程序的性能是完全不可确定的,它取决于例如数据被读取的平率与被修改的频率比较的结果.
 */
public class ReadWriteLockDemo1 {

    static class ReaderWriterList<T> {
        // 共享的数据
        private ArrayList<T> lockedList;

        // 读写锁
        private ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);

        public ReaderWriterList(int size, T initialValue) {
            lockedList = new ArrayList<>(Collections.nCopies(size, initialValue));
        }

        public T set(int index, T element) {
            // 获取写锁,其他写锁与读锁都会被阻塞
            ReentrantReadWriteLock.WriteLock wlock = lock.writeLock();
            wlock.lock();
            try {
                System.out.println("writeLock()");
                Thread.sleep(10000);
                return lockedList.set(index, element);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return null;
            } finally {
                wlock.unlock();
            }
        }

        public T get(int index) {
            // 读锁并不会影响到其他读锁与写锁
            ReentrantReadWriteLock.ReadLock rlock = lock.readLock();
            rlock.lock();
            try {
                // 获取读锁的个数
                if(lock.getReadLockCount() > 1) {
                    System.out.println(lock.getReadLockCount());
                }
                return lockedList.get(index);
            } finally {
                rlock.unlock();
            }
        }

        public static void main(String[] args) {
            new ReaderWriterListTest(30, 5);
        }
    }

    static class ReaderWriterListTest {
        ExecutorService exec = Executors.newCachedThreadPool();

        private final static int SIZE = 100;

        private static Random rand = new Random(47);

        private ReaderWriterList<Integer> list = new ReaderWriterList<>(SIZE, 0);

        private class Writer implements Runnable {

            @Override
            public void run() {
                try {
                    for (int i = 0; i < 20; i++) {
                        list.set(i, rand.nextInt());
                        TimeUnit.MILLISECONDS.sleep(1);
                    }
                } catch (InterruptedException e) {

                }
                System.out.println("Writer finished, shutting down");
                exec.shutdownNow();
            }
        }

        private class Reader implements Runnable {

            @Override
            public void run() {
                try {
                    while (!Thread.interrupted()) {
                        for (int i = 0; i < SIZE; i++) {
                            list.get(i);
                            TimeUnit.MILLISECONDS.sleep(100);
                        }
                    }
                } catch (InterruptedException e) {

                }
            }
        }

        public ReaderWriterListTest(int readers, int writers) {
            for(int i = 0; i < readers; i++) {
                exec.execute(new Reader());
            }

            for(int i = 0; i < writers; i++) {
                exec.execute(new Writer());
            }
        }
    }

}
