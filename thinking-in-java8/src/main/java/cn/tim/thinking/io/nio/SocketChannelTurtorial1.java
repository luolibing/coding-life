package cn.tim.thinking.io.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Objects;

/**
 * User: luolibing
 * Date: 2017/8/25 16:19
 */
public class SocketChannelTurtorial1 {

    @Test
    public void client() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        // 开启非阻塞模式，非阻塞模式下，调用connect会立刻返回，所以需要在后面判断是否连接上
//        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 80));
        ByteBuffer writeBuffer = ByteBuffer.wrap("close".getBytes());
        while (writeBuffer.hasRemaining()) {
            socketChannel.write(writeBuffer);
        }

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int len = socketChannel.read(buffer);
        buffer.flip();
        System.out.println("read length = " + len);
        Charset utf8 = Charset.forName("utf-8");
        System.out.println(utf8.decode(buffer));
        socketChannel.close();
    }


    @Test
    public void server() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress("127.0.0.1", 80));
        // 非阻塞模式
        // serverSocketChannel.configureBlocking(false);
        Charset utf8 = Charset.forName("utf-8");
        while (true) {
            // 默认为阻塞模式
            SocketChannel socketChannel = serverSocketChannel.accept();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            socketChannel.read(buffer);
            buffer.flip();

            String request = utf8.decode(buffer).toString();
            if(Objects.equals(request, "close")) {
                serverSocketChannel.close();
            }

            // handler

            System.out.println("received request = " + request);
        }
    }
}
