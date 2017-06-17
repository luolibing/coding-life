package cn.tim.thinking.concurrent21.consumer;

import cn.tim.thinking.concurrent21.thread.RunnableDemo;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by LuoLiBing on 16/10/25.
 * BlockingQueue接口
 * blockingQueue接口继承自Queue接口,额外增加了当队列为空时尝试获取元素时阻塞等待和当保存时无可用空间时阻塞等待.
 *
 * 一 blockingQueue各种操作都有4种不同的形式不同之处在于
 * 抛出异常,返回一个特殊的值false或者null,阻塞,带超时时间的阻塞
 * Insert   add(),offer(e),put(e),offer(e,time,unit)
 * Remove   remove(),poll(),take(),poll(time,unit)
 * Examine  element(),peek()
 *
 * 二 BlockingQueue不允许空元素,当有空元素时会抛出空指针异常,null值作为一个哨兵返回当调用poll().
 * 三 blockingQueue大部分情况下是为了实现生产者消费者模式而使用的,额外情况下支持Collection接口,例如remove()操作,但是这些操作一般并不能被有效地执行.只是偶尔使用,例如当队列消息取消时
 * 四 BlockingQueue是线程安全的,使用了内置锁来实现原子性,或者使用其他同步机制保证线程安全,并且可以很好的支持多生产者多消费者
 * 五 BlockingQueue本质上不支持close,shutdown来表明不允许继续添加元素,一个常用的方式是,生产者使用一个结束流或者毒药来告诉消费者生产结束,接下来不会再有元素被添加进来
 *
 * 六 BlockingQueue接口方法
 * add()    返回true或者当容量不够时抛出异常
 * put()    无返回值当容量不够时会wait()阻塞,直到有可用容量为止. 并且如果正在等待时中断,会抛出中断异常
 * offer()  向队列插入,返回true或者false(无可用容量)
 * offer(e,time,unit) 带超时时间的插入,因为要调用wait()所以会抛出中断异常,其他的与offer()一致
 *
 * remove() returnAndRemove()当为空时抛出异常NoSuchElementException
 * take()   returnAndRemove()当为空时wait阻塞,直到有可用元素为止
 * poll()   returnAndRemove()当为空时直接返回null
 * remove(e)remove()直接删除队列中所有相同元素,调用o.equals(e)判断是否相同,当存在时返回true,不存在时返回false
 *
 * element()return返回队头元素,但是不删除,当为空时抛出异常
 * peek()   return返回队头元素,但是不删除,为空返回null
 *
 * contains 判断是否存在某个元素
 * drainTo(coll)将队列中的元素全部移到coll容器当中
 * drainTo(coll,maxEle) 将最大多少个元素移到coll容器当中
 *
 * 七 BlockingQueue的实现
 *      ArrayBlockingQueue()
 */
public class BlockingQueueDemo1 {

    static class LiftOffRunner implements Runnable {

        private BlockingQueue<RunnableDemo.LiftOff> rockets;

        public LiftOffRunner(BlockingQueue<RunnableDemo.LiftOff> queue) {
            rockets = queue;
        }

        public void add(RunnableDemo.LiftOff lo) {
            try {
                rockets.put(lo);
            } catch (InterruptedException e) {
                System.out.println("Interrupted during put()");
            }
        }

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    RunnableDemo.LiftOff rocket = rockets.take();
                    rocket.run();
                }
            } catch (InterruptedException e) {
                System.out.println("Waking from take()");
            }
            System.out.println("Exiting LiftOffRunner()");
        }
    }

    static class TestBlockingQueues {

        static void getKey() {
            try {
                new BufferedReader(new InputStreamReader(System.in)).readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        static void getKey(String message) {
            System.out.println(message);
            getKey();
        }

        static void test(String msg, BlockingQueue<RunnableDemo.LiftOff> queue) {
            System.out.println(msg);
            LiftOffRunner runner = new LiftOffRunner(queue);
            Thread t = new Thread(runner);
            t.start();

            for(int i = 0; i < 5; i++) {
                runner.add(new RunnableDemo.LiftOff(5));
            }

            getKey("Press 'Enter' (" + msg + ")");
            t.interrupt();
            System.out.println("Finish " + msg + " test");
        }

        public static void main(String[] args) {
            test("LinkedBlockingQueue", new LinkedBlockingQueue<>()); // 任意有界,最大大小为Integer.MAX_VALUE
            test("ArrayBlockingQueue", new ArrayBlockingQueue<>(3));  // 有界队列
            test("SynchronousQueue", new SynchronousQueue<>());       // 同步队列(只允许有一个元素)

        }
    }

    @Test
    public void test1() throws InterruptedException {
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(2);
        for(int i = 0; i < 5; i++) {
            System.out.println("offer=" + queue.offer("id" + i));
        }

        queue = new ArrayBlockingQueue<>(2);
        for(int i = 0; i < 5; i++) {
            try {
                System.out.println("offer=" + queue.offer("id" + i, 100, TimeUnit.MILLISECONDS));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        List<String> result = new ArrayList<>();
//        queue.drainTo(result, 100);
        queue.take();
        System.out.println("drainTo=" + result);

        queue = new ArrayBlockingQueue<>(2);
        System.out.println("poll=" + queue.poll(1000, TimeUnit.MILLISECONDS));
        System.out.println("remove(e) = " + queue.remove(1));

        System.out.println("reamingCap= " + queue.remainingCapacity());
    }
}
