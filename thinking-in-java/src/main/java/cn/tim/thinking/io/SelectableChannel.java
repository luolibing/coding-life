package cn.tim.thinking.io;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * Created by LuoLiBing on 17/2/16.
 */
public class SelectableChannel {


    @Test
    public void test1() throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        ServerSocket serverSocket = serverChannel.socket();
        serverSocket.bind(new InetSocketAddress(8002));
        // 非阻塞
        serverChannel.configureBlocking(false);
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            // 有可能会阻塞很长一段时间,  一旦有请求到达, select将包含这些通道
            int n = selector.select();
            if(n == 0) {
                continue;
            }

            // 遍历select set里面的key
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            while (it.hasNext()) {
                SelectionKey key = it.next();

                // 判断此键对应的通道是否已经准备好连接新的socket
                if(key.isAcceptable()) {
                    ServerSocketChannel server = (ServerSocketChannel) key.channel();
                    SocketChannel request = server.accept();
                    registerChannel(selector, request, SelectionKey.OP_READ);
                    sayHello(request);
                }

                if(key.isReadable()) {
                    readDataFromSocket(key);
                }

                it.remove();
            }
        }
    }

    /**
     * 使用指定选择器为给定的操作指定通道
     * @param selector
     * @param channel
     * @param ops
     * @throws IOException
     */
    private void registerChannel(Selector selector, java.nio.channels.SelectableChannel channel, int ops) throws IOException {
        if(channel == null) {
            return;
        }

        channel.configureBlocking(false);
        channel.register(selector, ops);
    }

    private ByteBuffer buffer = ByteBuffer.allocateDirect(1024);

    /**
     * 从Socket中读取数据
     * @param key
     * @throws IOException
     */
    private void readDataFromSocket(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        int count;
        buffer.clear();

        while ((count = channel.read(buffer)) > 0) {
            buffer.flip();
            while (buffer.hasRemaining()) {
                channel.write(buffer);
            }
            buffer.clear();
        }

        if(count < 0) {
            channel.close();
        }
    }

    /**
     * 打印buffer中的数据
     * @param channel
     * @throws IOException
     */
    private void sayHello(SocketChannel channel) throws IOException {
        buffer.clear();
        buffer.put("Hi there! \r\n".getBytes());
        buffer.flip();
        channel.write(buffer);
    }
}
