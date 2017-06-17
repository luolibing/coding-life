package cn.tim.disruptor;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WorkHandler;
import com.lmax.disruptor.dsl.Disruptor;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by LuoLiBing on 17/2/28.
 * disruptor是什么
 * 1 一种数据结构, 无竞争的工作流
 * 2 快速消息传递
 * 3 允许真正的平行
 *
 * Disruptor
 * 1 ring buffer需要大于12个节点
 * 2 各自的handlers在分离的线程当中
 *
 * 使用场景
 * 1 可以让我们专注于领域建模
 * 2 能够并行但是单线程
 * 3 简单
 * 4 可靠的排序
 * 5 快速
 *
 * 使用方式
 * 1 单播
 * 2 广播
 * 3 工作流
 */
public class WhatIsDisruptor {

    static class Event {
        private static long counter = 0;
        private long id = counter++;
        private String path;

        public Event() {
        }

        public Event(long id, String path) {
            this.id = id;
            this.path = path;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        @Override
        public String toString() {
            return "Event{" +
                    "id=" + id +
                    ", path='" + path + '\'' +
                    '}';
        }
    }

    /**
     * 逐个单播模式
     * @throws IOException
     */
    @Test
    public void sample1() throws IOException {
        Disruptor<Event> disruptor = new Disruptor<>(Event::new, 1024, Executors.defaultThreadFactory());
        disruptor.handleEventsWithWorkerPool(new LoggerEventHandler(), new StoreEventHandler());
        disruptor.start();

        RingBuffer<Event> ringBuffer = disruptor.getRingBuffer();
        Files.walkFileTree(Paths.get("/share/json"), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                String fileName = file.getFileName().toString();
                if (fileName.endsWith(".json") || fileName.endsWith(".xls")) {
                    ringBuffer.publishEvent((event, seq) -> event.setPath(file.getFileName().toString()));
                }
                return FileVisitResult.CONTINUE;
            }
        });
        disruptor.shutdown();
    }

    class LoggerEventHandler implements WorkHandler<Event> {

        @Override
        public void onEvent(Event event) throws Exception {
            System.out.println(Thread.currentThread().getId() + ": logger info " +  event);
        }
    }

    class StoreEventHandler implements WorkHandler<Event> {

        @Override
        public void onEvent(Event event) throws Exception {
            System.out.println(Thread.currentThread().getId() + ": store database " + event);
        }
    }

    static class Order {
        private static long counter = 0;
        private long id = counter++;
        private String name;
        private int type;

        public Order(int type) {
            this.type = type;
            this.name = name(type);
        }

        public Order() {
        }

        public String name(Integer type) {
            switch (type) {
                case 0: return "一对一";
                case 1: return "公开课";
                case 2: return "外教点评";
                default: return "未知订单";
            }
        }
    }

    /**
     * 消息广播到所有的Handler, 然后不同的Handler可以选择自己关心的消息.
     * @throws InterruptedException
     */
    @Test
    public void sample2() throws InterruptedException {
        Disruptor<Order> disruptor = new Disruptor<>(Order::new, 1024, Executors.defaultThreadFactory());
        disruptor.handleEventsWith(new OneToOneOrderHandler(), new PublicClassHandler(), new CommentCardHandler(), new OtherHandler());
        disruptor.start();

        RingBuffer<Order> ringBuffer = disruptor.getRingBuffer();
        ThreadPoolExecutor exec = new ThreadPoolExecutor(
                4, 4, 1000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(10), new ThreadPoolExecutor.CallerRunsPolicy());
        Random rand = new Random(47);
        CountDownLatch latch = new CountDownLatch(4);
        for(int j = 0; j < 4; j++) {
            exec.execute(() -> {
                for (int i = 0; i < 1024; i++) {
                    ringBuffer.publishEvent((event, num) -> {
                        event.type = rand.nextInt(4);
                        event.name = event.name(event.type);
                    });
                }
                latch.countDown();
            });
        }
        exec.shutdown();
        disruptor.shutdown();
    }

    public static class OneToOneOrderHandler extends BaseOrderHandler {

        @Override
        public void execute(Order event) {
            System.out.println(String.format("处理一对一上课订单[%s]", event.id));
        }

        @Override
        public boolean interested(Order order, long sequence, boolean endOfBatch) {
            return order.type == 0;
        }
    }

    public static class PublicClassHandler extends BaseOrderHandler {


        @Override
        public void execute(Order event) {
            System.out.println(String.format("处理公开课上课订单[%s]", event.id));
        }

        @Override
        public boolean interested(Order order, long sequence, boolean endOfBatch) {
            return order.type == 1;
        }

    }

    public static class CommentCardHandler extends BaseOrderHandler {

        @Override
        public void execute(Order event) {
            System.out.println(String.format("处理外教点评订单[%s]", event.id));
        }

        @Override
        public boolean interested(Order order, long sequence, boolean endOfBatch) {
            return order.type == 2;
        }

    }

    public static class OtherHandler extends BaseOrderHandler {

        @Override
        public void execute(Order event) {
            System.out.println(String.format("处理其他订单[%s]", event.id));
        }

        @Override
        public boolean interested(Order order, long sequence, boolean endOfBatch) {
            return order.type == 3;
        }

    }

    public static abstract class BaseOrderHandler implements EventHandler<Order> {
        @Override
        public void onEvent(Order event, long sequence, boolean endOfBatch) throws Exception {
            if(event != null && interested(event, sequence, endOfBatch)) {
                execute(event);
            }
        }

        public abstract void execute(Order event);

        public abstract boolean interested(Order order, long sequence, boolean endOfBatch);
    }


    /**
     * 工作流的方式
     * @throws InterruptedException
     */
    @Test
    public void workFlow() throws InterruptedException {
        Disruptor<Work> disruptor = new Disruptor<>(Work::new, 1024, Executors.defaultThreadFactory());
        disruptor.handleEventsWith(new FirstProcessor())
                .then(new SecondProcessor())
                .then(new ThirdProcessor()).asSequenceBarrier().alert();
        disruptor.start();

        RingBuffer<Work> ringBuffer = disruptor.getRingBuffer();
        ThreadPoolExecutor exec = new ThreadPoolExecutor(
                4, 4, 500, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(4),
                new ThreadPoolExecutor.AbortPolicy());
        for(int i = 0; i < 4; i++) {
            exec.execute(() -> {
                for(int j = 0; j < 1024; j++) {
                    ringBuffer.publishEvent((e, s) -> {
                        e.step = 0;
                        /**
                         * Publish的流程是
                         * 1 使用sequencer.next()获取到一个序列号, 使用的方式是CAS方式获取
                         * 2 创建一个Event关联对应的sequence, 并且通过闭包将event, sequence传递给调用者
                         * 3 调用者设置event对应的属性, 然后更新sequencer, 一个Event就算是发布了, 可以被Handler处理了.
                         */
                    });
                }
            });
        }
        exec.shutdown();
        disruptor.shutdown();
        while (disruptor.getBufferSize() != 0) {
            TimeUnit.MILLISECONDS.sleep(200);
        }
    }

    public static class Work {
        private static long counter = 0;
        private long id = counter ++;
        private int step = 0;
        private boolean first;
        private boolean second;
        private boolean third;
    }

    public static class FirstProcessor extends BaseProcessor {

        @Override
        public void nextProcess(Work event) {
            event.first = true;
        }
    }

    public static class SecondProcessor extends BaseProcessor {

        @Override
        public void nextProcess(Work event) {
            event.second = true;
        }
    }

    public static class ThirdProcessor extends BaseProcessor {

        @Override
        public void nextProcess(Work work) {
            work.third = true;
        }
    }

    public static abstract class BaseProcessor implements EventHandler<Work> {

        @Override
        public void onEvent(Work event, long sequence, boolean endOfBatch) throws Exception {
            event.step++;
            System.out.println(String.format("Work[%s] 第%s道工序",
                    Long.toString(event.id), Integer.toString(event.step)));
            nextProcess(event);
        }

        public abstract void nextProcess(Work event);
    }
}
