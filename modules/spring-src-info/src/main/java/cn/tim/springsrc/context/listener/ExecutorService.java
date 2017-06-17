package cn.tim.springsrc.context.listener;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 手工指定executor
 * Created by luolibing on 2017/6/7.
 */
public class ExecutorService implements Executor {

    private java.util.concurrent.ExecutorService exec = Executors.newCachedThreadPool();

    @Override
    public void execute(Runnable command) {
        System.out.println("execute command");
        exec.execute(command);
    }
}
