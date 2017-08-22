package cn.tim.thinking.io.nio;

import org.junit.Test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/**
 * User: luolibing
 * Date: 2017/8/18 17:08
 */
public class ChannelTutorial1 {

    /**
     * Channel通道与流的区别
     * 1 Channel是双向的，既可以读且可以写，而流式单向
     * 2 Channel可以异步地读写
     * 3 Channel总是先给Buffer打交道，先读到一个Buffer，或者先写入到一个Buffer中
     * Channel不支持一行一行的读取
     */
    @Test
    public void channel1() throws IOException {
        RandomAccessFile file = new RandomAccessFile("D:\\all.log","rw");
        FileChannel channel = file.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(128);

        Charset utf8 = Charset.forName("utf-8");
        while (channel.read(buffer)!=-1) {
            buffer.flip();
            System.out.println(utf8.decode(buffer));
            buffer.clear();
        }
        file.close();
    }
}
