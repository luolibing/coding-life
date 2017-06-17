package cn.tim.zookeeper.barries;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;
import java.util.Objects;

import static org.apache.zookeeper.Watcher.Event.EventType.NodeCreated;

/**
 * 在/b下保存所有process node，并且所有的process阻塞监听/b/ready
 * 然后当达到指定个数时，插入一个/b/ready
 * User: luolibing
 * Date: 2017/6/6 17:17
 */
public class HighLevelBarrier implements Watcher {

    private ZooKeeper zk;

    private String root;

    private String name;

    private int count;

    private String readyPath;

    public HighLevelBarrier(String hostPort, String root, int count) {
        try {
            this.root = root;
            this.count = count;
            this.readyPath = root + "/ready";
            zk = new ZooKeeper(hostPort, 2000, this);

            // 创建出root节点
            Stat stat = zk.exists(root, false);
            if(stat == null) {
                zk.create(root, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }

            this.name = InetAddress.getLocalHost().getCanonicalHostName();
        } catch (IOException | InterruptedException | KeeperException e) {
            e.printStackTrace();
        }
    }

    public boolean enter() {
        // 先判断ready是否存在，如果存在，则直接执行，不需要再创建节点
        try {
            while (true) {
                Stat stat = zk.exists(readyPath, true);
                if(stat != null) {
                    return true;
                }

                String path = root + "/" + name;
                // 短暂节点，只在当前会话session生命周期内有效，断开连接就会自动删除掉
                zk.create(path, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);

                synchronized (this) {
                    List<String> children = zk.getChildren(root, false);
                    if (children.size() == count) {
                        zk.create(
                                readyPath,
                                new byte[0],
                                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                                CreateMode.PERSISTENT);
                    } else {
                        wait();
                    }
                }
            }
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void leave() {
        // 先判断是否存在/p/ready节点，如果存在表明还有节点没删除，需要继续等待
        // TODO 这个地方逻辑有点混乱
        while (true) {
            try {
                synchronized (this) {
                    List<String> children = zk.getChildren(root, false);
                    if (children.size() == 1) {
                        zk.delete(readyPath, 0);
                        return;
                    } else if(children.size() == 2) {
                        zk.delete(root + "/" + name, 0);
                        zk.delete(readyPath, 0);
                    } else if(children.size() == 0) {
                        return;
                    } else {
                        zk.delete(root + "/" + name, 0);
                    }
                }
            } catch (KeeperException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void process(WatchedEvent event) {
        synchronized (this) {
            System.out.println(event);
            if(event.getType() == NodeCreated && Objects.equals(event.getPath(), readyPath)) {
                notifyAll();
            }
        }
    }
}
