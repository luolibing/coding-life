package com.tim.zookeeper.task.domain;

/**
 * Created by luolibing on 2019/3/19.
 */
public interface TaskPublisher {

    <T> void publish(Task<T> task);
}
