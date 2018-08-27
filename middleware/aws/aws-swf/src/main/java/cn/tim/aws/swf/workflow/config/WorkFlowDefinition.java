package cn.tim.aws.swf.workflow.config;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WorkFlowDefinition {

    private String workflowKey;

    private WorkFlowSteps workFlowSteps;
}
