package cn.tim.thinking.io.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.Charset;

/**
 * UDP数据传输
 * User: luolibing
 * Date: 2017/8/25 17:15
 */
public class DatagramChannelTurtorial1 {

    /**
     * 因为UDP是无连接协议，所以发送数据之后，也不会确认对方是否收到。
     */
    @Test
    public void receive() throws IOException {
        DatagramChannel channel = DatagramChannel.open();
        channel.socket().bind(new InetSocketAddress(8888));
        // 如果使用的是read方法，会报NotYetConnectedException异常，无连接不能使用read()
        ByteBuffer buffer = ByteBuffer.allocate(10);
        channel.receive(buffer);

        buffer.flip();
        Charset utf8 = Charset.forName("utf-8");
        System.out.println(utf8.decode(buffer));
    }

    /**
     * 发送方，即使接收方，没有启动，照样会发送出去，不会报错，而tcp不行
     * @throws IOException
     */
    @Test
    public void send() throws IOException {
        DatagramChannel channel = DatagramChannel.open();
        int len = channel.send(
                ByteBuffer.wrap("luolibing hello, good morning!".getBytes()),
                new InetSocketAddress("127.0.0.1", 8888));
        System.out.println(len);
    }


    @Test
    public void connect() throws IOException {
        DatagramChannel channel = DatagramChannel.open();
        channel.connect(new InetSocketAddress("127.0.0.1", 9999));

    }
}
