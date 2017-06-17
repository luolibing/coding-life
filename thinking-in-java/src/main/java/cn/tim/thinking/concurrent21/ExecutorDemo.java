package cn.tim.thinking.concurrent21;

import cn.tim.thinking.concurrent21.thread.RunnableDemo;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by LuoLiBing on 16/10/10.
 * 线程池的等待队列界限为Integer.max值,2的31次方-1
 *
 * 线程池实际操作方式是,在一个Thread上执行多个Runnable,因为Thread在调用完run方法之后,就不能再次启动,所以在其run方法中执行多个Runnable达到复用的目的
 */
public class ExecutorDemo {

    /**
     * 创建一个按需创建新线程的线程池,线程可以重复利用,这样可以提升性能
     * execute将重复利用上一个可用的线程(如果存在).如果没有可用的线程
     * 则创建一个新的线程并且加入到线程池, 线程超过60秒没有使用,会自动终止,并且从线程池中删除
     *
     * execute执行步骤
     * 1 如果运行线程数量少于corePoolSize,尝试启动一个新的线程,addWorker方法会自动验证runState和workerCount状态
     * 2 如果一个任务能够成功入队,依然要两次验证,是否需要添加一个线程(有可能有线程因为超长时间未使用被杀掉)或者线程池已经shutdown.
     * 3 如果不能入队,则会尝试创建一个线程,如果失败,有可能是pool shutdown或者因为饱和拒绝了这个任务
     int c = ctl.get();
     if (workerCountOf(c) < corePoolSize) {
         if (addWorker(command, true))
            return;
         c = ctl.get();
     }
     if (isRunning(c) && workQueue.offer(command)) {
         int recheck = ctl.get();
         if (! isRunning(recheck) && remove(command))
            reject(command);
         else if (workerCountOf(recheck) == 0)
            addWorker(null, false);
     }
     else if (!addWorker(command, false))
        reject(command);
     *
     * addWorker
     *  新增worker的时候需要,判断当前的pool状态和线程池的最大限制.因此线程池的大小需要调整
     *  当线程池停止或者当线程工场创建线程失败的时候返回false,当创建线程失败返回null,或者因为异常(OutOfMemoryError)的时候返回
     *
     * execute和submit的区别
     *  execute和submit都能用线程池来驱动任务
     *  execute提交任务无返回值,submit有返回值,返回的future能够根据需要阻塞直到返回
     *
     * shutdown和terminate
     * 关闭与停止,shutdown会标记线程池状态为关闭状态,这个时候不能再提交任务,并且会触发tryTerminate(),尝试中断,给每一个任务标记中断状态(等待任务找到合适的时机进行中断)
     * 当调用shutdown()之后,调用isShutdown()会返回true, 当完成中断之后isTerminate()会返回true,所以当调用shutdown之后,判断是否还有线程在执行可以通过isTerminate来进行判断
     * shutdown会等待所有已经执行的任务执行完,不继续接收要执行的任务. shutdownNow()会通过中断的方式尝试结束正在执行的任务,未能响应中断的任务可能永远不会终止
     * @throws InterruptedException
     *
     * CachedThreadPool使用的是同步队列SynchronousQueue队列,这个线程池根据需要创建新的线程,如果有空闲线程则会重复使用,线程空闲60秒自动回收, 在创建线程池时就定义了最大空闲时间
     */
    @Test
    public void cachedThreadPool() throws InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();
        for(int i = 0; i < 5; i++) {
            exec.execute(new RunnableDemo.LiftOff());
        }

        List<Future<?>> futures = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            futures.add(exec.submit(new RunnableDemo.LiftOff()));
        }
        System.out.println(futures);
        exec.shutdown();
        while (!exec.isTerminated()) {
            System.out.println("waiting shutdown");
            TimeUnit.MILLISECONDS.sleep(50);
        }
        System.out.println("finish");
    }

    /**
     * SynchronousQueue同步队列是一个没有数据缓冲的BlockingQueue,生产者线程对其的插入操作put必须等待消费者的移除操作take,反之也一样
     * 内部并没有数据缓存空间,不能调用peek方法来看队列中是否有数据元素,只有当使用take时取走元素时才可能存在,peek()方法直接return null
     * 数据是在配对的生产者和消费者线程之间直接传递的,并不会将数据缓冲到队列中.可以这样理解:生产者和消费者互相等待对方,握手,然后一起离开
     */
    @Test
    public void synchronousQueue() throws InterruptedException {
        SynchronousQueue<String> queue = new SynchronousQueue<>();
        new Thread(() -> {
            try {
                queue.put("aaaa");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                System.out.println("take=" + queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * 固定大小的线程池, 大小不能为0, 且线程会一直保存在线程池当中, 当线程池中线程都处于执行状态时,会将任务加入到LinkedBlockingQueue队列中
     *
     * FixedThreadPool使用的是LinkedBlockingQueue队列
     * @throws InterruptedException
     */
    @Test
    public void fixedThreadPool() throws InterruptedException {
        ExecutorService exec = Executors.newFixedThreadPool(2);
        for(int i = 0; i < 10; i++) {
            exec.execute(new RunnableDemo.LiftOff());
        }
        exec.shutdown();
        while (!exec.isTerminated()) {
            System.out.println("waiting shutdown");
            TimeUnit.MILLISECONDS.sleep(50);
        }
    }

    /**
     * linked queue链表队列比数组队列拥有更高的吞吐量(因为插入操作因为链表更方便一些),但是在并发环境中性能不可预测
     * 有容量限制的构造函数,如果不指定容量大小,则默认为Integer的最大值
     * 节点动态的创建
     */
    @Test
    public void linkedBlockingQueue() {
        BlockingQueue<String> queue = new LinkedBlockingQueue<>(2);
        queue.offer("aaa");
        queue.offer("bbb");
        System.out.println(queue.offer("ccc"));
    }

    /**
     * singleThreadPool相当于大小为1的FixedThreadPool线程池,串行化执行所有提交的任务
     * 这对于确保任何时刻在任何线程中都只有唯一的任务在运行,可以很好的对共享资源的访问控制,实现序列化.
     */
    @Test
    public void singleThreadPool() {
        ExecutorService exec = Executors.newSingleThreadExecutor();
        for(int i = 0; i < 5; i ++) {
            exec.execute(() -> System.out.println(Thread.currentThread().getName()));
        }
        exec.shutdown();
    }

    /**
     * 创建窃取工作线程池,采用的是ForkJoinPool的工作方式,并行执行
     * @throws InterruptedException
     */
    @Test
    public void workStealingPool() throws InterruptedException {
        ExecutorService exec = Executors.newWorkStealingPool(Runtime.getRuntime().availableProcessors());
        for(int i = 0; i < 10000000; i++) {
            exec.execute(new RunnableDemo.LiftOff());
        }
        exec.shutdown();
        if(!exec.isTerminated()) {
            Thread.sleep(50);
        }
        System.out.println("finish");
    }

    /**
     * 单独线程调度一个延时任务,或者周期性执行的任务,如果一个单独线程因为执行失败中止,另外一个新的线程将继续执行下一个任务. 任务保证能够顺序执行,任务时刻保证只有一个线程处于活跃状态.
     *
     * schedule()               delay后定点执行一个任务
     * scheduleAtFixedRate()    初始延迟(initial delay)之后执行一次,然后再等待一个周期(period)之后再定点执行....,如果一个任务执行过程中发生了异常,它的下一个任务将被抑制;任务只能通过取消或者中止调度器才能中止任务
     *                          如果一个任务的执行时间超过了它的周期,下一个任务可能稍后执行(事实证明会马上执行,但是何时执行不一定),但是肯定不会并行执行.
     * scheduleWithFixedRate()  与scheduleAtFixedRate()类似,不管上一个任务什么时候执行完,都需要等上一个任务完成之后,过固定的时间周期再执行下一个任务.
     * @throws InterruptedException
     */
    @Test
    public void singleThreadScheduledExecutor() throws InterruptedException {
        ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
        // 定点执行一次
        exec.schedule(() -> {
            String str = "aaa";
            System.out.println("second ThreadId=" + Thread.currentThread().getId());
            System.out.println(str.length());
        }, 5, TimeUnit.SECONDS);

        // 首次延迟之后,后面周期性定点执行(上一个任务开头)有3个参数, initialDelay, period, TimeUnit
        exec.scheduleAtFixedRate( () -> {
                    System.out.println("before FixedRate task!!!");
                    try {
                        TimeUnit.SECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("execute FixedRate task!!!");
                }
                , 1000, 3000, TimeUnit.MILLISECONDS);

        // 与scheduleAtFixedRate类似,不定点,与上一个任务完成之后固定的周期
        exec.scheduleWithFixedDelay(() -> {
                    System.out.println("before FixedRate task!!!");
                    try {
                        TimeUnit.SECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("execute FixedRate task!!!");
        }, 1000, 3000, TimeUnit.MILLISECONDS);
        exec.shutdown();
        while (!exec.isTerminated()) {
            Thread.sleep(100);
        }
    }

    /**
     * 延迟定点执行多个任务
     * @throws InterruptedException
     */
    @Test
    public void scheduledThreadPool() throws InterruptedException {
        ScheduledExecutorService exec = Executors.newScheduledThreadPool(2);
        // 首次延迟之后,后面周期性定点执行(上一个任务开头)有3个参数, initialDelay, period, TimeUnit
        exec.schedule(() -> System.out.println("second ThreadId=" + Thread.currentThread().getId()), 5, TimeUnit.SECONDS);
        exec.shutdown();
        while (!exec.isTerminated()) {
            Thread.sleep(100);
        }
    }

    /**
     * 不可配置调度器, 因为使用了一个包装类,不能使用强转
     */
    @Test
    public void unconfigurableExecutorService() {
        ExecutorService exec = Executors.unconfigurableExecutorService(Executors.newScheduledThreadPool(10));
        // 强转错误
//        ((ScheduledExecutorService) exec).submit(() -> {
//            System.out.println("cast finish!!");
//        });
    }


    /**
     * defaultThreadFactory生成默认的线程工场, privilegedThreadFactory会生成与当前线程相同的访问控制AccessControlContext和类加载器ContextClassLoader
     * @throws InterruptedException
     */
    @Test
    public void defaultThreadFactory() throws InterruptedException {
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        Thread th = threadFactory.newThread(() -> System.out.println("aaaaa"));
        th.start();

        ThreadFactory tf = Executors.privilegedThreadFactory();
        Thread thread = tf.newThread(() -> System.out.println("bbb"));
        thread.start();
    }

    /**
     * 有结果返回的任务Callable
     *
     * Callable有返回值,且能抛出异常
     *
     * @throws Exception
     */
    @Test
    public void callable() throws Exception {
        // 将10传递给Callable
        Callable<Object> callable = Executors.callable(() -> System.out.println("callable"), 10);
        Object call = callable.call();
        System.out.println(call);

        Callable<Object> c = Executors.callable(() -> System.out.println("privilegedAction"));
        System.out.println(c.call());
    }


    class TaskWithResult implements Callable<String> {

        private int id;

        public TaskWithResult(int id) {
            this.id = id;
        }

        @Override
        public String call() throws Exception {
            Thread.sleep(new Random().nextInt(10000));
            return "result of TaskWithResult " + id;
        }
    }

    /**
     * 带返回值任务模式, Callable<Object>   Future<String>结果  future = executor.submit(new Callable())
     */
    @Test
    public void callableDemo() {
        ThreadPoolExecutor exec = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        List<Future<String>> results = new ArrayList<>();
        for(int i = 0; i < 100; i++) {
            results.add(exec.submit(new TaskWithResult(i)));
        }

        // 可以打印任务执行状态,执行百分比,向下强转获取总次数完成次数等
        new Thread(() -> {
            try {
                String message = null;
                exec.shutdown();
                while (!exec.isTerminated()) {
                    exec.awaitTermination(10, TimeUnit.MILLISECONDS);
                    int progress = Math.round((exec.getCompletedTaskCount() * 100) / exec.getTaskCount());
                    String msg = progress + "% has done," + exec.getCompletedTaskCount() + "has completed";
                    if(!Objects.equals(message, msg)) {
                        System.out.println(message = msg);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        // 获取结果, get方法可以阻塞住结果
        for(Future<String> f : results) {
            try {
                System.out.println(f.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        // 带超时的get()方法 Future.get(100, TimeUnit.MILLISECOND));
        // isDone()判断是否完成
    }

    private Random rand = new Random(47);

    // 将结果都放入到CompletionService队列中，然后可以通过take方式获取队列里面的结果， 与用list收集future相比， 这个可以快速的将已经完成的任务返回，这样就能快速的进行接下来的处理了
    @Test
    public void completionService() {
        ExecutorService exec = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        CompletionService<Integer> executor = new ExecutorCompletionService<>(exec);
        for(int i = 0; i < 1000; i++) {
            executor.submit(() -> {
                TimeUnit.MILLISECONDS.sleep(rand.nextInt(2000));
                return rand.nextInt(100);
            });
        }
        exec.shutdown();

        try {
            for (int i = 0; i < 1000; i++) {
                Future<Integer> future = executor.take();
                Integer result = future.get();
                System.out.println("result = " + result);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch(ExecutionException e){
            e.printStackTrace();
        }
    }

    /**
     * 一批任务一起执行， 然后统一返回
     * @throws InterruptedException
     * @throws ExecutionException
     */
    @Test
    public void invokeAll() throws InterruptedException, ExecutionException {
        ExecutorService exec = Executors.newCachedThreadPool();
        List<Callable<Integer>> tasks = new ArrayList<>(100);
        for(int i = 0; i <100; i++) {
            tasks.add(() -> {
                TimeUnit.MILLISECONDS.sleep(rand.nextInt(2000));
                return rand.nextInt(100);
            });
        }
        List<Future<Integer>> futures = exec.invokeAll(tasks, 10, TimeUnit.SECONDS);
        for(Future<Integer> future : futures) {
            System.out.println("future = " + future.get());
        }
    }
}
