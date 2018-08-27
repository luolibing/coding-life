package cn.tim.aws.swf.workflow.worker;

import cn.tim.aws.swf.workflow.decider.WorkFlowContext;

public interface WorkerTaskHandler<R, K, P> {

    TaskResult<R> execute(WorkFlowContext<K, P> workFlowContext);
}
