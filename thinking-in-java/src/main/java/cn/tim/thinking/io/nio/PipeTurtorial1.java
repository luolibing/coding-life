package cn.tim.thinking.io.nio;

import org.junit.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Pipe管道是2个线程之间的单向数据连接。Pipe数据写入到sink通道，从source通道读取。
 * User: luolibing
 * Date: 2017/8/25 17:49
 */
public class PipeTurtorial1 {

    private Pipe pipe = Pipe.open();

    public PipeTurtorial1() throws IOException {
    }


    /**
     * sink端写入
     * @throws IOException
     */
    public void sink() throws IOException {
        // 获取到sink通道，写入数据
        Pipe.SinkChannel sinkChannel = pipe.sink();
        ByteBuffer buffer = ByteBuffer.wrap("hello, world!".getBytes());

        while (buffer.hasRemaining()) {
            sinkChannel.write(buffer);
        }
        sinkChannel.close();
    }

    /**
     * 从source端读取数据
     */
    public void source() throws IOException {
        Pipe.SourceChannel sourceChannel = pipe.source();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int len = sourceChannel.read(buffer);
        buffer.flip();
        System.out.println("len = " + len);
        System.out.println("content = " + Charset.forName("utf-8").decode(buffer));
        sourceChannel.close();
    }

    @Test
    public void pipe() throws IOException, InterruptedException {
        PipeTurtorial1 pipeTur = new PipeTurtorial1();
        // 线程A和线程B之间通过pipe进行通信
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.submit(() -> {
            try {
                pipeTur.source();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        exec.submit(() -> {
            try {
                pipeTur.sink();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Thread.sleep(10000);
    }
}
