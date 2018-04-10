package cn.tim.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by luolibing on 2018/4/10.
 */
public class SelectorSample {

    @Test
    public void selector() throws IOException, InterruptedException {
        Selector selector = Selector.open();
        SocketChannel baidu = SocketChannel.open(new InetSocketAddress("www.baidu.com", 80));
        SocketChannel jd = SocketChannel.open(new InetSocketAddress("www.jd.com", 80));
        SelectionKey baiduKey = baidu.register(selector, SelectionKey.OP_READ);
        SelectionKey jdKey = jd.register(selector, SelectionKey.OP_READ);


        TimeUnit.SECONDS.sleep(10);
        int select = selector.select(10_000);
        System.out.println(select);
    }


    public void server() throws IOException {
        ServerSocketChannel server = ServerSocketChannel.open();
        server.bind(new InetSocketAddress("127.0.0.1", 80));
        server.configureBlocking(false);

        Selector selector = Selector.open();
        server.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            int acceptCount = selector.select();
            if(acceptCount == 0) {
                continue;
            }

            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            selectionKeys.forEach(key -> {
                if(key.isAcceptable()) {

                }
            });
        }
    }
}
