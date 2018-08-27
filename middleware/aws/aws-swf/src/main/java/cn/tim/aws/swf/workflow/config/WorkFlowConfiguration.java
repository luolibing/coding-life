package cn.tim.aws.swf.workflow.config;

import cn.tim.aws.swf.workflow.decider.DecisionCommand;
import cn.tim.aws.swf.workflow.decider.DecisionTaskHandler;
import cn.tim.aws.swf.workflow.model.flow1.FinishDecisionTaskHandler;
import cn.tim.aws.swf.workflow.model.flow1.Flow1Form;
import cn.tim.aws.swf.workflow.model.flow1.Flow1WorkFlowStep;
import cn.tim.aws.swf.workflow.model.flow1.StartDecisionTaskHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WorkFlowConfiguration {

    @Bean
    public WorkFlowDefinition workflow1() {
        return WorkFlowDefinition.builder()
                .workflowKey("workflow1")
                .workFlowSteps(
                        new WorkFlowSteps()
                                .flowStep(new Flow1WorkFlowStep<>("start", flow1StartDecisionTaskHandler()))
                                .flowStep(new Flow1WorkFlowStep<>("end", flow1FinishDecisionTaskHandler())))
                .build();
    }

    @Bean
    public DecisionTaskHandler<Long, Flow1Form, DecisionCommand> flow1StartDecisionTaskHandler() {
        return new StartDecisionTaskHandler();
    }

    @Bean DecisionTaskHandler<Long, Flow1Form, DecisionCommand> flow1FinishDecisionTaskHandler() {
        return new FinishDecisionTaskHandler();
    }


    @Bean
    public WorkFlowDefinition workflow2() {
        return WorkFlowDefinition.builder()
                .workflowKey("workflow2")
                .workFlowSteps(null)
                .build();
    }


    @Bean
    public WorkFlowDefinition workflow3() {
        return WorkFlowDefinition.builder()
                .workflowKey("workflow3")
                .workFlowSteps(null)
                .build();
    }
}
