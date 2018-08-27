package cn.tim.aws.swf.workflow.config;

import java.util.ArrayList;
import java.util.Collections;
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

    public List<WorkFlowStep> getWorkFlowStepList() {
        return Collections.unmodifiableList(workFlowStepList);
    }
}
