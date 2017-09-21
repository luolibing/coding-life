package cn.tim.java8.source;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * ParallelStream的原理与fork-join息息相关，大致就是对forkJoinTask进行了封装
 * 1 + 10000
 * User: luolibing
 * Date: 2017/9/21 16:50
 */
public class MyForkJoinTask extends RecursiveTask<Long> {

    // threshold每个任务最大一次执行的数量，正常应该按照总数/线程数，一般cpu对应4个线程
    private final static int threshold = ForkJoinPool.getCommonPoolParallelism() << 2;

    private int start;

    private int end;

    public MyForkJoinTask(int start, int end) {
        this.start = start;
        this.end = end;
    }


    @Override
    protected Long compute() {
        long sum;
        boolean canCompute = ((end - start) <= threshold);
        if(canCompute) {
            sum = doExec();
        } else {
            int mid = (end + start) / 2;
            MyForkJoinTask left = new MyForkJoinTask(start, mid);
            MyForkJoinTask right = new MyForkJoinTask(mid + 1, end);

            // 分裂
            left.fork();
            right.fork();

            Long leftResult = left.join();
            Long rightResult = right.join();
            sum = leftResult + rightResult;
        }
        return sum;
    }

    private long doExec() {
        long sum = 0;
        for(int i = start; i <= end; i++) {
            sum += i;
        }
        return sum;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println(threshold);
        ForkJoinTask<Long> result = ForkJoinPool.commonPool()
                .submit(new MyForkJoinTask(0, 1_000_000_00));
        System.out.println("result = " + result.get());
    }
}
