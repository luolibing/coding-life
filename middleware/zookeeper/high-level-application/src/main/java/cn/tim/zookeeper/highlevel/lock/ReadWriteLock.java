package cn.tim.zookeeper.highlevel.lock;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;

/**
 * TODO 写得有点乱，有空重构一下
 * 全局读写锁
 *
 * 可扩展全局读写锁：在原有锁的基础上，在create完之后，通过调用getData()给节点添加一个watch，当内容发生变化时，进行回调。
 * 要撤销锁的线程，可以通过设置对应锁的内容，例如设置为"unlock"，来与锁的持有者进行交互，当锁持有者收到通知之后，可以对锁进行删除撤销。
 * User: luolibing
 * Date: 2017/6/6 20:09
 */
public class ReadWriteLock implements Watcher {

    private ZooKeeper zk;

    private String root;

    private String readLockName;

    private String writeLockName;

    public ReadWriteLock(String hostPort, String root) {
        try {
            this.root = root;
            this.readLockName = root + "/read-";
            this.writeLockName = root + "/write-";
            zk = new ZooKeeper(hostPort, 20000, this);

            // 创建出root节点
            Stat stat = zk.exists(root, false);
            if(stat == null) {
                zk.create(root, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }

        } catch (IOException | InterruptedException | KeeperException e) {
            e.printStackTrace();
        }
    }

    public void tryWriteLock() {
        try {
            // 创建一个写锁
            String wlkName = zk.create(writeLockName, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);

            // 可撤销锁，在create之后通过getData监控节点数据内容的变化，其他线程想要撤销当前锁，可以通过修改当前锁的内容来告知锁的持有者
            int wlkNum = Integer.parseInt(wlkName.substring(wlkName.indexOf("-") + 1));

            boolean flag = true;
            while (flag) {
                flag = false;
                List<String> children = zk.getChildren(root, false);
                if (!CollectionUtils.isEmpty(children)) {
                    String maxLkNode = null;
                    int maxLkNum = -1;
                    for (String child : children) {
                        int num = Integer.parseInt(child.substring(child.indexOf("-")+1));
                        // 已经被锁
                        if (wlkNum != num && num > maxLkNum) {
                            maxLkNum = num;
                            maxLkNode = child;
                        }
                    }

                    if(maxLkNum >= 0) {
                        // 等待上一个锁解锁
                        Stat stat = zk.exists(root + "/" + maxLkNode, true);
                        if (stat != null) {
                            synchronized (this) {
                                wait();
                            }
                        } else {
                            return;
                        }
                    }
                }
            }
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void tryReadLock() {
        try {
            // 在root下创建单调递增的节点
            String rkName = zk.create(readLockName, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            int rkNum = Integer.parseInt(rkName.substring(rkName.indexOf("-")+1));

            boolean flag = true;
            while (flag) {
                flag = false;
                List<String> children = zk.getChildren(root, false);
                if (!CollectionUtils.isEmpty(children)) {
                    int maxWriteNum = -1;
                    String maxWriteNode = null;
                    for (String child : children) {
                        // 有写锁
                        if (child.startsWith("write")) {
                            int writeNum = Integer.parseInt(child.substring(child.indexOf("-") +1));
                            if(writeNum > maxWriteNum) {
                                maxWriteNum = writeNum;
                                maxWriteNode = child;
                            }
                        }

                        if(maxWriteNum >=0) {
                            // 监听写锁，等待写锁消失
                            Stat stat = zk.exists(root + "/" + maxWriteNode, true);
                            if (stat != null) {
                                synchronized (this) {
                                    wait();
                                }
                            } else {
                                return;
                            }
                        }
                    }
                }
            }

            // 只要能获取到锁，读取完之后马上将读锁删除
            zk.delete(rkName, 0);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void process(WatchedEvent event) {
        synchronized (this) {
            notifyAll();
        }
    }
}
