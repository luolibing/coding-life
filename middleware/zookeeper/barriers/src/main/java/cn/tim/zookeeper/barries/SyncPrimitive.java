package cn.tim.zookeeper.barries;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.List;

/**
 * Zookeeper原生同步处理
 * User: luolibing
 * Date: 2017/6/6 14:36
 */
public class SyncPrimitive implements Watcher {

    static ZooKeeper zk = null;
    // 互斥量
    static Integer mutex;

    String root;

    public SyncPrimitive() {}

    SyncPrimitive(String address) {
        if(zk == null){
            try {
                System.out.println("Starting ZK:");
                // 设置了watcher，当有变动时会回调watch的process方法
                zk = new ZooKeeper(address, 3000, this);
                mutex = -1;
                System.out.println("Finished starting ZK: " + zk);
            } catch (IOException e) {
                System.out.println(e.toString());
                zk = null;
            }
        }
        //else mutex = new Integer(-1);
    }

    /**
     * 回调唤醒enter中等待的对象
     * @param event
     */
    @Override
    synchronized public void process(WatchedEvent event) {
        synchronized (mutex) {
            //System.out.println("Process: " + event.getType());
            mutex.notify();
        }
    }


    /**
     * 栅栏Barrier
     */
    static public class Barrier extends SyncPrimitive {
        int size;
        String name;

        public Barrier() {
            super();
        }

        /**
         * 同步查看root节点是否存在（这个root节点并不是/节点，而是一个Parent节点）
         * 1 查看root节点是否存在，不存在，创建出根节点
         * 2 根据本机地址，计算出该节点的名称
         *
         * @param address
         * @param root
         * @param size
         */
        public Barrier(String address, String root, int size) {
            super(address);
            this.root = root;
            this.size = size;

            if (zk != null) {
                try {
                    Stat s = zk.exists(root, false);
                    if (s == null) {
                        zk.create(root, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE,
                                CreateMode.PERSISTENT);
                    }
                } catch (KeeperException e) {
                    System.out
                            .println("Keeper exception when instantiating queue: "
                                    + e.toString());
                } catch (InterruptedException e) {
                    System.out.println("Interrupted exception");
                }
            }

            // My node name
            try {
                name = InetAddress.getLocalHost().getCanonicalHostName();
            } catch (UnknownHostException e) {
                System.out.println(e.toString());
            }

        }

        /**
         * 进入barrier，等待所有节点都进入barrier
         *
         * @return
         * @throws KeeperException
         * @throws InterruptedException
         */
        boolean enter() throws KeeperException, InterruptedException{
            // 在root节点下创建节点， CreateMode.EPHEMERAL_SEQUENTIAL短暂的节点
            zk.create(root + "/" + name, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.EPHEMERAL_SEQUENTIAL);
            while (true) {
                synchronized (mutex) {
                    // 查看节点数是否超过栅栏的size
                    List<String> list = zk.getChildren(root, true);

                    if (list.size() < size) {
                        // 等待回调唤醒
                        mutex.wait();
                    } else {
                        return true;
                    }
                }
            }
        }

        /**
         * Wait until all reach barrier
         * 离开barrier，直到所有的节点都离开barrier栅栏
         * @return
         * @throws KeeperException
         * @throws InterruptedException
         */
        boolean leave() throws KeeperException, InterruptedException{
            zk.delete(root + "/" + name, 0);
            while (true) {
                synchronized (mutex) {
                    List<String> list = zk.getChildren(root, true);
                    if (list.size() > 0) {
                        mutex.wait();
                    } else {
                        return true;
                    }
                }
            }
        }
    }


    /**
     * Producer-Consumer queue
     */
    static public class Queue extends SyncPrimitive {

        /**
         * Constructor of producer-consumer queue
         *
         * @param address
         * @param name
         */
        Queue(String address, String name) {
            super(address);
            this.root = name;
            // 与barrier一样，检查是否有root节点，没有则创建
            if (zk != null) {
                try {
                    Stat s = zk.exists(root, false);
                    if (s == null) {
                        zk.create(root, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE,
                                CreateMode.PERSISTENT);
                    }
                } catch (KeeperException e) {
                    System.out
                            .println("Keeper exception when instantiating queue: "
                                    + e.toString());
                } catch (InterruptedException e) {
                    System.out.println("Interrupted exception");
                }
            }
        }

        /**
         * Add element to the queue.
         *
         * @param i
         * @return
         */

        boolean produce(int i) throws KeeperException, InterruptedException{
            // 将数字添加到byteBuffer中
            ByteBuffer b = ByteBuffer.allocate(4);
            byte[] value;

            // 1个整形4字节
            b.putInt(i);
            value = b.array();
            // PERSISTENT_SEQUENTIAL 持久化的，当znode断开时不会自动删除，其名称将以单调递增的方式追加，例如/q/element0000001
            zk.create(root + "/element", value, ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.PERSISTENT_SEQUENTIAL);

            return true;
        }


        /**
         * Remove first element from the queue.
         *
         * @return
         * @throws KeeperException
         * @throws InterruptedException
         */
        int consume() throws KeeperException, InterruptedException{
            int retvalue = -1;
            Stat stat = null;

            // Get the first element available
            while (true) {
                synchronized (mutex) {
                    // 获取所有子节点，这个地方返回的是字典序，所以并不能直接取第一个
                    List<String> list = zk.getChildren(root, true);
                    if (list.size() == 0) {
                        System.out.println("Going to wait");
                        mutex.wait();
                    } else {
                        // 要重找找出最小的编号，最小编号的为第一个入队的元素
                        Integer min = new Integer(list.get(0).substring(7));
                        for(String s : list){
                            Integer tempValue = new Integer(s.substring(7));
                            //System.out.println("Temporary value: " + tempValue);
                            if(tempValue < min) min = tempValue;
                        }

                        // 找到最小的节点，获取到数据然后从中删除掉
                        System.out.println("Temporary value: " + root + "/element" + min);
                        byte[] b = zk.getData(root + "/element" + String.format("%010d", min),
                                false, stat);
                        zk.delete(root + "/element" + String.format("%010d", min), 0);
                        ByteBuffer buffer = ByteBuffer.wrap(b);
                        retvalue = buffer.getInt();

                        return retvalue;
                    }
                }
            }
        }
    }

}
