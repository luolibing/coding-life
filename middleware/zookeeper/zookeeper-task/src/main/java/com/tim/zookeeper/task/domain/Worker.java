package com.tim.zookeeper.task.domain;

/**
 * Created by luolibing on 2019/3/19.
 */
public interface Worker<T> {

    /**
     * 接受任务
     * @param task
     */
    void process(Task<T> task);
}
