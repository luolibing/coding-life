package cn.tim.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

/**
 * Created by luolibing on 2018/3/28.
 */
public class SocketChannelSample {

    @Test
    public void block() throws IOException {
        ServerSocketChannel serverSocket = ServerSocketChannel.open();
        serverSocket.bind(new InetSocketAddress("127.0,0,1", 8070));

        // 获取锁，防止在执行重要操作的时候，阻塞模式被修改
        Object lockObj = serverSocket.blockingLock();
        synchronized (lockObj) {
            boolean prevState = serverSocket.isBlocking();
            // 切换为非阻塞模式，默认是阻塞模式
            serverSocket.configureBlocking(false);
            // do some thing
            // 修改回之前的模式
            serverSocket.configureBlocking(prevState);
        }
    }


    @Test
    public void accept() throws IOException, InterruptedException {
        int port = 8070;
        String greet = "hello, welcome to beijing!";
        ByteBuffer buffer = ByteBuffer.wrap(greet.getBytes());
        ServerSocketChannel server = ServerSocketChannel.open();
        server.bind(new InetSocketAddress(port));
        server.configureBlocking(false);

        while (true) {
            System.out.println("waiting for connect");
            // 非阻塞模式会直接返回
            SocketChannel socketChannel = server.accept();

            if(socketChannel == null) {
                TimeUnit.MILLISECONDS.sleep(2000);
            } else {
                // 每个socketChannel都对应一个socket对象，但是反过来却不成立，直接创建的socket没有socketChannel对象
                Socket socket = socketChannel.socket();
                System.out.println("Incoming connection from " + socketChannel.socket().getRemoteSocketAddress());
                buffer.rewind();
                socketChannel.write(buffer);
                socketChannel.close();
            }
        }
    }

    @Test
    public void connect() throws IOException {
        InetSocketAddress address = new InetSocketAddress(8070);
        SocketChannel sc = SocketChannel.open();
        sc.configureBlocking(false);
        sc.connect(address);

        // 连上之前做一些其他的事情
        while (!sc.finishConnect()) {
            System.out.println("do Some thing else");
        }

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (sc.read(buffer) != -1) {
            buffer.flip();
            System.out.println(new String(buffer.array()));
            buffer.clear();
        }
        sc.close();
    }
}
