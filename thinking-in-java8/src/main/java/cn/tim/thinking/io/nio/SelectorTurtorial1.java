package cn.tim.thinking.io.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

/**
 * selector可以绑定多个Channel，这样做的好处是，单线程处理多个channel，减少线程数，提高吞吐量
 * User: luolibing
 * Date: 2017/8/24 15:34
 */
public class SelectorTurtorial1 {

    @Test
    public void selector() throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel server = ServerSocketChannel.open();
        server.bind(new InetSocketAddress("127.0.0.1", 80));
        while (true) {
            server.configureBlocking(false);
            SocketChannel sc = server.accept();
            // 将通道注册到selector上，只读。为了将channel和selector配合使用，channel必须为非阻塞模式，而FileChannel不能切换为非阻塞模式，所以不能注册
            SelectionKey selectionKey = sc.register(selector, SelectionKey.OP_READ | SelectionKey.OP_ACCEPT | SelectionKey.OP_CONNECT | SelectionKey.OP_WRITE);
            // 获取到感兴趣的key
            int interestSet = selectionKey.interestOps();
            // 取交集看是否有选择OP_WRITE
            int isInterestedInWrite = interestSet & SelectionKey.OP_WRITE;
            System.out.println(isInterestedInWrite);

            // 就绪状态列表
            int i = selectionKey.readyOps();
            selectionKey.isAcceptable();

            // 根据key获取channel和selector
            SelectableChannel channel = selectionKey.channel();
            Selector selector1 = selectionKey.selector();

            // 附加对象的存取
            selectionKey.attach(new ArrayList<>());
            selectionKey.attachment();
            // 在register的时候将attachment附带上
            sc.register(selector, SelectionKey.OP_ACCEPT, new ArrayList<>());

            // 默认会阻塞住，直到至少有一个通道在注册的事件上就绪。返回的结果表示有几个通道就绪
            int selected = selector.select();
            // 所有就绪集的通道
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> it = selectionKeys.iterator();
            while (it.hasNext()) {
                SelectionKey key = it.next();
                if(key.isAcceptable()) {

                } else if(key.isConnectable()) {

                } else if(key.isReadable()) {

                } else if(key.isWritable()) {

                }
                it.remove();
            }

            // 唤醒selector，从select()的阻塞中走出，即使没有任何事件
            selector.wakeup();

            // 关闭selector
            selector.close();

            ByteBuffer bf = ByteBuffer.allocate(1024);
            sc.read(bf);
            bf.flip();
            Charset utf8 = Charset.forName("UTF-8");
            System.out.println(utf8.decode(bf));
            sc.write(ByteBuffer.wrap("ack".getBytes("utf-8")));
        }
    }

    @Test
    public void standard() throws IOException {
        ServerSocketChannel server = ServerSocketChannel.open();
        Selector selector = Selector.open();
        server.bind(new InetSocketAddress("127.0.0.1", 80));
        while (true) {
            server.configureBlocking(false);
            SocketChannel socketChannel = server.accept();
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE | SelectionKey.OP_CONNECT | SelectionKey.OP_ACCEPT);
            while (true) {
                // 阻塞住，当有事件发出会通过
                int readyChannels = selector.select();
                // 被wakeUp()
                if(readyChannels == 0) {
                    continue;
                }

                // 获取事件集合
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    if(selectionKey.isAcceptable()) {

                    } else if(selectionKey.isWritable()) {

                    }
                    iterator.remove();
                }
            }
        }
    }
}
