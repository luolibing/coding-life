package cn.tim.thinking.concurrent21.end;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.ClosedByInterruptException;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by LuoLiBing on 16/10/21.
 * 使用NIO中断
 */
public class NIOBlockedDemo {

    static class NIOBlocked implements Runnable {

        private final SocketChannel sc;

        public NIOBlocked(SocketChannel sc) {
            this.sc = sc;
        }

        @Override
        public void run() {
            System.out.println("Waiting for read() in " + this);
            try {
                sc.read(ByteBuffer.allocate(1));
            } catch (ClosedByInterruptException e) {
                e.printStackTrace();
            } catch (AsynchronousCloseException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Exiting NIOBlocked.run() " + this);
        }
    }

    static class NIOInterruption {
        public static void main(String[] args) throws IOException, InterruptedException {
            ExecutorService exec = Executors.newCachedThreadPool();
            ServerSocket server = new ServerSocket(8080);
            InetSocketAddress isa = new InetSocketAddress("localhost", 8080);
            SocketChannel sc1 = SocketChannel.open(isa);
            SocketChannel sc2 = SocketChannel.open(isa);
            exec.execute(new NIOBlocked(sc1));
            exec.execute(new NIOBlocked(sc2));
            TimeUnit.MILLISECONDS.sleep(100);
            exec.shutdownNow();

            // 关闭底层资源
            sc1.close();
            sc2.close();
            System.in.close();
        }
    }

    static class NoTask  {
        public void execute() throws InterruptedException {
            Thread.sleep(1000000);
        }

        public void release() {
            System.out.println("NoTask release!!");
        }
    }

    static class Task implements Runnable {
        private NoTask noTask;
        public Task(NoTask noTask) {
            this.noTask = noTask;
        }

        @Override
        public void run() {
            try {
                noTask.execute();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                noTask.release();
            }
        }

        public static void main(String[] args) {
            Thread th = new Thread(new Task(new NoTask()));
            th.start();
            th.interrupt();
        }
    }
}
