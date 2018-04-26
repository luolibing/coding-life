package cn.tim.thinking.concurrent21.consumer;

import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by LuoLiBing on 16/10/25.
 *
 * ArrayBlockingQueue的实现
 *   固定容量大小的数组,典型的场景是固定大小的缓冲,生产者插入,消费者取出.一旦创建,大小就不能改变.
 *   支持可选的排序公平策略,默认的排序并不令人放心,使用fairness为true构造能够让访问队列按照FIFO排序.
 *   公平设置为true会降低吞吐量,但是会减少易变性,并且避免了饥饿.
 *
 *   底层是通过数组实现,数组大小固定,通过3个标识来辅助插入与取出,putIndex插入位置,takeIndex取出位置,count当前大小
 *   初始状态下,putIndex和takeIndex,count都等于0,随着插入与取出putIndex和takeIndex位置不停的变化,当putIndex或者takeIndex达到数组末尾时,会回到0.
 *   通过一个lock锁,两个condition条件(notEmpty和notFull)进行不同线程间通信
 *      当put的时候会先获取锁,然后判断如果已满,则需要在notFull.await()上阻塞等待直到notFull有空间为止.然后根据putIndex进行插入操作,并且通知notEmpty.signal()激活take()
 *      当take的时候同样会先获取锁,然后判断如果为空,则需要在notEmpty.await()上阻塞等到直到notEmpty有元素为止,然后根据takeIndex进行获取操作,并且通知notFull.signal()激活put()
 *      从上可以看出,put和take都使用了一个共享全局锁,所以吞吐量会大打折扣.
 *
 *   remove(e)其实可以把ArrayBlockingQueue看做一个首尾相接的大小固定的环,putIndex和takeIndex指针会在这个环内运动,并且当删除元素时,需要向逆时针移动元素补位

 *   类属性
 *      items(Object[]),takeIndex,putIndex,count,  同步策略lock(ReentrantLock),notEmpty(Condition),notFull(Condition),  迭代器相关Itrs(itrs)
 *
 *   方法: 大部分对外暴露的方法都不允许传null值,因为这些方法执行的时候都会去调用checkNotNull
 *      ArrayBlocking(capacity,fair)    两个参数,容量与公平策略
 *
 *
 *
 */
public class ArrayBlockingQueueDemo {

    @Test
    public void test1() {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(3);
        for(int i = 0; i < 3; i++) {
            try {
                queue.put(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        while (!Thread.interrupted()) {
            try {
                System.out.println("num = " + queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
