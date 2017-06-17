package cn.tim.thinking.io;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by LuoLiBing on 17/2/15.
 */
public class NioDemo2 {

    /**
     * Scatter/Gather
     *
     */
    @Test
    public void server() throws IOException {
        ServerSocketChannel server = ServerSocketChannel.open();
        server.socket().bind(new InetSocketAddress(8001));
        while (true) {
            SocketChannel request = server.accept();
            ByteBuffer header = ByteBuffer.allocateDirect(1024);
            ByteBuffer body = ByteBuffer.allocateDirect(1024);
            ByteBuffer[] buffers = {header, body};
            request.read(buffers);
//            System.out.println(header.getShort(0));
            switch (header.getShort(0)) {
                case 24845: // ping
                    System.out.println("ping");
                    break;
                case 20565: // http
                    body.clear();
                    // 重新写回
                    body.put("luolibing".getBytes()).flip();
                    header.putShort((short) 20565).putLong(body.limit()).flip();
                    request.write(buffers);
                    System.out.println("client");
                    break;
            }
        }
    }


    @Test
    public void server2() throws IOException {
        ServerSocketChannel server = ServerSocketChannel.open();
        server.socket().bind(new InetSocketAddress(8001));
        server.configureBlocking(false);

    }
}
