package cn.tim.thinking.io.nio;

import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * User: luolibing
 * Date: 2017/8/25 15:40
 */
public class FileChannelTurtorial1 {

    @Test
    public void write() throws IOException {
        FileOutputStream out = new FileOutputStream("filechannel.txt");
        FileChannel channel = out.getChannel();
        ByteBuffer buffer = ByteBuffer.wrap(("hello, good morning!" + System.currentTimeMillis()).getBytes());
        while (buffer.hasRemaining()) {
            // 不保证能够写入多少字节数据
            channel.write(buffer);

            // 将内存中的数据写入到硬盘，true表示同时将文件的元数据也写入到硬盘上
            channel.force(true);
        }

        // 获取channel当前位置
        long position = channel.position();
        // 指定位置写入
        channel.position(position + 128);
        buffer.clear();
        buffer.put("hihihi".getBytes());
        buffer.flip();
        while (buffer.hasRemaining()) {
            channel.write(buffer);
        }

        // 获取文件大小
        long size = channel.size();
        System.out.println(size);

        // 文件截取，将指定位置后面的部分截断
        channel.truncate(size - 1);
        channel.close();
    }
}
