package cn.tim.thinking.concurrent21.end;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Created by LuoLiBing on 16/10/21.
 * 线程状态
 * 新建(new) 就绪(Runnable) 阻塞(Blocked) 死亡(Dead)
 *
 * 进入阻塞状态
 * 1 sleep() 调用sleep()方法时任务进入休眠状态,这种情况下,任务在制定的时间内不会运行
 * 2 wait()  调用wait()犯法使线程挂起,直到线程得到notify()或notifyAll()消息,线程才会进入就绪状态
 * 3 任务在等待某个输入/输出完成
 * 4 任务试图在某个对象上调用其同步控制方法,但是对象锁不可用,因为另一个任务已经获取了锁
 *
 * 中断
 * 在Runnable.run()方法的中间打断它,比等待方法到达例如cancel标识的测试,或者到达准备好离开该方法的其他一些地方相比,要棘手得多.
 * 当你打断被阻塞的任务时,可能需要清理资源.因为这一点,在任务的run()方法中间打断,更像是抛出异常,然后在catch()中进行资源的清理.
 *
 * interrupt() 这个方法将设置线程的中断状态,然后线程会找到一个合适的时机进行中断. 如果一个线程已经被阻塞,或者试图执行一个阻塞操作,设置线程中断状态将抛出InterruptedException
 * 当抛出异常或者该任务调用Thread.interrupted()时,中断状态将被复位,状态继续设置为true. 能够中断对sleep()的调用,但是不能中断正在获取synchronized锁或者试图执行I/O操作的线程.
 * 一种行之有效的解决方案是,关闭任务在其上发生阻塞的底层资源.
 */
public class ThreadBlockDemo {

    public static void main(String[] args) throws InterruptedException {
        InterruptedTask task = new InterruptedTask();
        Thread th = new Thread(task);
        th.start();
        TimeUnit.SECONDS.sleep(5);

        th.interrupt();
    }

    // 中断sleep()
    static class InterruptedTask implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    System.out.println("Aaaaaaa=" + Thread.currentThread().isInterrupted());
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    // 使用抛出异常或者该任务调用Thread.interrupted(),中断状态将被复位
                    e.printStackTrace();
                }
            }
        }

        public void interrupt() {
            Thread.interrupted();
        }
    }

    // 中断sleep()
    static class SleepBlocked implements Runnable {

        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("InterruptedException");
            }
            System.out.println("Exiting SleepBlocked.run()");
        }
    }

    // 无法中断in.read()
    static class IOBlocked implements Runnable {

        private InputStream in;

        public IOBlocked(InputStream in) {
            this.in = in;
        }

        @Override
        public void run() {
            System.out.println("Waiting for read();");
            try {
                in.read();
            } catch (IOException e) {
                e.printStackTrace();
                if(Thread.currentThread().isInterrupted()) {
                    System.out.println("Interrupted from blocked I/O");
                } else {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("Exiting IOBlocked.run()");
        }
    }

    // 无法中断同步方法
    static class SynchronizedBlocked implements Runnable {

        public synchronized void f() {
            while (true) {
                Thread.yield();
            }
        }

        public SynchronizedBlocked() {
            // 先用一个线程永久获取锁,然后再起一下线程阻塞在这个f()方法这
            new Thread() {
                @Override
                public void run() {
                    f();
                }
            }.start();
        }

        @Override
        public void run() {
            System.out.println("Trying to call f()");
            f();
            System.out.println("Exiting SynchronizedBlocked.run()");
        }
    }

    static class Interrupting {
        private static ExecutorService exec = Executors.newCachedThreadPool();
        static void test(Runnable r) throws InterruptedException {
            Future<?> f = exec.submit(r);
            TimeUnit.MILLISECONDS.sleep(100);
            System.out.println("Interrupting " + r.getClass().getName());
            // 可单独中断的Future方式
            f.cancel(true);
            System.out.println("Interrupt sent to " + r.getClass().getName());
        }

        public static void main(String[] args) throws InterruptedException {
            test(new SleepBlocked());
            test(new IOBlocked(System.in));
            test(new SynchronizedBlocked());
            TimeUnit.SECONDS.sleep(3);
            System.exit(0);
        }
    }

    static class CloseResource {
        public static void main(String[] args) throws IOException, InterruptedException {
            ExecutorService exec = Executors.newCachedThreadPool();
            ServerSocket server = new ServerSocket(8080);
            InputStream socketInput = new Socket("localhost", 8080).getInputStream();
            exec.execute(new IOBlocked(socketInput));
            exec.execute(new IOBlocked(socketInput));
            TimeUnit.MILLISECONDS.sleep(100);
            exec.shutdownNow();

            // 关闭底层资源
            socketInput.close();
            System.in.close();
        }
    }
}
