package com.tim.zookeeper.task.infrastructure;

import com.tim.zookeeper.task.domain.TaskConstant;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.apache.zookeeper.ZooDefs.Ids.OPEN_ACL_UNSAFE;

/**
 * Created by luolibing on 2019/3/19.
 */
@Slf4j
public class Endpoint {

    private static final int SESSION_TIME_OUT = 10_000;

    private static final String MASTER_PATH = TaskConstant.BASE_ZK_PATH + "/master";

    private static final String WORKER_PATH = TaskConstant.BASE_ZK_PATH + "/worker";

    private ZooKeeper zooKeeper;

    private String hostPort;

    private final String SERVICE_ID = UUID.randomUUID().toString();

    private boolean master = false;

    public Endpoint(String hostPort) {
        this.hostPort = hostPort;
    }

    @SneakyThrows
    public Endpoint startZk() {
        zooKeeper = new ZooKeeper(hostPort, SESSION_TIME_OUT, (event) -> {
            if(event.getType() == Watcher.Event.EventType.None) {
                switch (event.getState()) {
                    case SyncConnected:
                        // 竞选master
                        break;
                    case Disconnected:
                        break;
                }
            }
        });
        return this;
    }

    /**
     * 主动关闭的时候并不会发布watchedEvent事件
     * @return
     */
    @SneakyThrows
    public Endpoint stopZk() {
        zooKeeper.close();
        return this;
    }

    /**
     * 竞选master，创建master自己的节点
     * @return
     */
    @SneakyThrows
    public boolean runForMaster() {
        while (true) {

            try {
                zooKeeper.create(MASTER_PATH, SERVICE_ID.getBytes(), OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
                master = true;
                return true;
            } catch (KeeperException e) {
                if(e instanceof KeeperException.NodeExistsException) {
                    zooKeeper.exists(MASTER_PATH, event -> {
                        switch (event.getType()) {
                            case NodeDeleted:
                                // 竞选master
                                runForMaster();
                                break;
                        }
                    });
                    return false;
                }
            } catch (InterruptedException e) {

            }
        }
    }

    @SneakyThrows
    public boolean runForWorker() {
        while (true) {

            try {
                zooKeeper.create(WORKER_PATH + "-" + SERVICE_ID, SERVICE_ID.getBytes(), OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
                return true;
            } catch (KeeperException e) {
                if(e instanceof KeeperException.NodeExistsException) {
                    return false;
                }
            } catch (InterruptedException e) {

            }
        }
    }

    public boolean isMaster() {
        return master;
    }

    @SneakyThrows
    public static void main(String[] args) {
        Endpoint endpoint1 = new Endpoint("127.0.0.1:2180");
        endpoint1.startZk();
        endpoint1.runForMaster();

        Endpoint endpoint2 = new Endpoint("127.0.0.1:2180");
        endpoint2.startZk();
        endpoint2.runForMaster();
        TimeUnit.SECONDS.sleep(10);
        endpoint1.stopZk();
        TimeUnit.SECONDS.sleep(10);
        endpoint2.stopZk();
        TimeUnit.SECONDS.sleep(20);
    }
}
