package cn.tim.aws.swf.workflow.decider;

/**
 * 决策
 */
public interface DecisionTaskHandler<K, P, R> {

    Decision<R> decision(WorkFlowContext<K, P> workFlowContext);
}
