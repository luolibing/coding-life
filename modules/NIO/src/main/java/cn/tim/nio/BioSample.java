package cn.tim.nio;

import org.junit.Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by luolibing on 2018/9/18.
 */
public class BioSample {

    @Test
    public void bio() throws IOException {
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 4);
        ServerSocket server = new ServerSocket(8888);
        while (!Thread.currentThread().isInterrupted()) {
            Socket socket = server.accept();
            executor.submit(new ConnectionHandler(socket));
        }
    }

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

}
