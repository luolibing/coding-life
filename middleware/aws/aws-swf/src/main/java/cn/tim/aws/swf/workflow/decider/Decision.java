package cn.tim.aws.swf.workflow.decider;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class Decision<D> {

    private List<DecisionCommand> decisionCommandList;

    private D data;
}
