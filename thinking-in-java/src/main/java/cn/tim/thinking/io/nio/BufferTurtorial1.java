package cn.tim.thinking.io.nio;

import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/**
 * NIO中的Buffer用于和NIO通道进行交互，数据从通道写入到缓冲区，从缓冲区写入到通道中。
 * 缓冲区本质是一块可以写入数据，然后可以从中读取数据的内存。这块内存被包装成NIO Buffer对象
 * User: luolibing
 * Date: 2017/8/18 18:20
 */
public class BufferTurtorial1 {

    // 用法
    @Test
    public void standard() throws IOException {
        FileChannel channel = new FileOutputStream("D://error.log").getChannel();
        // 1 分配空间
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        // 2 写入数据
        channel.write(buffer);
        // 3 flip重置position
        buffer.flip();
        Charset utf8 = Charset.forName("utf-8");
        // 4 读取数据
        System.out.println(utf8.decode(buffer));
        // 5 清空数据，重置
        buffer.clear();
    }
}
