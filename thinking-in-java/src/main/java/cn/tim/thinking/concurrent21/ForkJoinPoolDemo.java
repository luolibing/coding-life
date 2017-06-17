package cn.tim.thinking.concurrent21;

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * Created by LuoLiBing on 16/10/11.
 * http://ifeve.com/talk-concurrency-forkjoin/
 *
 * forkJoin是一个大任务分割成若干小任务,最终汇总每个小任务结果得到大任务结果的框架
 * work-stealing工作窃取算法是指从其他队列窃取任务来执行.
 * 一个比较大的任务,我们会把大任务分割成若干互不依赖的子任务,为了减少线程间竞争,于是把这些子任务分别放到不同的队列里,并为每个队列创建一个单独的线程来执行对应的任务.
 * 当有一个队列任务较少时,与其干等着不如去帮其他线程队列干活,于是它就去其他队列里窃取一个任务来执行,为了减少竞争,从另一个队列的队尾进行获取任务
 * 工作窃取算法充分利用了处理器进行并行计算,减少了线程间的竞争
 *
 * 对应到ForkJoin步骤
 * 1 分割任务,把大任务fork,直到足够小达到THRESHOLD某个阙值,才不再分割直接执行
 * 2 执行任务并合并结果.每个队列执行自己的任务,结果统一放到一个队列里,所以处理的结果是无序的,启动一个线程从队列里拿数据,然后合并这些数据
 *
 * ForkJoin的两个类
 *  ForkJoinTask 任务类,包括fork()和join操作机制,通常我们不直接继承forkJoinTask,只需要继承它的子类
 *      RecursiveAction: 无返回结果子任务
 *      RecursiveTask:   有返回结果子任务
 *
 *  ForkJoinPool ForkJoinTask需要通过ForkJoinPool来执行,创建多少个队列,任务进入哪个队列,窃取任务等工作都由ForkJoinPool来完成
 *
 */
public class ForkJoinPoolDemo extends RecursiveTask<Integer> {

    private static final int THRESHOLD = 1000;

    private int start;

    private int end;

    public ForkJoinPoolDemo(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum = 0;
        boolean canCompute = (end - start) <= THRESHOLD;
        if(canCompute) {
            for(int i = start; i <= end; i++) {
                sum += i;
            }
        } else {
            int mid = (start + end) / 2;
            ForkJoinPoolDemo l = new ForkJoinPoolDemo(start, mid);
            ForkJoinPoolDemo r = new ForkJoinPoolDemo(mid + 1, end);
            l.fork();
            r.fork();

            int lr = l.join();
            int rr = r.join();
            sum = lr + rr;
        }
        return sum;
    }

    public static void main1(String[] args) {
        long start = System.currentTimeMillis();
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinPoolDemo task = new ForkJoinPoolDemo(0, 10000000);
        ForkJoinTask<Integer> result = pool.submit(task);
        try {
            System.out.println(result.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        if(task.isCompletedAbnormally()) {
            System.out.println(task.getException());
        }
        System.out.println("time=" + (System.currentTimeMillis() - start));
    }

    @org.junit.Test
    public void main2() {
        long start = System.currentTimeMillis();
        int sum = 0;
        for(int i = 0; i < 10000000; i++) {
            sum += i;
        }
        System.out.println(sum);
        System.out.println("time=" + (System.currentTimeMillis() - start));
    }

    public static void spliterator() throws InterruptedException {
        ArrayList<Object> list = new ArrayList<>();
        List<Spliterator<Object>> spliterators = new ArrayList<>();
        for(int i = 0; i < 100; i ++) {
            list.add(i);
        }
        Spliterator<Object> spliterator1 = list.spliterator();
        System.out.println(" " +  spliterator1.characteristics());
        System.out.println(" " + spliterator1.estimateSize());

        System.out.println("after trySplit()");
        Spliterator<Object> spliterator2 = spliterator1.trySplit();
        System.out.println(" " + spliterator1.characteristics());
        System.out.println(" " + spliterator1.estimateSize());
        Spliterator<Object> spliterator1_1 = spliterator1.trySplit();
        spliterators.add(spliterator1);
        spliterators.add(spliterator1_1);
        Spliterator<Object> spliterator2_2 = spliterator2.trySplit();
        spliterators.add(spliterator2_2);
        spliterators.add(spliterator2);

        int i = 0;
        for(Spliterator<Object> spliterator : spliterators) {
            System.out.println("第" + (++i)+ "部分");
            while (spliterator.tryAdvance(System.out::println)) ;
            System.out.println();
        }
        Thread.sleep(10000);
    }

    public static void main(String[] args) throws InterruptedException {
        spliterator();
    }
}
