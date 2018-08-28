package cn.tim.aws.swf.workflow.model.flow1;

import cn.tim.aws.swf.workflow.decider.Decision;
import cn.tim.aws.swf.workflow.decider.DecisionCommand;
import cn.tim.aws.swf.workflow.decider.DecisionTaskHandler;
import cn.tim.aws.swf.workflow.decider.WorkFlowContext;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class FinishDecisionTaskHandler implements DecisionTaskHandler<Long,Flow1Form,DecisionCommand> {

    @Override
    public Decision<DecisionCommand> decision(WorkFlowContext<Long, Flow1Form> workFlowContext) {
        return Decision.<DecisionCommand>builder()
                .decisionCommandList(Collections.singletonList(DecisionCommand.FINISH_MQ))
                .build();
    }
}
