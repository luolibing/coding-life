package cn.tim.thinking.concurrent21.thread;

import java.io.IOException;

/**
 * Created by LuoLiBing on 16/10/17.
 */
public class UnresponsiveUIDemo {

    class UnresponsiveUI {
        private volatile double d = 1;
        public UnresponsiveUI() throws IOException {
            while (d > 0) {
                d = d + (Math.PI + Math.E) / d;
            }
            System.in.read();
        }
    }

    static class ResponsiveUI extends Thread {
        private static volatile double d = 1;
        public ResponsiveUI() {
            // 后台线程,main执行完毕会被中止
            setDaemon(true);
            start();
        }

        @Override
        public void run() {
            while (true) {
                d = d + (Math.PI + Math.E) / d;
            }
        }

        public static void main(String[] args) throws IOException {
            new ResponsiveUI();
            // 等待输入
            System.in.read();
            System.out.println(d);
        }
    }

}
