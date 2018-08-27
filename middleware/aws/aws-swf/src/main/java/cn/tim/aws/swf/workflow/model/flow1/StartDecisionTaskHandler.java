package cn.tim.aws.swf.workflow.model.flow1;

import cn.tim.aws.swf.workflow.decider.Decision;
import cn.tim.aws.swf.workflow.decider.DecisionCommand;
import cn.tim.aws.swf.workflow.decider.DecisionTaskHandler;
import cn.tim.aws.swf.workflow.decider.WorkFlowContext;

import java.util.Collections;

public class StartDecisionTaskHandler implements DecisionTaskHandler<Long, Flow1Form, DecisionCommand> {

    @Override
    public Decision<DecisionCommand> decision(WorkFlowContext<Long, Flow1Form> workFlowContext) {
        Flow1Form data = workFlowContext.getData();
        System.out.println("receive data form = " + data);
        return Decision.<DecisionCommand>builder()
                .decisionCommandList(Collections.singletonList(DecisionCommand.START_MQ))
                .build();
    }
}
