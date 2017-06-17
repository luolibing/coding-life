package cn.tim.twopc.coordinator;

import cn.tim.twopc.transaction.TransactionTask;
import cn.tim.twopc.transaction.TwoPcTransaction;
import cn.tim.twopc.zookeeper.BaseZookeeper;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;

import java.util.List;

/**
 * 协调者
 * User: luolibing
 * Date: 2017/6/8 11:20
 */
public class Coordinator extends BaseZookeeper implements TwoPcTransaction {

    private final static String baseRoot = "/twopc";

    private String txPath;

    private int count;

    public Coordinator(String hostPort, String transactionName, int count) {
        super(hostPort);
        this.txPath = baseRoot + "/" + transactionName;
        this.count = count;

        try {
            Stat stat = zooKeeper.exists(baseRoot, false);
            if(stat == null) {
                zooKeeper.create(baseRoot, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            }
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(transactionName);
    }


    @Override
    public TransactionTask createTransaction() {
        try {
            zooKeeper.create(
                    txPath,
                    new byte[0],
                    ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.PERSISTENT);

            // 设置监控
            zooKeeper.getChildren(txPath, true);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }

        TransactionTask transaction = new TransactionTask();
        transaction.setTxPath(txPath);
        transaction.setStatus(TransactionTask.Status.create);
        return transaction;
    }

    @Override
    public void decideTransaction(List<String> children) {
        boolean veto = false;
        for(String child : children) {
            try {
                byte[] data = zooKeeper.getData(txPath + "/" + child, false, null);
                String content = new String(data);
                // 一票否决
                if ("abort".equals(content)) {
                    veto = true;
                }
            } catch (Exception e) {
            }
        }

        String content;
        if(veto) {
            // 否决
            content = "rollback";
        } else {
            // 通过
            content = "ok";
        }

        for(String child : children) {
            try {
                zooKeeper.setData(txPath + "/" + child, content.getBytes(), 0);
            } catch (KeeperException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void submit(TransactionTask transaction) {

    }

    @Override
    public void process(WatchedEvent event) {
        System.out.println("coordinator event = " + event);
        Event.EventType type = event.getType();
        switch (type) {
            case NodeChildrenChanged: {
                try {
                    List<String> children = zooKeeper.getChildren(txPath, false);
                    System.out.println(children);
                    if(children != null && children.size() == count) {
                        decideTransaction(children);
                    }
                } catch (KeeperException | InterruptedException e) {
                    e.printStackTrace();
                }
            } break;
        }
    }
}
