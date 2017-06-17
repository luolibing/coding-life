package cn.tim.thinking.concurrent21.local;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by LuoLiBing on 16/10/21.
 * 防止任务在共享资源上产生冲突的第二种方式是使用线程本地存储(ThreadLocal),可以根除对变量的共享,并且是一种自动化机制.
 * 可以为使用相同变量的每个不同线程都创建不同的存储
 *
 * ThreadLocal类
 * 1 即使多个线程共用一个threadLocal对象,也会自动为每个线程单独存储一个变量
 * 2 java8中ThreadLocal添加了静态生成方法ThreadLocal.withInitial()
 * 3 每个线程都有一个ThreadLocalMap对象用来保存自己的变量,当使用get的时候会根据当前线程Thread.currentThread()获取这个线程的ThreadLocalMap对象,然后根据ThreadLocal作为key获取对应的value
 * 4 一般情况下ThreadLocal作为静态变量存在于类中.
 *
 * ThreadLocal实现原理：
 * 每个Thread都关联了一个ThreadLocalMap对象， 其中的key为ThreadLocal对象， value为对应的值。
 * 当要获取Thread的时候get()，会用当前线程直接获取对应的ThreadLocalMap， 然后使用ThreadLocal对象this作为key取得对应的值， 如果没有值的话， 取默认值并且放入到对应的Entry中。
 */
public class ThreadLocalDemo {

    static class Accessor implements Runnable {
        private final int id;
        public Accessor(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                ThreadLocalVariableHolder.increment();
                System.out.println(this);
                Thread.yield();
            }
        }

        @Override
        public String toString() {
            return "#" + id + ": " + ThreadLocalVariableHolder.get();
        }
    }

    static class ThreadLocalVariableHolder {

        // 虽然ThreadLocal是静态域,即便只有一个ThreadLocalVariableHolder对象,在多线程情况下ThreadLocal会自动为每个线程创建一个线程存储变量
        private static ThreadLocal<Integer> value = new ThreadLocal<Integer>() {
            private Random rand = new Random(47);
            @Override
            protected synchronized Integer initialValue() {
                System.out.println("ThreadLocal");
                return rand.nextInt(10000);
            }
        };

        public static void increment() {
            value.set(value.get() + 1);
        }

        public static int get() { return value.get(); }

        public static void main(String[] args) throws InterruptedException {
            ExecutorService exec = Executors.newCachedThreadPool();
            for(int i = 0; i < 5; i++) {
                exec.execute(new Accessor(i));
            }
            TimeUnit.SECONDS.sleep(3);
            exec.shutdown();
        }
    }

    static class Task implements Runnable {

        private ThreadLocal<Integer> value;

        private int id;

        public Task(int id, ThreadLocal<Integer> value) {
            this.value = value;
            this.id = id;
        }

        @Override
        public void run() {
            while (value.get() < 1000) {
                int num = value.get() + 1;
                value.set(num);
                System.out.println("id" + id + ", value= " + num);
                Thread.yield();
            }
        }
    }

    public static void main(String[] args) {
        ThreadLocal<Integer> tl = ThreadLocal.withInitial(() -> 0);
        ExecutorService exec = Executors.newCachedThreadPool();
        for(int i = 0; i < 5; i++) {
            exec.execute(new Task(i, tl));
        }
        exec.shutdown();
    }

}
