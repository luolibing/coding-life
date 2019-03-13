package com.tim.zookeeper.master;

import lombok.SneakyThrows;
import org.apache.zookeeper.*;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * 需要实现的功能是，worker作为工作台，它有2个角色，master或者slave。 master只有一个slave有1个或者多个
 * 所有机器都可以参与选举，目前选举的方式是，slave排在第一个的先得。master失效之后，所有的slave都可以参与竞选
 *
 * 实现细节，master的nodePath=/master, slave的path=/slaves
 *
 * TODO 实现有问题
 * Created by luolibing on 2019/3/14.
 */
public class Worker implements Watcher {

    private static final String MASTER_PATH = "/master";

    private static final String SLAVE_PATH = "/slaves";

    private Boolean master = true;

    private List<String> masterList;

    private List<String> slaveList;

    private ZooKeeper zk;

    private String name;

    public Worker(ZooKeeper zk, String name) {
        this.zk = zk;
        this.name = name;
        election();
        refresh();
    }

    @SneakyThrows
    public void election() {
        try {
            // 参与选举的情况，没有master，或者slaveList第一个机器
            boolean election = (!CollectionUtils.isEmpty(slaveList) && name.equals(slaveList.get(0))) || CollectionUtils.isEmpty(masterList);
            if(election) {
                zk.create(MASTER_PATH, name.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
                master = true;
            } else {
                if(master == null) {
                    zk.create(SLAVE_PATH + "/" + name, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
                    master = false;
                }
            }
        } catch (KeeperException | InterruptedException e) {
            zk.create(SLAVE_PATH, name.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            master = false;
        }
    }

    public void refresh() {
        try {
            slaveList = zk.getChildren(SLAVE_PATH, this);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }

        try {
            byte[] data = zk.getData(MASTER_PATH, this, null);
            if(data != null) {
                masterList = Collections.singletonList(new String(data));
            }
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void process(WatchedEvent event) {
        String path = event.getPath();
        Event.EventType type = event.getType();
        if(MASTER_PATH.equals(path)) {
            if(type == Event.EventType.NodeChildrenChanged) {
                refresh();
            } else if(type == Event.EventType.NodeDeleted) {
                election();
                refresh();
            }
        }

        if(SLAVE_PATH.equals(path)) {
            if(type == Event.EventType.NodeChildrenChanged) {
                refresh();
            }
        }
    }

    public void printAll() {
        System.out.println("master = " + masterList);
        System.out.println("slaves = " + slaveList );
    }
}
