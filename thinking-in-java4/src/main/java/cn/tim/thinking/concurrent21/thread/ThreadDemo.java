package cn.tim.thinking.concurrent21.thread;

import org.junit.Test;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * Created by LuoLiBing on 16/10/9.
 * 将Runnable对象转变为工作任务的传统方式是把它提交给一个Thread构造器
 *
 * Thread类
 * 1 每个线程都有一个优先级,高优先级优先于低优先级执行.新线程的初始优先级被设置为创建线程
 * 2 线程可以被标记为守护线程. 当jvm启动的时候,通常会有一个单独的非守护线程main()方法
 * 3 jvm将继续执行线程,直到发生以下情况
 *      1 调用了Runtime.exit()已经执行,并且安全管理器SecurityManager允许退出
 *      2 所有非守护线程都died,或者调用run()方法,抛出一个异常跳出了run方法
 * 4 创建执行线程的两种方式
 *      1 继承自Thread类,重写run()方法  new PrimeRun().start()
 *      2 另一种方式,是实现Runnable接口,将其作为参数传递给Thread类 new Thread(new PrimeRunnable()).start(); Thread类实现了Runnable接口
 * 5 每个线程都有一个自己的唯一标识名称,多个线程可能拥有相同的名字
 *
 * Thread类的组成
 * 字段
 *  name[]          名称
 *  priority        优先级
 *  daemon          守护线程
 *  target          runnable执行的任务
 *  group           threadGroup线程组
 *  contextClassLoader 线程类加载器
 *  autonumbering   自增匿名线程计数
 *  threadLocals    ThreadLocalMap 线程相关本地变量map
 *  inheritableThreadLocals 继承线程上下文
 *  tid             线程Id
 *  threadSeqNumber 线程序列id,自增
 *  threadStatus    线程状态,默认为0,还没有启动
 *  parkBlocker     记录被那个对象阻塞,通过LockSupport.getBlocker来获取阻塞对象
 *  blocker         IO中断
 *  blockerLock     IO中断锁
 *
 *  优先级
 *  MIN_PRIORITY    1
 *  NORM_PRIORITY   5  默认优先级
 *  MAX_PRIORITY    10
 *
 * 方法
 *  currentThread()     当前线程
 *  yield()             给调度器的一个提示,当前线程自愿让出占有的处理器.调度器可以忽略这个提示.yield只是一个建议,采不采纳不一定.
 *  sleep(millis, nanos)睡眠(微秒+纳秒),暂停执行,但不会失去对处理器的占用
 *  init()              线程初始化,ThreadGroup,Runnable,name,stackSize,acc
 *  start()             使该线程开始运行; JVM调用该线程的run方法; 结果是两个线程并行执行,主线程和执行run方法的线程; 一个线程只能启动一次,不能重复启动
 *  start0()            native方法,启动线程交给jvm底层来实现,jvm底层会自动来调用run()方法
 *  run()               调用Runnable的run方法
 *  exit()              JVM调用,以供线程退出时,清理作用
 *  interrupt()         调用中断,但是仅仅是设置一个中断状态,至于何时实施中断,则由线程本身来决定, http://www.infoq.com/cn/articles/java-interrupt-mechanism
 *  static interrupt()  判断当前线程的中断状态
 *      中断的使用
 *          1 点击某个应用中的取消按钮时
 *          2 某个操作超过一定的执行时间限制需要中止时
 *          3 多个线程做相同的事情,只要有一个线程成功其他的线程就可以取消时
 *          4 一组线程中的一个活多个出现错误导致整组都无法继续时
 *          5 当一个应用或服务需要停止时
 *  isAlive()           判断当前线程是否还存活
 *  setPriority()       设置线程优先级
 *  activeCount()       活跃线程总数
 *  join()              join(millis,nanos),等待线程执行完毕(或者等待制定时间之后died)died再继续执行
 *  dumpStack()         打印线程错误堆栈日志
 *  setDaemon()         设置守护线程,只有当所有非守护线程都退出之后jvm才会退出
 *  checkAccess()       判断当前线程是否有权限修改对应的线程
 *  getContextClassLoader()获取上下文的类加载器
 *  holdsLock(obj)static是否持有某个对象的锁
 *  getStackTrace()     返回执行堆栈
 *  getAllStackTraces() 返回所有的线程执行堆栈
 *
 * 线程的状态
 * NEW(创建),RUNNABLE(可运行),BLOCKED(阻塞),WAITING(等待),TIMED_WAITING(超时等待),TERMINATED(结束)
 *
 */
public class ThreadDemo {

    private int blocker;

//    private static final sun.misc.Unsafe UNSAFE;
//
//
//    static {
//        try {
//            UNSAFE = sun.misc.Unsafe.getUnsafe();
//            Class<?> tk = ThreadDemo.class;
//            long offset = UNSAFE.objectFieldOffset(tk.getDeclaredField("blocker"));
//            System.out.println(offset);
//        } catch (Exception ex) { throw new Error(ex); }
//    }

    /**
     * 单线程执行
     * @param args
     */
    public static void main1(String[] args) {
        System.out.println("mainThreadId: " + Thread.currentThread().getId());
        Thread th = new Thread(new RunnableDemo.LiftOff());
        th.start();
        System.out.println("Waiting for LiftOff");
    }

    public static void main2(String[] args) {
        // Runtime.getRuntime().exit(1);
        System.out.println("mainThreadId: " + Thread.currentThread().getId());
        for(int i = 0, size = Runtime.getRuntime().availableProcessors(); i < size; i ++) {
            new Thread(new RunnableDemo.LiftOff()).start();
        }
        System.out.println("Waiting for LiftOff");
    }

    public static void main3(String[] args) throws NoSuchFieldException {
//        long offset = Unsafe.getUnsafe().objectFieldOffset(ThreadDemo.class.getDeclaredField("blocker"));
//        System.out.println(offset);
        Thread th = Thread.currentThread();
        System.out.println("start");
        LockSupport.park(th);
        System.out.println("locked th thread");
        LockSupport.unpark(th);
        System.out.println("end");
    }

    @Test
    public void threadGroup() {
        SecurityManager securityManager = System.getSecurityManager();
        ThreadGroup threadGroup;
        if(securityManager != null) {
            threadGroup = securityManager.getThreadGroup();
        } else {
            threadGroup = Thread.currentThread().getThreadGroup();
        }
        System.out.println(threadGroup);
    }

    @Test
    public void thread() {
        new Thread("thread-Name");
    }

    /**
     * 等待某个线程执行完毕再进行下面的操作
     * @throws InterruptedException
     */
    @Test
    public void join() throws InterruptedException {
        Thread t1 = new Thread(new ThreadA());
        Thread t2 = new Thread(new ThreadB());
        t1.start();
        t1.join(1000);
        t2.start();
        t2.join();
    }

    /**
     * 中断
     * @throws InterruptedException
     */
    @Test
    public void interrupted() throws InterruptedException {
        Thread t1 = new Thread(new ThreadA());
        Thread t2 = new Thread(new ThreadB());
        t1.start();
        t2.start();
        while (count < 1000) {
            TimeUnit.MILLISECONDS.sleep(100);
        }
        t1.interrupt();
        t2.interrupt();
    }

    @Test
    public void getAllStackTraces() {
        Map<Thread, StackTraceElement[]> stackTraces = Thread.getAllStackTraces();
        System.out.println(stackTraces);
    }

    private int count = 0;

    class ThreadA implements Runnable {
        @Override
        public void run() {
            System.out.println("ThreadA run!!!");
            while (true) {
                try {
                    System.out.println("count=" + count++);
                    TimeUnit.MILLISECONDS.sleep(100);
                    System.out.println("ThreadA end!!!");
                } catch (InterruptedException e) {
                    System.err.println("ThreadA interrupted");
                }
            }
        }
    }

    class ThreadB implements Runnable {
        @Override
        public void run() {
            System.out.println("ThreadB run!!!");
            while (true) {
                try {
                    System.out.println("count=" + count++);
                    TimeUnit.MILLISECONDS.sleep(100);
                    System.out.println("ThreadB end!!!");
                } catch (InterruptedException e) {
                    System.err.println("ThreadB interrupted");
                }
            }
        }
    }

    public static void main(String[] args) {
        Thread th = new Thread(() -> {
            System.out.println("startThread");
            throw new RuntimeException("aaaa");
        });
        th.setUncaughtExceptionHandler((t, e) -> {
            System.out.println("caught the exception = " + e.getMessage());
            throw new RuntimeException(e);
        });
        try {
            th.start();
        } catch (Exception e) {
            System.out.println("caught thread " + e.getMessage());
        }
    }


    @Test
    public void test() throws InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();
        for(int i = 0; i < 100; i++) {
//            exec.execute(() -> {
//                try {
//                    Hello.sayHello();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            });
            new Thread(() -> {
                try {
                    Hello.sayHello();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        Thread.sleep(Integer.MAX_VALUE);
    }


    static class Hello {
        public synchronized static void sayHello() throws InterruptedException {
            System.out.println("Hello");
            Thread.sleep(10);
        }
    }
}
