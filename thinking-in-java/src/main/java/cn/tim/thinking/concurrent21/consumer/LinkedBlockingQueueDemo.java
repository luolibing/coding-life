package cn.tim.thinking.concurrent21.consumer;

import org.junit.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by LuoLiBing on 16/10/27.
 *
 * LinkedBlockingQueue
 * 任意有界队列,底层使用链表实现,有序FIFO. 链表队列在大多数并发系统中一般比数组型队列例如ArrayBlockingQueue吞吐量更高但是更低的可预见性.
 * 可选容量参数可以防止链表队列过度扩张,节点被动态的创建出来,前提是大小不超过最大容量.
 *
 * LinkedBlockingQueue是双锁队列的一种变体算法, putLock是添加元素(put,offer)的的一个锁,并且有一个关联的Condition作为进入的条件. 同样takeLock,当取出元素时使用take或者poll.
 * 使用一个原子操作的AtomicInteger的count字段,避免大多数情况下因为要获取count而必须获取两个锁的情况. 同时,为了减少需要获取takeLock的次数,使用了级联的notify通知. 当一个put通知达到时,至少有一个take被激活,通过发送信号.
 * 如果从调用完signal之后,更多的items被加入进来,taker会将消息转发给其他线程.对称的takes发送信号给puts,remove和iterator迭代操作都需要获取到两个锁
 *
 * 写者与读者的可见性如下:
 * 每当有元素要添加进来时,获取putLock锁和更新count值.随后的读者通过获取putLock或通过获取takeLock来保证对入队Node的可见性.
 *
 * LinkedBlockingQueue类
 *  属性:
 *      capacity(int)容量,count(AtomicInteger)大小,head(Node)队头,last(Node)队尾,队头的item始终为null,队尾的next为null.初始化的时候head和last为同一个节点
 *      takeLock(ReentrantLock)获取锁,notEmpty(Condition)不为空,putLock(加入锁),notFull(不为满)
 *  方法:
 *      put()   新建节点,然后获取putLock锁,当容量为满时,会一直等待直到容量不为满为止,然后加入到队尾,自增容量,如果当前容量还不为满,可以通知给其他await的线程
 *      offer() 加入节点
 *      take()  获取队头节点,先获取takeLock锁,当队列为空时,循环等待await(),当容量不为空时,唤醒,将head去掉,将first内容置为空,变相的把first给删除掉
 */
public class LinkedBlockingQueueDemo {

    @Test
    public void test1() {
        BlockingQueue<String> queue = new LinkedBlockingQueue<>(100);
        queue.add("a");

    }
}
