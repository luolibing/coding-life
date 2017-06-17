package cn.tim.thinking.concurrent21.resource;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by LuoLiBing on 16/10/17.
 */
public class SynchronizedTest {

    class Resource {
        private int data1 = 1;

        private long data2 = 0;

        // 修改和访问方法都得加锁才能起作用
        public synchronized void increment() {
            data1 ++;
            data1 ++;
            data2 ++;
            data2 ++;
        }

        //
        public synchronized void print() {
            System.out.println("data1= " + data1);
            System.out.println("data2= " + data2);
        }
    }

    @Test
    public void test1() {
        ExecutorService exec = Executors.newCachedThreadPool();
        Resource resource = new Resource();
        for(int i = 0 ; i < 10; i ++) {
            exec.execute(() -> {
                resource.increment();
                resource.print();
            });
        }
        exec.shutdown();
    }
}
