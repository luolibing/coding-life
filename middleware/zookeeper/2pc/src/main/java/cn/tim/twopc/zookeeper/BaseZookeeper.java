package cn.tim.twopc.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

/**
 * User: luolibing
 * Date: 2017/6/8 11:26
 */
public abstract class BaseZookeeper implements Watcher {

    protected ZooKeeper zooKeeper;

    public BaseZookeeper(String hostPort) {
        try {
            this.zooKeeper = new ZooKeeper(hostPort, 50000, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract void process(WatchedEvent event);
}
