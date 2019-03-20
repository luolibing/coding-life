package com.tim.zookeeper.task.infrastructure;

/**
 * 所有节点都是worker。都能进行选举
 * Created by luolibing on 2019/3/19.
 */
public class Worker {

    private Endpoint endpoint;

    public Worker(String hostPort) {
        endpoint = new Endpoint(hostPort);
    }

    public void start() {
        endpoint.startZk();
        boolean master = endpoint.runForMaster();
        if(master) {
            // 接管成为master
        } else {
            boolean worker = endpoint.runForWorker();
            if(worker) {
                // 进入任务工作状态
            }
        }
    }
}
