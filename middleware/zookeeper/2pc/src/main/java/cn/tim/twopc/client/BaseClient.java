package cn.tim.twopc.client;

import cn.tim.twopc.transaction.TransactionTask;
import cn.tim.twopc.zookeeper.BaseZookeeper;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.ZooDefs;

import java.util.Random;
import java.util.function.Supplier;

/**
 * User: luolibing
 * Date: 2017/6/8 12:40
 */
public class BaseClient<T> extends BaseZookeeper implements ClientTransaction {

    private TransactionTask task;

    private String nodePath;

    private boolean callback = false;

    private Supplier<T> supplier;

    private Random rand = new Random(47);

    public BaseClient(String hostPort, TransactionTask task, Supplier<T> action) {
        super(hostPort);
        this.task = task;
        this.supplier = action;
    }

    @Override
    public void process(WatchedEvent event) {
        // 监听自己节点的变化
        Event.EventType eventType = event.getType();
        switch (eventType) {
            case NodeDataChanged: {
                try {
                    byte[] data = zooKeeper.getData(nodePath, false, null);
                    String content = new String(data);
                    switch (content) {
                        case "ok": {
                            System.out.println("client commit");
                            callback = true;
                            synchronized (this) {
                                notifyAll();
                            }
                        } break;

                        case "rollback": {
                            System.out.println("client rollback");
                            callback = true;
                            synchronized (this) {
                                notifyAll();
                            }
                        } break;
                    }
                } catch (KeeperException | InterruptedException e) {
                    e.printStackTrace();
                }
            } break;
        }
    }

    @Override
    public T beginTransaction() {
        System.out.println("beginTransaction");
        try {
            T t = supplier.get();
            commit();
            return t;
        } catch (Exception e) {
            rollback();
//            throw new RuntimeException("rollback");
            return null;
        }
    }

    @Override
    public void rollback() {
        try {
            // 创建节点
            this.nodePath = zooKeeper.create(
                    task.getTxPath() + "/node-",
                    "abort".getBytes(),
                    ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.EPHEMERAL_SEQUENTIAL);

            // 设置监听
            while (!callback) {
                byte[] data = zooKeeper.getData(nodePath, true, null);
                if (data == null || !"rollback".equals(new String(data))) {
                    synchronized (this) {
                        wait();
                    }
                } else {
                    break;
                }
            }
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void commit() {
        try {
            // 创建节点
            this.nodePath = zooKeeper.create(
                    task.getTxPath() + "/node-",
                    "commit".getBytes(),
                    ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.EPHEMERAL_SEQUENTIAL);

            // 设置监听
            while (!callback) {
                byte[] data = zooKeeper.getData(nodePath, true, null);
                if (data == null || !"ok".equals(new String(data))) {
                    synchronized (this) {
                        wait();
                    }
                } else {
                    break;
                }
            }
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
