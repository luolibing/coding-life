package cn.tim.aws.swf.workflow.model.flow1;

import cn.tim.aws.swf.workflow.decider.WorkFlowContext;
import cn.tim.aws.swf.workflow.worker.TaskResult;
import cn.tim.aws.swf.workflow.worker.WorkerTaskHandler;
import org.springframework.stereotype.Service;

@Service
public class StartWorkerTaskHandler implements WorkerTaskHandler<Void, Long, Flow1Form> {
    @Override
    public TaskResult<Void> execute(WorkFlowContext<Long, Flow1Form> workFlowContext) {
        System.out.println("start worker execute");
        return TaskResult.<Void>builder().build();
    }
}
