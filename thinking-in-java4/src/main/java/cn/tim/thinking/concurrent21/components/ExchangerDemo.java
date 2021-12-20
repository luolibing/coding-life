package cn.tim.thinking.concurrent21.components;

import java.util.List;
import java.util.concurrent.*;

/**
 * Created by LuoLiBing on 16/11/12.
 * Exchanger是两个任务之间交换对象的栅栏,当这些任务进入栅栏时,他们各自拥有一个对象,当他们离开时,他们都拥有之前对方持有的对象
 */
public class ExchangerDemo {

    private final static int SIZE = 20;

    /**
     * 生产者
     * @param <T>
     */
    static class ExchangerProducer<T> implements Runnable {

        // 当前持有的列表
        private List<T> holded;

        // 生成器
        private Generator<T> generator;

        // 交换栅栏
        private Exchanger<List<T>> exchanger;

        public ExchangerProducer(Generator<T> generator, Exchanger<List<T>> exchanger, List<T> holded) {
            this.generator = generator;
            this.exchanger = exchanger;
            this.holded = holded;
        }


        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    for(int i = 0; i < SIZE; i++) {
                        holded.add(generator.next());
                    }
                    // 生成完之后放入到交换区,然后接收从消费者传递过来的集合
                    holded = exchanger.exchange(holded);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 交换消费者
     * @param <T>
     */
    static class ExchangerConsumer<T> implements Runnable {

        private List<T> holder;

        private volatile T t;

        private Exchanger<List<T>> exchanger;

        public ExchangerConsumer(Exchanger<List<T>> exchanger, List<T> holder) {
            this.holder = holder;
            this.exchanger = exchanger;
        }

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    for (int i = 0; i < holder.size(); i++) {
                        t = holder.get(i);
                        System.out.println(t);
                    }
                    holder.clear();
                    System.out.println("Final T = " + t);

                    // 消费完之后,将空的对象放入交换器,然后等待从交换器获取新一批的对象
                    holder = exchanger.exchange(holder);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class Generator<T> {

        private Class<T> clazz;

        public Generator(Class<T> clazz) {
            this.clazz = clazz;
        }

        T next() {
            try {
                return clazz.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();
        List<SemaphoreDemo.Fat> producerList = new CopyOnWriteArrayList<>(),
                                consumerList = new CopyOnWriteArrayList<>();
        Exchanger<List<SemaphoreDemo.Fat>> exchanger = new Exchanger<>();
        Generator<SemaphoreDemo.Fat> generator = new Generator<>(SemaphoreDemo.Fat.class);
        exec.execute(new ExchangerProducer<>(generator, exchanger, producerList));
        exec.execute(new ExchangerConsumer<>(exchanger, consumerList));

        TimeUnit.SECONDS.sleep(Long.MAX_VALUE);
        exec.shutdownNow();
    }
}
