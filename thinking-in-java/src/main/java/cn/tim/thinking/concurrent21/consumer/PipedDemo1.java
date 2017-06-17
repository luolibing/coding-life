package cn.tim.thinking.concurrent21.consumer;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by LuoLiBing on 16/11/1.
 * 任务间通过PipedWriter和PipedReader管道进行输入输出通信
 * 如果启动了一个没有构造完毕的对象,在不同平台管道可能会产生不一致的行为,所以使用BlockingQueue是更为稳妥的方法
 * PipedReader与普通的IO之间最重要的差异,PipedReader是可中断的,如果将in.read()改成System.in.read().interrupt()将不能打断read()的调用
 */
public class PipedDemo1 {

    // 生产者
    static class Sender implements Runnable {

        private Random rand = new Random(47);

        // sender通过PipedWriter向消费者写入信息
        private PipedWriter out = new PipedWriter();

        public PipedWriter getPipedWriter() {
            return out;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    for (char c = 'A'; c <= 'z'; c++) {
                        out.write(c);
                        TimeUnit.MILLISECONDS.sleep(rand.nextInt(500));
                    }
                }
            } catch (IOException e) {
                System.out.println(e + " Sender write exception");
            } catch (InterruptedException e) {
                System.out.println(e + " Sender sleep interrupted");
            }
        }
    }

    // 消费者
    static class Receiver implements Runnable {

        // 使用PipedReader接收sender发过来的消息
        private PipedReader in;

        public Receiver(Sender sender) throws IOException {
            in = new PipedReader(sender.getPipedWriter());
        }

        @Override
        public void run() {
            try {
                while (true) {
                    System.out.println("Read: " + (char) in.read() + ", ");
                }
            } catch (IOException e) {
                System.out.println(e + " Receiver read exception");
            }
        }
    }

    static class PipedIO {

        public static void main(String[] args) throws IOException, InterruptedException {
            Sender sender = new Sender();
            Receiver receiver = new Receiver(sender);
            ExecutorService exec = Executors.newCachedThreadPool();
            exec.execute(sender);
            exec.execute(receiver);
            TimeUnit.SECONDS.sleep(10);
            exec.shutdownNow();
        }
    }
}
