package com.tim.zookeeper.glock;

import lombok.SneakyThrows;
import org.apache.zookeeper.*;

/**
 * zk全局锁的实现与redis的区别是，zk锁可以在对应锁释放的时候通过watcher来通知其他需要获取锁的进程，
 * redis在以前的版本是没有这个的，在2.8之后也添加了这个功能，理论上也是你可以实现这个功能。
 * https://redis.io/topics/notifications
 *
 * 实现思路：
 * 1 首先尝试获取锁，即创建一个指定路径的临时节点，为什么是临时节点，如果因为程序崩溃会话结束，临时节点会自动删除。
 * 2 如果获取失败，程序setWatch到这个节点，同时程序进入到wait状态，程序就此阻塞住
 * 3 当这个节点被删除的时候，没有获取锁wait状态，会被notify唤醒，这个时候再次尝试获取锁
 * Created by luolibing on 2019/3/13.
 */
public class ZkGlock implements Glock, Watcher {

    private String lockKey;

    private ZooKeeper zk;

    private boolean holdLock = false;

    public ZkGlock(String lockKey, ZooKeeper zk) {
        this.lockKey = lockKey;
        this.zk = zk;
    }

    /**
     * 尝试获取锁
     * @return
     */
    @Override
    @SneakyThrows
    public void lock() {
        while (true) {
            try {
                zk.create(lockKey, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
                this.holdLock = true;
                return;
            } catch (KeeperException | InterruptedException e) {
                zk.exists(lockKey, this);
                synchronized (this) {
                    wait();
                }
            }
        }
    }

    /**
     * 释放锁
     */
    @SneakyThrows
    @Override
    public void unlock() {
        if(holdLock) {
            holdLock = false;
            zk.delete(lockKey, 0);
        }
    }

    @Override
    public void process(WatchedEvent event) {
        Event.EventType type = event.getType();
        String path = event.getPath();
        if(lockKey.equals(path) && type == Event.EventType.NodeDeleted && !holdLock) {
            synchronized (this) {
                notifyAll();
            }
        }
    }
}
