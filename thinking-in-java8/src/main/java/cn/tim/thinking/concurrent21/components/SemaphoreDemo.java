package cn.tim.thinking.concurrent21.components;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by LuoLiBing on 16/11/12.
 * Semaphore信号量, 控制一个对象最多能同时被多少个对象访问
 */
public class SemaphoreDemo {

    /**
     * 对象池,对于构造比较耗时的大对象,可以使用对象池进行管理
     * @param <T>
     */
    static class Pool<T> {
        // 池大小
        private int size;

        // 对象列表
        private List<T> items;

        // 使用情况表
        private volatile boolean[] checkOuts;

        // 信号量
        private Semaphore available;

        public Pool(int size, Class<T> clazz) {
            this.size = size;
            checkOuts = new boolean[size];
            available = new Semaphore(size, true);
            items = new ArrayList<>();
            for(int i = 0; i < size; i++) {
                try {
                    items.add(clazz.newInstance());
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        /**
         * 取出对象
         * @return
         */
        public T checkOut() throws InterruptedException {
            available.acquire();
            return getItem();
        }

        private synchronized T getItem() {
            for(int i = 0; i < size; i++) {
                if(!checkOuts[i]) {
                    checkOuts[i] = true;
                    return items.get(i);
                }
            }
            return null;
        }

        /**
         * 放回对象
         * @param t
         */
        public void checkIn(T t) {
            if(release(t)) {
                available.release();
            }
        }

        // 释放
        private synchronized boolean release(T t) {
            int index = items.indexOf(t);
            // 没有找到
            if(index == -1) return false;
            // 判断是否是处于checkOut状态
            if(checkOuts[index]) {
                checkOuts[index] = false;
                return true;
            }
            // 不是checkOut状态
            return false;
        }
    }

    static class Fat {
        private volatile double d;
        private static int counter;
        private int id = counter ++;

        // 模拟比较耗时的对象构造
        public Fat() {
            for(int i = 1; i < 10000; i++) {
                d += (Math.PI + Math.E) / (double) i;
            }
        }

        public void operate() {
            System.out.println(this);
        }

        @Override
        public String toString() {
            return "Fat id = " + id;
        }
    }

    public static void main1(String[] args) throws InterruptedException {
        Pool<Fat> pool = new Pool<>(3, Fat.class);
        Fat f1 = pool.checkOut();
        System.out.println(f1);
        pool.checkIn(f1);
        Fat f2 = pool.checkOut();
        System.out.println(f2);
    }

    // 模拟一个线程从对象池中获取一个对象然后持有一段时间放回
    static class CheckoutTask<T> implements Runnable {

        private static int counter = 0;

        private int id = counter ++;

        private Pool<T> pool;

        public CheckoutTask(Pool<T> p) {
            pool = p;
        }

        @Override
        public void run() {
            try {
                T t = pool.checkOut();
                System.out.println(this + " check out " + t);
                TimeUnit.SECONDS.sleep(1);
                System.out.println(this + " check in " + t);
                pool.checkIn(t);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public String toString() {
            return "CheckoutTask " + id;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final int SIZE = 25;
        Pool<Fat> pool = new Pool<>(SIZE, Fat.class);
        ExecutorService exec = Executors.newCachedThreadPool();
        // 使用然后放回
        for(int i = 0; i < SIZE; i++) {
            exec.execute(new CheckoutTask<>(pool));
        }
        System.out.println("All CheckoutTasks created");

        List<Fat> fats = new ArrayList<>();
        for(int i = 0; i < SIZE; i++) {
            Fat fat = pool.checkOut();
            fat.operate();
            fats.add(fat);
        }

        Future<?> future = exec.submit(() -> {
            try {
                pool.checkOut();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        TimeUnit.SECONDS.sleep(2);
        future.cancel(true);
        for(int i = 0; i < SIZE; i++) {
            pool.checkIn(fats.get(i));
        }

        for(int i = 0; i < SIZE; i++) {
            Fat fat = pool.checkOut();
            fat.operate();
        }
        exec.shutdown();
    }
}
