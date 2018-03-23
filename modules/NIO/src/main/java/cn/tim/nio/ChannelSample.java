package cn.tim.nio;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.*;

/**
 * Created by luolibing on 2018/3/21.
 */
public class ChannelSample {

    @Test
    public void channel() throws IOException {
        FileInputStream in = new FileInputStream(new File("/sfds"));
        FileChannel channel = in.getChannel();
        // 抛出异常，因为是只读的channel
        channel.write(ByteBuffer.wrap("abc".getBytes()));
    }

    @Test
    public void copy() throws IOException {

    }

    public static void main(String[] args) throws IOException {
        ReadableByteChannel readableByteChannel = Channels.newChannel(System.in);
        WritableByteChannel writableByteChannel = Channels.newChannel(System.out);

        copyChannel1(readableByteChannel, writableByteChannel);

        // 可能会产生阻塞，多线程同时操作的时候如果当前正有线程在关闭通道，其他线程会阻塞掉。多次调用没有影响，保证了幂等
        readableByteChannel.close();
        writableByteChannel.close();
    }

    private static void copyChannel1(ReadableByteChannel readableByteChannel, WritableByteChannel writableByteChannel) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024 * 10);
        while (readableByteChannel.read(buffer) != -1) {
            buffer.flip();
            // 这里可能只写入一部分，或者完全没有
            writableByteChannel.write(buffer);

            // 压缩，防止有部分写入的情况，其实就是将没有写入的移动到队头
            buffer.compact();
        }

        buffer.flip();
        while (buffer.hasRemaining()) {
            writableByteChannel.write(buffer);
        }
    }

    private static void copyChannel2(ReadableByteChannel readableByteChannel, WritableByteChannel writableByteChannel) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024 * 10);
        while (readableByteChannel.read(buffer) != -1) {
            buffer.flip();

            while (buffer.hasRemaining()) {
                writableByteChannel.write(buffer);
            }

            buffer.clear();
        }
    }


    @Test
    public void scatterAndGather() throws IOException {
        ScatteringByteChannel in = null;
        ByteBuffer header = ByteBuffer.allocateDirect(10);
        ByteBuffer body = ByteBuffer.allocateDirect(30);
        in.read(new ByteBuffer[] {header, body});

        GatheringByteChannel out = null;
        header.clear();
        header.put("OK 200".getBytes()).flip();

        body.clear();
        body.put("<html></html>".getBytes()).flip();
        // 第1个和第2个缓冲区将被写入
        out.write(new ByteBuffer[] {header, body}, 0, 2);
    }



}
