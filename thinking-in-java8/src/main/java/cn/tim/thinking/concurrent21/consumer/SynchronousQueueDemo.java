package cn.tim.thinking.concurrent21.consumer;

import org.junit.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * Created by LuoLiBing on 16/10/27.
 * 当插入的时候必须等待,之前的元素被其他的线程take掉,size永远为0
 */
public class SynchronousQueueDemo {


    @Test
    public void test1() {
        BlockingQueue<String> queue = new SynchronousQueue<>();
        new Thread(() -> { for(;;) {
            try {
                System.out.println("take = " + queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }}).start();

        for(int i = 0; i < 100; i++) {
            String id = "id" + i;
            System.out.println("input " + id);
            try {
                queue.put(id);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
