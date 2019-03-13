package com.tim.zookeeper.glock;

import lombok.SneakyThrows;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.TimeUnit;

/**
 * Created by luolibing on 2019/3/13.
 */
public class Worker {

    private String name;

    private ZooKeeper zk;

    private static int NO = 0;

    private int num = NO ++;

    public Worker(String name, ZooKeeper zk) {
        this.name = name;
        this.zk = zk;
    }

    @SneakyThrows
    public void process() {
        Glock zkGlock = new ZkGlock("/lock", zk);
        zkGlock.lock();
        System.out.println("worker-" + num + " work " + name);
        TimeUnit.SECONDS.sleep(5);
        zkGlock.unlock();
    }
}
