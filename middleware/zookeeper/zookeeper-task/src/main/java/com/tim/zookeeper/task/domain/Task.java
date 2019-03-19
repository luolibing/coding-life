package com.tim.zookeeper.task.domain;

import lombok.Data;

/**
 * Created by luolibing on 2019/3/19.
 */
@Data
public class Task<T> {

    private String id;

    private Integer priority;

    private T data;
}
