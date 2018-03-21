package cn.tim.nio;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

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
}
