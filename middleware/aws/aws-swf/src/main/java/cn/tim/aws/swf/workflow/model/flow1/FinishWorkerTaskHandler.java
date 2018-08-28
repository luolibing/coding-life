package cn.tim.aws.swf.workflow.model.flow1;

import cn.tim.aws.swf.workflow.decider.WorkFlowContext;
import cn.tim.aws.swf.workflow.worker.TaskResult;
import cn.tim.aws.swf.workflow.worker.WorkerTaskHandler;
import org.springframework.stereotype.Service;

/**
 * Created by luolibing on 2018/8/28.
 */
@Service
public class FinishWorkerTaskHandler implements WorkerTaskHandler<Void, Long, Flow1Form> {
    @Override
    public TaskResult<Void> execute(WorkFlowContext<Long, Flow1Form> workFlowContext) {
        System.out.println("send finish mq");
        return TaskResult.<Void>builder().build();
    }
}
