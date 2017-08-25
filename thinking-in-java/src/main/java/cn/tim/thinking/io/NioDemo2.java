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
     * 分散和聚集
     * 分散（scatter）将从Channel中读取的数据写入到多个buffer当中
     * 聚集（gather）将多个buffer中的数据汇总后发送到channel当中
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
            // 发散到各个buffers当中
            request.read(buffers);
//            header.flip();
//            body.flip();
            System.out.println(header.getShort(0));
            switch (header.getShort(0)) {
                case 24845: // ping
                    System.out.println("ping");
                    break;
                case 20565: // http
                    body.clear();
                    // 重新写回
                    body.put("luolibing".getBytes()).flip();
                    header.putShort((short) 20565).putLong(body.limit()).flip();
                    // 聚集
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
