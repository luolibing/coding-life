package cn.tim.aws.swf.workflow.model.flow1;

import cn.tim.aws.swf.workflow.decider.Decision;
import cn.tim.aws.swf.workflow.decider.DecisionCommand;
import cn.tim.aws.swf.workflow.decider.DecisionTaskHandler;
import cn.tim.aws.swf.workflow.decider.WorkFlowContext;

public class FinishDecisionTaskHandler implements DecisionTaskHandler<Long,Flow1Form,DecisionCommand> {

    @Override
    public Decision<DecisionCommand> decision(WorkFlowContext<Long, Flow1Form> workFlowContext) {
        return null;
    }
}
