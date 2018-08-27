package cn.tim.aws.swf.workflow.decider;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WorkFlowContext<K, D> {

    private K key;

    private D data;

}
