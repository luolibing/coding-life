package cn.tim.zookeeper.watcher;

import lombok.SneakyThrows;
import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.apache.zookeeper.Watcher.Event.KeeperState.SyncConnected;

/**
 * Watcher
 * Created by luolibing on 2019/3/11.
 */
public class RegisterWatcher implements Watcher {

    /**
     * 当在zk上设置watcher时，这个watcher主要监测会话的状态，连接关闭什么的
     * @param event
     */
    @Override
    public void process(WatchedEvent event) {
        System.out.println("register watcher - " + event);
    }

    static class Worker implements Runnable, Watcher {

        private ZooKeeper zk;

        private String hostName;

        private static final String WORKER_NODE_KEY = "/worker";

        private List<String> workerList;

        public Worker(ZooKeeper zk, String hostName) throws IOException {
            this.zk = zk;
            this.hostName = hostName;
            // 然后获取所有节点列表,同事watch/worker下子节点的变更
            this.workerList = getChildrenData();
            // 将自己注册到服务端
            register();
        }

        @Override
        @SneakyThrows
        public void run() {
            synchronized (this) {
                wait();
            }
        }

        @SneakyThrows
        private void register() {
            // 注册节点，临时节点，当节点下线时，临时节点会因为session会话关闭，而删除临时节点
            zk.create(WORKER_NODE_KEY  + "/" + hostName, hostName.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        }

        @SneakyThrows
        private List<String> getChildrenData() {
            return zk.getChildren(WORKER_NODE_KEY, this);
        }

        private void printWorkerList() {
            workerList.forEach(System.out::println);
        }

        @Override
        public void process(WatchedEvent event) {
            Event.KeeperState state = event.getState();
            if(state != SyncConnected) {
                return;
            }

            Event.EventType type = event.getType();
            System.out.println("receive state = " + state + ", type = " + type);
            // 重新获取所有子节点，同时设置下一个监视点
            this.workerList = getChildrenData();
            printWorkerList();
        }
    }

    /**
     * 1 worker在启动的时候，首先将自己注册到注册中心
     * 2 同时获取到所有节点，获取节点的同时设置watch监视，/worker下的子节点
     * 3 当有节点变更时，会发送事件到watcher里面
     * 4 接受到event之后，会再次去获取节点下的子节点，同时设置下一个监视点
     * @param args
     * @throws IOException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        // zk上设置的watcher，观察zk的状态
        ZooKeeper zk = new ZooKeeper("127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2184", 3000, new RegisterWatcher());
        for(int i = 0; i < 10; i++) {
            new Thread(new Worker(zk, "worker-" + i)).start();
        }
        TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
    }
}
