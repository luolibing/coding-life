package com.tim.interview.nio;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * bio和nio模型的区别
 * Created by luolibing on 2018/9/25.
 */
public class IOModel {

    static class BIOModel {
        static class ConnectionHandler implements Runnable {

            private Socket client;

            public ConnectionHandler(Socket socket) {
                this.client = socket;
            }

            @Override
            public void run() {
                try (InputStream inputStream = client.getInputStream(); OutputStream outputStream = client.getOutputStream()){
                    byte[] bytes = new byte[inputStream.available()];
                    inputStream.read(bytes);
                    String str = new String(bytes);
                    System.out.println(str);

                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(outputStream));
                    out.write("200 OK");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        static class Server implements Runnable {

            @Override
            public void run() {
                try {
                    ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 4);
                    ServerSocket server = new ServerSocket(8888);
                    while (!Thread.currentThread().isInterrupted()) {
                        Socket socket = server.accept();
                        executor.submit(new ConnectionHandler(socket));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public static void main(String[] args) throws IOException {
            new Thread(new Server()).start();
            System.in.read();
        }
    }


    static class NIOModel {
        static class Server implements Runnable {

            @Override
            public void run() {
                try {
                    ServerSocketChannel server = ServerSocketChannel.open();
                    server.bind(new InetSocketAddress("127.0.0.1", 8888));

                    Selector selector = Selector.open();
                    server.configureBlocking(false);
                    server.register(selector, SelectionKey.OP_ACCEPT);
                    while (!Thread.interrupted()) {
                        int count = selector.select();
                        if(count == 0) {
                            continue;
                        }

                        Set<SelectionKey> selectionKeys = selector.selectedKeys();
                        selectionKeys.stream()
                                .forEach(selectionKey -> {
                                    if(selectionKey.isAcceptable()) {
                                        try {
                                            ServerSocketChannel s = (ServerSocketChannel) selectionKey.channel();
                                            SocketChannel req = s.accept();
                                            registerChannel(selector, req, SelectionKey.OP_READ);
                                            welcome(req);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    if(selectionKey.isReadable()) {
                                        try {
                                            readResponse(selectionKey);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
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


            private void welcome(SocketChannel channel) throws IOException {
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                buffer.put("Hi there! \r\n".getBytes());
                buffer.flip();
                channel.write(buffer);
            }

            private void readResponse(SelectionKey key) throws IOException {
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                SocketChannel channel = (SocketChannel) key.channel();
                int count;

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

                System.out.println(buffer.toString());
            }
        }

        public static void main(String[] args) throws IOException {
            new Thread(new Server()).start();
            System.in.read();
        }
    }
}
