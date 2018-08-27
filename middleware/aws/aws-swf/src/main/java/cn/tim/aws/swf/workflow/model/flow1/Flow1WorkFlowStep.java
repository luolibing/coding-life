package cn.tim.aws.swf.workflow.model.flow1;

import cn.tim.aws.swf.workflow.config.WorkFlowStep;
import cn.tim.aws.swf.workflow.decider.DecisionTaskHandler;

public class Flow1WorkFlowStep<F> extends WorkFlowStep {

    public Flow1WorkFlowStep(String step, DecisionTaskHandler<Long, Flow1Form, F> decisionTaskHandler) {
        super(step, decisionTaskHandler);
    }
}
