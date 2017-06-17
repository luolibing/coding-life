package cn.tim.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by LuoLiBing on 15/8/13.
 */
public class ThreadPoolExecutorTest {

    void run() {
        List<Future> futures = new ArrayList();
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(
                10, 10, 1, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>());
        for(int i=0; i<100; i++) {
            futures.add(poolExecutor.submit(new Job(i)));
        }
        poolExecutor.shutdown();
        try {
            String mssage = null;
            while (!poolExecutor.isTerminated()) {
                poolExecutor.awaitTermination(100, TimeUnit.MICROSECONDS);
                int progress = Math.round(poolExecutor.getCompletedTaskCount()*100 / poolExecutor.getTaskCount());
                String msg = progress + "% done(" + poolExecutor.getCompletedTaskCount() + " task have finished!";
                if(msg.equalsIgnoreCase(mssage)) {
                    System.out.println(mssage = msg);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            for (Future f : futures) {
                System.out.println(f.get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        new ThreadPoolExecutorTest().run();
    }

    class Job implements Callable<String> {

        int start;

        public Job(int i) {
            start = i;
        }

        public String call() throws Exception {
            int sum = 0;
            for(int i=start; i<1000000000; i++) {
                sum += i;
            }
            return this.toString() + ",sum=" + sum + "完成";
        }
    }
}
