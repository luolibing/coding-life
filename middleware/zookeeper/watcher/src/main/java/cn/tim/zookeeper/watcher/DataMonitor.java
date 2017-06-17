package cn.tim.zookeeper.watcher;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.Arrays;

import static org.apache.zookeeper.KeeperException.Code;

/**
 * User: luolibing
 * Date: 2017/6/6 10:45
 */
public class DataMonitor implements AsyncCallback.StatCallback {

    ZooKeeper zk;

    String znode;

    Watcher chainedWatcher;

    boolean dead;

    DataMonitorListener listener;

    byte prevData[];

    public DataMonitor(ZooKeeper zk, String znode, Watcher chainedWatcher,
                       DataMonitorListener listener) {
        this.zk = zk;
        this.znode = znode;
        this.chainedWatcher = chainedWatcher;
        this.listener = listener;

        // 调用该节点是否存在，异步调用返回
        zk.exists(znode, true, this, null);
    }

    /**
     * Other classes use the DataMonitor by implementing this method
     */
    public interface DataMonitorListener {
        /**
         * The existence status of the node has changed.
         */
        void exists(byte data[]);

        /**
         * The ZooKeeper session is no longer valid.
         *
         * @param rc
         *                the ZooKeeper reason code
         */
        void closing(int rc);
    }

    /**
     * event事件回调
     * @param event
     */
    public void process(WatchedEvent event) {
        String path = event.getPath();
        if (event.getType() == Watcher.Event.EventType.None) {
            // We are are being told that the state of the
            // connection has changed
            switch (event.getState()) {
                case SyncConnected:
                    // In this particular example we don't need to do anything
                    // here - watches are automatically re-registered with
                    // server and any watches triggered while the client was
                    // disconnected will be delivered (in order of course)
                    break;
                case Expired:
                    // It's all over
                    dead = true;
                    listener.closing(KeeperException.Code.SessionExpired);
                    break;
            }
        } else {
            if (path != null && path.equals(znode)) {
                // Something has changed on the node, let's find out
                zk.exists(znode, true, this, null);
            }
        }
        if (chainedWatcher != null) {
            chainedWatcher.process(event);
        }
    }

    /**
     * 调用exists之后的异步回调
     * @param rc
     * @param path
     * @param ctx
     * @param stat
     */
    public void processResult(int rc, String path, Object ctx, Stat stat) {
        boolean exists;
        switch (rc) {
            case Code.Ok:
                exists = true;
                break;
            case Code.NoNode:
                exists = false;
                break;
            // session过期，或者无法认证，调用listener的closing()
            case Code.SessionExpired:
            case Code.NoAuth:
                dead = true;
                listener.closing(rc);
                return;
            default:
                // 重试判断
                zk.exists(znode, true, this, null);
                return;
        }

        byte b[] = null;
        if (exists) {
            try {
                // 如果还存在，则获取数据保存起来
                b = zk.getData(znode, false, null);
            } catch (KeeperException e) {
                // We don't need to worry about recovering now. The watch
                // callbacks will kick off any exception handling
                e.printStackTrace();
            } catch (InterruptedException e) {
                return;
            }
        }
        if ((b == null && b != prevData)
                || (b != null && !Arrays.equals(prevData, b))) {
            listener.exists(b);
            prevData = b;
        }
    }
}
