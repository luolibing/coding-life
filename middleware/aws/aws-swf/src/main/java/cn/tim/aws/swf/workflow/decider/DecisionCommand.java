package cn.tim.aws.swf.workflow.decider;

import cn.tim.aws.swf.workflow.model.flow1.StartWorkerTaskHandler;
import cn.tim.aws.swf.workflow.worker.WorkerTaskHandler;

public enum DecisionCommand {

    START_MQ(StartWorkerTaskHandler.class);

    private Class<? extends WorkerTaskHandler> workerTaskHandlerClazz;

    DecisionCommand(Class<? extends WorkerTaskHandler> workerTaskHandlerClazz) {
        this.workerTaskHandlerClazz = workerTaskHandlerClazz;
    }

    public Class<? extends WorkerTaskHandler> getWorkerTaskHandlerClazz() {
        return workerTaskHandlerClazz;
    }
}
