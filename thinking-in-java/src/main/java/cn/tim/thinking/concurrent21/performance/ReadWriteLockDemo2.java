package cn.tim.thinking.concurrent21.performance;

import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by LuoLiBing on 16/11/22.
 */
public class ReadWriteLockDemo2 {

    static class ReaderWriterMap<K, V> extends AbstractMap<K, V> {
        private Map<K, V> lockedMap;

        private ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);

        public ReaderWriterMap(Generator<K> genK, int size, V initialValue) {
            lockedMap = new HashMap<>(new MapData<>(genK, initialValue, size));
        }

        public V put(K key, V value) {
            ReentrantReadWriteLock.WriteLock wlock = lock.writeLock();
            wlock.lock();
            try {
                return lockedMap.put(key, value);
            } finally {
                wlock.unlock();
            }
        }

        @Override
        public Set<Entry<K, V>> entrySet() {
            return null;
        }

        public V get(Object key) {
            ReentrantReadWriteLock.ReadLock rlock = lock.readLock();
            rlock.lock();
            try {
                if(lock.getReadLockCount() > 1) {
                    System.out.println(lock.getReadLockCount());
                }
                return lockedMap.get(key);
            } finally {
                rlock.unlock();
            }
        }
    }

    static class ReaderWriterMapTest extends OptimisticLockDemo1.SynchronizedHashMapTest {

        public ReaderWriterMapTest(int nReaders, int nWriters) {
            super(nReaders, nWriters);
            this.testId = "ReaderWriterMap";
        }

        @Override
        Map<Integer, Integer> containerInitializer() {
            return new ReaderWriterMap<>(new CounttingGenerator(containerSize), containerSize, 1);
        }
    }


    static class Comparisons2 {
        public static void main(String[] args) {
            OptimisticLockDemo1.Tester.initMain(10, 1000, 100);
            new OptimisticLockDemo1.SynchronizedHashMapTest(10, 0);
            new OptimisticLockDemo1.SynchronizedHashMapTest(9, 1);
            new OptimisticLockDemo1.SynchronizedHashMapTest(5, 5);
            new OptimisticLockDemo1.ConcurrentHashMapTest(10, 0);
            new OptimisticLockDemo1.ConcurrentHashMapTest(9, 1);
            new OptimisticLockDemo1.ConcurrentHashMapTest(5, 5);
            new ReaderWriterMapTest(10, 0);
            new ReaderWriterMapTest(9, 1);
            new ReaderWriterMapTest(5, 5);
            Thread.yield();
            OptimisticLockDemo1.Tester.exec.shutdown();
        }
    }

    static class MapData<K, V> extends HashMap<K, V> {

        public MapData(Generator<K> generator, V initialValue, int size) {
            for(int i = 0; i < size; i++) {
                put(generator.next(), initialValue);
            }
        }
    }

    static class CounttingGenerator implements Generator<Integer> {

        static Random rand = new Random(47);

        private int size;

        public CounttingGenerator(int size) {
            this.size = size;
        }

        @Override
        public Integer next() {
            return rand.nextInt(size);
        }
    }

    interface Generator<T> {
        T next();
    }
}
