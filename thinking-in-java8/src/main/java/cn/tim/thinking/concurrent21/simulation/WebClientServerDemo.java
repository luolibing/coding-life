package cn.tim.thinking.concurrent21.simulation;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by LuoLiBing on 16/11/14.
 */
public class WebClientServerDemo {

    // 模拟web client
    static class WebClient  {
        private static int counter = 0;
        private final int id = counter++;
        private final int serviceTime;

        public WebClient(int serviceTime) {
            this.serviceTime = serviceTime;
        }

        public int getServiceTime() {
            return serviceTime;
        }

        @Override
        public String toString() {
            return "WebClient" + id + " serviceTime=[" + serviceTime + "] ";
        }
    }

    // web client队列
    static class WebClientLine extends ArrayBlockingQueue<WebClient> {

        public WebClientLine(int maxSize) {
            super(maxSize);
        }

        @Override
        public String toString() {
            if(size() == 0) {
                return "Empty";
            }
            StringBuilder sb = new StringBuilder();
            this.forEach(sb::append);
            return sb.toString();
        }
    }

    // 模拟web client生成
    static class WebClientGenerator implements Runnable {

        private final WebClientLine webClients;

        // 负载因子
        volatile int loadFactor = 1;

        private Random rand = new Random(47);

        WebClientGenerator(WebClientLine webClients) {
            this.webClients = webClients;
        }

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    TimeUnit.MILLISECONDS.sleep(rand.nextInt(500));
                    webClients.put(new WebClient(rand.nextInt(2000)));
                }
            } catch (InterruptedException e) {
                System.out.println("停止继续排队");
            }
        }
    }

    // 服务器
    static class Server implements Runnable {

        private static int counter = 0;

        private final int id = counter++;

        private AtomicInteger servedCount = new AtomicInteger(0);

        private final WebClientLine clients;

        public Server(WebClientLine clients) {
            this.clients = clients;
        }

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    WebClient client = clients.take();
                    TimeUnit.MILLISECONDS.sleep(client.getServiceTime());
                    synchronized (this) {
                        servedCount.incrementAndGet();
                    }
                }
            } catch (InterruptedException e) {
                System.out.println(this + "interrupted");
            }
            System.out.println(this + "terminating");
        }

        @Override
        public String toString() {
            return "Server " + id + " ";
        }

        public String shortString() {
            return "T" + id;
        }
    }

    // 模拟器
    static class SimulationManager implements Runnable {

        private ExecutorService exec;

        private WebClientLine webClients;

        private WebClientGenerator generator;

        private Queue<Server> servers = new LinkedList<>();

        private int adjustmentPeriod;

        private boolean stable = true;

        private int prevSize;

        public SimulationManager(ExecutorService exec, WebClientGenerator gen,
                                 WebClientLine clients, int adjustmentPeriod, int n) {
            this.exec = exec;
            this.generator = gen;
            this.webClients = clients;
            this.adjustmentPeriod = adjustmentPeriod;
            for(int i = 0; i < n; i ++) {
                Server server = new Server(clients);
                exec.execute(server);
                servers.add(server);
            }
        }

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    TimeUnit.MILLISECONDS.sleep(adjustmentPeriod);
                    System.out.println(webClients + "{");
                    for(Server server : servers) {
                        System.out.println(server.shortString() + " ");
                    }
                    System.out.println("}");
                    adjustLoadFactor();
                }
            } catch (InterruptedException e) {
                System.out.println(this + "interrupted");
            }
        }

        // 动态调节
        public void adjustLoadFactor() {
            if(webClients.size() > prevSize) {
                if(stable) {
                    stable = false;
                } else {
                    System.out.println("Peak load factor: ~" + generator.loadFactor);
                    exec.shutdownNow();
                }
            } else {
                System.out.println("New load factor: " + (++generator.loadFactor));
                stable = true;
            }
            prevSize = webClients.size();
        }

        @Override
        public String toString() {
            return "SimulationManager ";
        }
    }

    public static void main(String[] args) throws IOException {
        final int MAX_LINE_SIZE = 50;
        final int NUM_OF_SERVERS = 3;
        final int ADJUSTMENT_PERIOD = 1000;
        ExecutorService exec = Executors.newCachedThreadPool();
        WebClientLine clients = new WebClientLine(MAX_LINE_SIZE);
        WebClientGenerator generator = new WebClientGenerator(clients);
        exec.execute(generator);
        exec.execute(new SimulationManager(exec, generator, clients, ADJUSTMENT_PERIOD, NUM_OF_SERVERS));
        System.out.println("Press 'Enter' to quit");
        System.in.read();
        exec.shutdownNow();
    }
}
