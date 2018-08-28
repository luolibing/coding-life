package cn.tim.aws.swf.workflow.engine;

import cn.tim.aws.swf.workflow.config.WorkFlowDefinition;
import cn.tim.aws.swf.workflow.config.WorkFlowStep;
import cn.tim.aws.swf.workflow.decider.Decision;
import cn.tim.aws.swf.workflow.decider.DecisionCommand;
import cn.tim.aws.swf.workflow.decider.WorkFlowContext;
import cn.tim.aws.swf.workflow.model.flow1.Flow1Form;
import cn.tim.aws.swf.workflow.worker.WorkerTaskHandler;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class WorkFlowExecution implements ApplicationContextAware {

    private Map<String, WorkFlowDefinition> workFlowDefinitionMap;

    private ApplicationContext applicationContext;

    public void startup(Long flowKey, String actionKey, Flow1Form flow1Form) {
        WorkFlowDefinition workflow1 = workFlowDefinitionMap.get("workflow1");
        WorkFlowStep workFlowStep = workflow1.getWorkFlowSteps().getWorkFlowStep(actionKey);
        Decision decision = workFlowStep.getDecisionTaskHandler()
                .decision(WorkFlowContext.<Long, Flow1Form>builder().key(flowKey).data(flow1Form).build());
        List<DecisionCommand> decisionCommandList = decision.getDecisionCommandList();
        decisionCommandList.forEach(command -> this.execute(flowKey, command));
    }

    private void execute(Long flowKey, DecisionCommand command) {
        Class<? extends WorkerTaskHandler> clazz = command.getWorkerTaskHandlerClazz();
        WorkerTaskHandler taskHandler = applicationContext.getBean(clazz);
        taskHandler.execute(WorkFlowContext.<Long, Flow1Form>builder().key(flowKey).build());
    }

    @Autowired
    public void initWorkFlowDefinitions(List<WorkFlowDefinition> workFlowDefinitionList) {
        if(CollectionUtils.isEmpty(workFlowDefinitionList)) {
            workFlowDefinitionMap = Collections.emptyMap();
            return;
        }

        Map<String, WorkFlowDefinition> map = workFlowDefinitionList.stream()
                .collect(Collectors.toMap(WorkFlowDefinition::getWorkflowKey, Function.identity()));
        workFlowDefinitionMap = Collections.unmodifiableMap(map);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
