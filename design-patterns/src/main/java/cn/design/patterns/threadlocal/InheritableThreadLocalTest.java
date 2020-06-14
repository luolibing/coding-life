package cn.design.patterns.threadlocal;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.TtlRunnable;
import org.junit.Test;

import java.util.Objects;
import java.util.WeakHashMap;
import java.util.concurrent.*;
import java.util.stream.IntStream;

/**
 * desc: TODO
 *
 * @author Kroos.luo
 * @since 2020-03-28 18:53
 */
public class InheritableThreadLocalTest {

    private static ThreadLocal<String> inheritTenantHolder = new InheritableThreadLocal<>();

    private static ThreadLocal<String> normalTenantHolder = new ThreadLocal<>();

    /**
     * inheritableThreadLocal在创建新的线程的时候，会从主线程传递ThreadLocal信息到子线程
     * 普通的ThreadLocal不会
     * @throws InterruptedException
     */
    @Test
    public void executeWithNewThread() throws InterruptedException {
        normalTenantHolder.set("cloud");
        inheritTenantHolder.set("cloud");
        new Thread(() -> {
            System.out.println("normal tenant = " + normalTenantHolder.get());
        }).start();

        new Thread(() -> {
            System.out.println("inherit tenant = " + inheritTenantHolder.get());
        }).start();
        TimeUnit.SECONDS.sleep(5);
    }


    /**
     * 因为线程池，是初始化一部分线程，然后会从主线程创建出一部分线程，这个时候使用inheritThreadLocal，会继承ThreadLocal上下文信息
     * 当重复使用线程时，ThreadLocal不会再次继承，而是使用已有的线程信息，因为继承是发生在初始化线程的时候
     * parallel也是一样，使用的是线程池的方式来实现的，所以也会有这个问题
     * @throws InterruptedException
     */
    @Test
    public void executeWithThreadPool() throws InterruptedException {
        ExecutorService pool1 = new ThreadPoolExecutor(2, 2, 10, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(100), new ThreadPoolExecutor.CallerRunsPolicy());
        ExecutorService pool2 = new ThreadPoolExecutor(2, 2, 10, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(100), new ThreadPoolExecutor.CallerRunsPolicy());
        inheritTenantHolder.set("cloud");
        normalTenantHolder.set("cloud");
        for(int i = 0; i < 100; i++) {
            pool1.execute(() -> {
                System.out.println(Thread.currentThread().getId() + " normal pool " + normalTenantHolder.get());
            });

            inheritTenantHolder.set("cloud-inherit");
            pool2.execute(() -> {
                System.out.println(Thread.currentThread().getId() + " inherit pool " + inheritTenantHolder.get());
            });
        }

        TimeUnit.SECONDS.sleep(10);
        for(int i = 0; i < 1000; i++) {
            inheritTenantHolder.set("cloud-inherit-after");
            pool2.execute(() -> {
                System.out.println(Thread.currentThread().getId() + " inherit pool " + inheritTenantHolder.get());
            });
        }
        TimeUnit.SECONDS.sleep(10);

        inheritTenantHolder.set("cloud-inherit-parallel");
        IntStream.range(0, 1000)
                .parallel()
                .boxed()
                .forEach((i) -> {
                    System.out.println(Thread.currentThread().getId() + " execute parallel " + inheritTenantHolder.get());
                });

        TimeUnit.SECONDS.sleep(5);
        inheritTenantHolder.set("cloud-inherit-parallel-after");
        IntStream.range(0, 1000)
                .parallel()
                .boxed()
                .forEach((i) -> {
                    System.out.println(Thread.currentThread().getId() + " execute after parallel " + inheritTenantHolder.get());
                });
    }

    @Test
    public void executeHandle1() throws InterruptedException {
        inheritTenantHolder.set("cloud");
        ExecutorService pool1 = new ThreadPoolExecutor(2, 2, 10, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(100), new ThreadPoolExecutor.CallerRunsPolicy());
        String tenant = inheritTenantHolder.get();
        String finalTenant = tenant;
        for(int i = 0; i < 100; i++) {
            pool1.execute(() -> {
                try {
                    inheritTenantHolder.set(finalTenant);
                    System.out.println(Thread.currentThread().getId() + " execute handle " + inheritTenantHolder.get());
                } finally {
                    inheritTenantHolder.remove();
                }
            });
        }

        TimeUnit.SECONDS.sleep(5);
        inheritTenantHolder.set("cloud-after");
        tenant = inheritTenantHolder.get();
        String finalTenant1 = tenant;
        for(int i = 0; i < 100; i++) {
            pool1.execute(() -> {
                try {
                    inheritTenantHolder.set(finalTenant1);
                    System.out.println(Thread.currentThread().getId() + " execute after handle " + inheritTenantHolder.get());
                } finally {
                    inheritTenantHolder.remove();
                }
            });
        }
        TimeUnit.SECONDS.sleep(100);
    }

    @Test
    public void executeHandle2() throws InterruptedException {
        inheritTenantHolder.set("cloud");
        ExecutorService pool1 = new ThreadPoolExecutor(2, 2, 10, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(100), new ThreadPoolExecutor.CallerRunsPolicy()) {
            @Override
            public void execute(Runnable command) {
                String tenant = inheritTenantHolder.get();
                super.execute(new MyRunnable(command, tenant));
            }
        };

        for(int i = 0; i < 100; i++) {
            pool1.execute(() -> {
                System.out.println(Thread.currentThread().getId() + " execute after handle " + inheritTenantHolder.get());
            });
        }
        TimeUnit.SECONDS.sleep(10);

        inheritTenantHolder.set("cloud-after");
        for(int i = 0; i < 100; i++) {
            pool1.execute(() -> {
                System.out.println(Thread.currentThread().getId() + " execute after handle " + inheritTenantHolder.get());
            });
        }
        TimeUnit.SECONDS.sleep(10);
    }

    public static class MyRunnable implements Runnable {

        private Runnable runnable;

        private String tenant;

        public MyRunnable(Runnable runnable, String tenant) {
            this.runnable = runnable;
            this.tenant = tenant;
        }

        @Override
        public void run() {
            try {
                inheritTenantHolder.set(tenant);
                runnable.run();
            } finally {
                inheritTenantHolder.remove();
            }
        }
    }

    private static TransmittableThreadLocal<String> ttlThreadLocal = new TransmittableThreadLocal<>();

    @Test
    public void ttl1() throws InterruptedException {
        ttlThreadLocal.set("ttl1");
        new Thread(() -> {
            System.out.println("ttl1 thread execute " + ttlThreadLocal.get());
        }).start();
        TimeUnit.SECONDS.sleep(2);
        System.out.println(ttlThreadLocal.get());

        ExecutorService pool1 = new ThreadPoolExecutor(2, 2, 10, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(100), new ThreadPoolExecutor.CallerRunsPolicy());
        for(int i = 0; i < 1000; i++) {
            ttlThreadLocal.set("tenant" + i);
            int finalI = i;
            TtlRunnable command = TtlRunnable.get(() -> {
                String current = ttlThreadLocal.get();
                if (!Objects.equals(current, "tenant" + finalI)) {
                    System.out.println("error ttl, " + ttlThreadLocal.get());
                }
            });
            pool1.execute(command);
        }
        TimeUnit.SECONDS.sleep(100);
    }


    @Test
    public void weakMap() {
    }
}
