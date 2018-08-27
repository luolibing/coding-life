package cn.tim.aws.swf.workflow.config;

import org.apache.commons.codec.binary.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class WorkFlowSteps {

    private List<WorkFlowStep> workFlowStepList;

    public WorkFlowSteps() {
        workFlowStepList = new ArrayList<>();
    }

    public WorkFlowSteps flowStep(WorkFlowStep workFlowStep) {
        workFlowStepList.add(workFlowStep);
        return this;
    }

    public WorkFlowStep getWorkFlowStep(String stepKey) {
        return workFlowStepList.stream()
                .filter(k -> StringUtils.equals(stepKey, k.getStep()))
                .findFirst()
                .orElse(null);
    }
}
