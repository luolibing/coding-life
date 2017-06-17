package cn.tim;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by LuoLiBing on 15/8/8.
 */
public abstract class AbstractExecutor<T> {

    private ExecutorService threadPool = Executors.newFixedThreadPool(8);

    final List<T> batches = new ArrayList<T>();

    abstract T callDatabase(String msg);

    T execute() throws ExecutionException, InterruptedException {
        Callable<T> t = new Callable<T>() {
            public T call() throws Exception {
                synchronized (batches) {
                    T result = callDatabase("callable");
                    batches.add(result);
                    return result;
                }
            }
        };

        Future<T> f = threadPool.submit(t);
        return f.get();
    }

}
