package cn.tim.zookeeper.highlevel.lock;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.Objects;

/**
 * User: luolibing
 * Date: 2017/6/7 15:24
 */
public class RevocableLock implements Watcher {

    private ZooKeeper zk;

    private String lockName;

    private volatile boolean isDelete = false;

    public RevocableLock(String hostPort, String lockName) {
        try {
            this.lockName = lockName;
            zk = new ZooKeeper(hostPort, 20000, this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void tryLock(String client) {
        try {
            Stat stat = zk.exists(lockName, false);
            // 已经被锁，尝试
            if(stat != null) {
                byte[] data = zk.getData(lockName, false, null);
                String content = new String(data);
                // 锁持有者重入锁
                if(("lock-" + client).equals(content)) {
                    return;
                }

                tryRevoceLock();
            }

            // 创建lock
            zk.create(lockName, ("lock-" + client).getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

            // 提供回调
            zk.getData(lockName, true, null);

        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void tryRevoceLock() {
        try {
            zk.setData(lockName, "unlock".getBytes(), 0);

            // TODO 这个地方有可能还没来得及wait()，notify就已经发送过来了
            while (true) {
                Stat stat = zk.exists(lockName, true);
                if (stat != null) {
                    synchronized (this) {
                        if(!isDelete) {
                            wait();
                        }
                    }
                }
            }
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * watcher回调
     * @param event
     */
    @Override
    public void process(WatchedEvent event) {
        System.out.println(event);
        Event.EventType type = event.getType();
        if(type == Event.EventType.NodeDataChanged &&
                Objects.equals(event.getPath(), lockName)) {
            try {
                synchronized (this) {
                    zk.delete(lockName, 1);
                    notifyAll();
                }
            } catch (InterruptedException | KeeperException e) {
                e.printStackTrace();
            }
        } else if(type == Event.EventType.NodeDeleted) {
            synchronized (this) {
                isDelete = true;
                notifyAll();
            }
        }
    }
}
