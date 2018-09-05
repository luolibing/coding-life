package com.tim.dubbo.sample.plugin;

import com.alibaba.dubbo.rpc.cluster.Merger;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by luolibing on 2018/9/6.
 */
public class MyMerger implements Merger<String> {
    @Override
    public String merge(String... items) {
        return Stream.of(items).collect(Collectors.joining("  -  "));
    }
}
