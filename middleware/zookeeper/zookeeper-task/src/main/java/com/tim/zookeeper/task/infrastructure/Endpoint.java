package com.tim.zookeeper.task.infrastructure;

import com.tim.zookeeper.task.domain.TaskConstant;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.apache.zookeeper.ZooDefs.Ids.OPEN_ACL_UNSAFE;

/**
 * Created by luolibing on 2019/3/19.
 */
@Slf4j
public class Endpoint implements Watcher {

    private static final int SESSION_TIME_OUT = 10_000;

    private static final String MASTER_PATH = TaskConstant.BASE_ZK_PATH + "/master";

    private ZooKeeper zooKeeper;

    private String hostPort;

    private static final String SERVICE_ID = UUID.randomUUID().toString();

    public Endpoint(String hostPort) {
        this.hostPort = hostPort;
    }

    @SneakyThrows
    public Endpoint startZk() {
        zooKeeper = new ZooKeeper(hostPort, SESSION_TIME_OUT, this);
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

    @Override
    public void process(WatchedEvent event) {
        log.info("endpoint receive watchEvent {}", event);
        if(event.getType() != Event.EventType.None) {
            return;
        }

        switch (event.getState()) {
            case SyncConnected:
                // 竞选master
                break;
            case Disconnected:
                break;
        }
    }

    /**
     * 竞选master，创建master自己的节点
     * @return
     */
    public Endpoint runForMaster() {
        while (true) {

            try {
                zooKeeper.create(MASTER_PATH, SERVICE_ID.getBytes(), OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
                return this;
            } catch (KeeperException e) {
                // todo 重试
            } catch (InterruptedException e) {
                return this;
            }
        }
    }

    @SneakyThrows
    public static void main(String[] args) {
        Endpoint endpoint = new Endpoint("127.0.0.1:2180");
        endpoint.startZk();
        endpoint.runForMaster();
        TimeUnit.SECONDS.sleep(10);
        endpoint.stopZk();
        TimeUnit.SECONDS.sleep(20);
    }
}
