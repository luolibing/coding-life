package cn.tim.thinking.io.nio;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * Channels     通道
 * Buffers      缓冲
 * Selectors    选择器
 *
 * User: luolibing
 * Date: 2017/8/18 11:26
 */
public class NioTutorial1 {

    /**
     * FileChannel
     * @throws FileNotFoundException
     */
    @Test
    public void fileChannel() throws FileNotFoundException {
        // FileChannel
        FileChannel fc = new FileOutputStream("D://pop-admin-error.log").getChannel();
    }


    /**
     * ServerSocketChannel
     * SocketChannel
     * @throws IOException
     */
    @Test
    public void tcpServer() throws IOException {
        // TCP通信
        ServerSocketChannel server = ServerSocketChannel.open();
        server.bind(new InetSocketAddress("127.0.0.1", 80));
        while (true) {
            SocketChannel sc = server.accept();
            ByteBuffer bf = ByteBuffer.allocate(1024);
            sc.read(bf);
            bf.flip();
            Charset utf8 = Charset.forName("UTF-8");
            System.out.println(utf8.decode(bf));
            sc.write(ByteBuffer.wrap("ack".getBytes("utf-8")));
        }
    }


    @Test
    public void tcpClient() throws IOException {
        SocketChannel sc = SocketChannel.open(new InetSocketAddress("127.0.0.1", 80));
        sc.write(ByteBuffer.wrap("hello0!".getBytes("utf-8")));
        ByteBuffer resp = ByteBuffer.allocate(1000);
        sc.read(resp);
        resp.flip();
        Charset utf8 = Charset.forName("UTF-8");
        System.out.println(utf8.decode(resp));
    }


    /**
     * DatagramChannel
     * UDP接收端
     * @throws IOException
     */
    @Test
    public void udpServer() throws IOException {
        // DatagramChannel数据报通信UDP
        DatagramChannel dc = DatagramChannel.open();
        dc.bind(new InetSocketAddress("127.0.0.1", 88));
        while (true) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(47);
            byteBuffer.clear();
            dc.receive(byteBuffer);
            byteBuffer.flip();
            Charset utf8 = Charset.forName("UTF-8");
            System.out.println(utf8.decode(byteBuffer));
        }
    }
    
    @Test
    public void udpClient() throws IOException {
        DatagramChannel dc = DatagramChannel.open();
        String message = "您好呀，搞什么飞机";
        ByteBuffer bf = ByteBuffer.allocate(47);
        bf.clear();
        bf.put(message.getBytes());
        bf.flip();
        dc.send(bf, new InetSocketAddress("127.0.0.1", 88));
    }

    /**
     * Selector允许单线程处理多个Channel. 聊天是一个典型的Selector的例子.
     * 多个聊天Channel注册到Selector当中，然后调用select（）方法，阻塞住直到注册的channel中有事件发生，阻塞放开。
     */
    @Test
    public void selector() {

    }
}
