package cn.tim.thinking.concurrent21.resource;

/**
 * Created by LuoLiBing on 16/10/17.
 * 解决共享资源竞争
 *
 * 解决共享资源的方法是当资源被一个任务使用时,在其上加锁,第一个访问某资源的任务必须锁定这项资源,使其他任务在被解锁之前无法访问它.在它被解锁之后,另一个任务又可以锁定它
 * 互斥量,临界区,临界资源
 *
 * 要控制对共享资源的访问,得先把它包装金一个对象,然后把所有访问这个资源的方法标记为synchronized,如果某个任务处于一个标记为synchronized的方法中,则在返回前,这个类中所有被标记为synchronized方法的线程都会被阻塞.
 * 一个任务可以多次获得对象的锁,jvm负责跟踪对象被加锁的次数,如果一个对象被解锁,其计数为0,在一次加锁后变为1,在这个相同的任务上再次获得锁时,计数会自增.释放递减,显然只有首次获得锁的任务才能允许继续获得多个锁.]
 *
 * 同步规则:
 * 如果你正在写一个变量,它可能接下来将被另一个线程读取,或者正在读取一个上一次已经被另一个线程写过的变量,那么你必须使用同步,并且,读写线程都必须用相同的监视器锁同步.
 *
 */
public class SynchronizedEvenGeneratorDemo {

    static class SynchronizedEvenGenerator extends EvenCheckerDemo.IntGenerator {

        private int currentEventValue = 0;

        @Override
        public synchronized int next() {
            ++ currentEventValue;
            Thread.yield();
            ++ currentEventValue;
            return currentEventValue;
        }

        public static void main(String[] args) {
            EvenCheckerDemo.EventChecker.test(new SynchronizedEvenGenerator());
        }
    }
}
