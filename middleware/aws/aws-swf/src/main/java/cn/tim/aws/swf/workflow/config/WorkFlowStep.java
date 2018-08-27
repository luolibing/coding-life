package cn.tim.aws.swf.workflow.config;

import cn.tim.aws.swf.workflow.decider.DecisionTaskHandler;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WorkFlowStep<K, P, R> {

    protected String step;

    protected DecisionTaskHandler<K, P, R> decisionTaskHandler;

    public WorkFlowStep(String step, DecisionTaskHandler<K, P, R> decisionTaskHandler) {
        this.step = step;
        this.decisionTaskHandler = decisionTaskHandler;
    }
}
