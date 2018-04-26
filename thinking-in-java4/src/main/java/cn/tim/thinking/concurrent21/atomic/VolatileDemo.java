package cn.tim.thinking.concurrent21.atomic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by LuoLiBing on 16/10/17.
 * 原子性与易变性
 * 对于读取和写入除long和double之外的基本类型变量这样的操作,可以保证他们不会被当做不可分(原子)的操作来操作内存.
 * 但是JVM可以将64位(long和double变量)的读取和写入当做两个分离的32位操作来执行,这就产生了在一个读取和写入操作中间发生上下文切换,
 * 从而导致不同任务可以看到不正确结果的可能性.(这有时被称为字撕裂,因为你可能会看到部分被修改过的数值). 当定义long和double变量时,
 * 使用volatile关键词,就会获得(简单赋值与返回操作)原子性. 但是我们不应该依赖于平台的看似原子的操作. 因此原子操作应该由线程机制来保证其不可中断
 *
 * 同步机制强制在处理器系统中,一个任务作出的修改必须在应用中是可视的,如果没有同步机制,那么修改时可视将无法确定.
 *
 * volatile(易变):
 * 1 volatile确保了应用中的可视性,如果一个字段声明为volatile,那么只要对这个域产生了写操作,所有的读操作就都可以看到这个修改.对于只需要在任务内部可视的时候,没有必要将其设置为volatile
 *      即使使用了本地缓存,情况也确实如此,volatile域会立即被写入到主存中,而读操作就发生在主存中.
 * 2 原子操作和易变性是不同的概念这一点很重要.在非volatile域上的原子操作不必刷新到主存中去,因此其他读取该域的任务也不必看到这个新值.
 * 3 如果多个任务在  <<<同时>>>  访问某个域,那么这个域就应该是volatile,否则就应该只使用同步来访问.同步也会导致向主存刷新,因此就没有必要再使用volatile
 * 4 当一个域的值依赖于它之前的值时,例如递增一个计数器,volatile就无法工作了.如果某个域的值收到其他域的值的限制,那么volatile也无法工作,例如边界限制.
 * 5 使用volatile而不是synchronized的唯一安全的情况是类中只有一个可变的域.
 *
 *
 * Atomic(原子性)
 * java中 i++ i+=3都不是原子性的,从jvm指令中就可以看出来. 自增都有一个取i值至于栈顶,  aload_0, dup, 然后获取值 getfield #3 ,加载常量 iconst_1 ,执行加操作iadd, 将结果保存进i putfield
    0: aload_0
    1: dup
    2: getfield      #3                  // Field i:I
    5: iconst_1
    6: iadd
    7: putfield      #3                  // Field i:I
    10: return
   在get和put中间还有一些其他的指令,所以有可能会在这个中间另一个任务修改这个域,所以这些操作不是原子性的:
 *
 *
 */
public class VolatileDemo {

    class Atomicity {
        int i;

        void f1() {
            i++;
        }

        void f2() {
            i+=3;
        }
    }

    static class AtomicityTest implements Runnable {
        private volatile int i = 0;
        // 读取也使用同步代码块
        public synchronized int getValue() {
            return i;
        }

        // 同步代码块保证原子操作
        private synchronized void evenIncrement() {
            i++; i++;
        }

        @Override
        public void run() {
            while (true) {
                evenIncrement();
            }
        }

        public static void main(String[] args) {
            ExecutorService exec = Executors.newCachedThreadPool();
            AtomicityTest at = new AtomicityTest();
            exec.execute(at);
            while (true) {
                int val = at.getValue();
                if( val % 2 != 0) {
                    System.out.println(val);
                    System.exit(0);
                }
            }
        }
    }

    /**
     * 如果一个域可能会被多个任务同时访问,或者这些任务中至少有一个是写入任务,那么你就应该将这个域设置为volatile.这样会告诉编译器不要执行任务移除读取和写入操作的优化.
     * 这些操作的目的是用线程中局部变量维护队这个域的精确同步.实际上,读取和写入都是直接针对内存的,没有缓存.
     */
    static class SerialNumberGenerator {
        private static volatile int serialNumber = 0;
        // 加上同步就不会出错了,不适用同步,即使serialNumber用volatile修饰,可见性得到了保障,但是还不是安全的原子操作.解决方式是使用synchronized关键词
        public static synchronized int nextSerialNumber() {
            return serialNumber++;
        }
    }

    static class CircularSet {
        private int[] array;
        private int len;
        private int index = 0;
        public CircularSet(int size) {
            array = new int[size];
            len = size;
            for(int i = 0; i < size; i++) {
                array[i] = -1;
            }
        }

        public synchronized void add(int i) {
            array[index] = i;
            index = ++index % len;
        }

        public synchronized boolean contains(int val) {
            for(int i = 0; i < len; i++) {
                if(array[i] == val) return true;
            }
            return false;
        }
    }

    static class SerialNumberChecker {
        private final static int SIZE = 10;
        private static CircularSet serials = new CircularSet(1000);
        private static ExecutorService exec = Executors.newCachedThreadPool();

        static class SerialChecker implements Runnable {

            @Override
            public void run() {
                while (true) {
                    int serial = SerialNumberGenerator.nextSerialNumber();
                    if (serials.contains(serial)) {
                        System.out.println("duplicate: " + serial);
                        System.exit(0);
                    }
                    serials.add(serial);
                }
            }
        }

        public static void main(String[] args) {
            for(int i = 0; i < SIZE; i++) {
                exec.execute(new SerialChecker());
            }
        }
    }

}
