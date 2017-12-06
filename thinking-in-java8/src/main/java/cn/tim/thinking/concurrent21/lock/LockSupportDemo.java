package cn.tim.thinking.concurrent21.lock;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * Created by LuoLiBing on 16/10/25.
 * 用于创建锁和基本线程阻塞原语同步类
 *
 * 每个线程都跟lockSupport有联系,例如信号量Semaphore,调用park()方法会立即返回,如果允许挂起,否则将会阻塞.一次unpark的调用,取消挂起能让对象可用.不像信号量,许可不可基类,最多只有一个
 * park和unpark提供了高效的阻塞与恢复线程的操作,不会有调用过期的Thread.suspend和Thread.resume所遭遇到的不安全问题.
 * 当线程被中断时,park将被返回,同时支持带超时时间的park,park也有可能在其他时候得到响应所以一般情况下必须被包含在while()循环中,当park返回的时候重新验证条件.
 * park服务是对忙等待的一种优化,防止浪费过多的时间切换,但是必须与unpark配对使用,以使线程可用.
 *
 * park方式调用有三种方式:
 * 1 park(blockerObj),传入临界资源,以告诉监控或者诊断工具知道线程被哪个因素阻塞,一般情况下是使用this来作为阻塞参数(建议是使用不带参数的park())
 * 2 park()
 * 3 park(timeout),带超时时间的参数
 *
 * 这些方法一般被作为高级同步工具的小工具,一般不用于大多数并发应用程序
 * park一般用法
 * while(!canProceed)) { ... LockSupport.park(this);}
 *
 * LockSupport类方法
 *
 * setBlocker(Thread,Obj)       设置阻塞因子对象
 * unpark(Thread)               取消挂起,恢复可用
 * park(blocker)                挂起当前线程
 * park(blocker,nanosTimeOut)   带超时时间的挂起
 * park(blocker,deadline)       带绝对时间的挂起
 * getBlocker(Thread)           获取阻塞因子对象
 * park()                       挂起当前线程
 * park(nanoTimeout)
 * parkUnit(deadLine)
 *
 */
public class LockSupportDemo {

    public static void parkAndunpark() {
        List<String> list = new ArrayList<>();
        list.add("aaa");
        list.add("bbb");
        list.add("ccc");
        Thread th = new Thread(() -> {
            System.out.println(list);
            try {
                System.out.println("before sleep");
                // 挂起当前线程
                LockSupport.park();
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("end sleep");
        });
        th.start();
        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 恢复制定线程
        LockSupport.unpark(th);
    }

    @Test
    public void getBlocker() throws InterruptedException {
        class Person {
            private int id;
            public Person(int id) {
                this.id = id;
            }

            public synchronized void increment() {
                try {
                    while (!Thread.interrupted() && id < 100) {
                        id++;
                        LockSupport.park(id);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        Person p = new Person(1);
        Thread thread = new Thread(p::increment);
        thread.start();

        TimeUnit.MILLISECONDS.sleep(1000);
        Object blocker = LockSupport.getBlocker(thread);
        for(int i = 0; i < 100; i++) {
            LockSupport.unpark(thread);
            TimeUnit.MILLISECONDS.sleep(50);
        }
        System.out.println("blocker= " + blocker);
        System.out.println("Person= " + p.id);

    }


    public static void main(String[] args) {
        parkAndunpark();
    }
}
