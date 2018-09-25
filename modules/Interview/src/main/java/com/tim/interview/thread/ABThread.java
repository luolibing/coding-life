package com.tim.interview.thread;

import org.junit.Test;

import java.io.IOException;

/**
 * 线程AB，交替打印AB，面试的时候，还是有很多细节需要注意。
 * Created by luolibing on 2018/9/25.
 */
public class ABThread {

    // 前提2个线程之间得有共享的资源，存在资源竞争。
    static class ShareResource {
        private boolean isA = true;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        ShareResource shareResource = new ShareResource();
        Thread aThread = new Thread(new Worker(shareResource, true));
        Thread bThread = new Thread(new Worker(shareResource, false));
        aThread.start();
        bThread.start();
        System.in.read();
    }

    static class Worker implements Runnable {

        private final ShareResource shareResource;

        private final boolean printA;

        public Worker(ShareResource shareResource, boolean printA) {
            this.shareResource = shareResource;
            this.printA = printA;
        }

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    synchronized (shareResource) {
                        // 查看是否到自己线程要打印的时候，如果不是则进行等待
                        while (printA != shareResource.isA) {
                            // wait和notify都得在共享资源上进行，如果直接在thread上this.wait(),那是不存在竞争，会报java.lang.IllegalMonitorStateException异常
                            shareResource.wait();
                        }
                    }

                    String name = printA ? "A" : "B";
                    System.out.println(Thread.currentThread().getName() + ":" + name);

                    synchronized (shareResource) {
                        shareResource.isA = !printA;
                        shareResource.notifyAll();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void test() throws IOException {
        ShareResource shareResource = new ShareResource();
        Thread aThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (!Thread.interrupted()) {
                        synchronized (shareResource) {
                            while (!shareResource.isA) {
                                // wait和notify都得在共享资源上进行，如果直接在thread上this.wait(),那是不存在竞争，会报java.lang.IllegalMonitorStateException异常
                                shareResource.wait();
                            }
                        }

                        System.out.println(Thread.currentThread().getName() + ":A");

                        synchronized (shareResource) {
                            shareResource.isA = false;
                            shareResource.notifyAll();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread bThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (!Thread.interrupted()) {
                        synchronized (shareResource) {
                            while (shareResource.isA) {
                                shareResource.wait();
                            }
                        }

                        System.out.println(Thread.currentThread().getName() + ":B");

                        synchronized (shareResource) {
                            shareResource.isA = true;
                            shareResource.notifyAll();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        aThread.start();
        bThread.start();
        System.in.read();
    }
}
