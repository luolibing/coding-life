package cn.tim.aws.swf.workflow.engine;

import cn.tim.aws.swf.workflow.config.WorkFlowDefinition;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class WorkFlowExecution implements ApplicationContextAware {

    private Map<String, WorkFlowDefinition> workFlowDefinitionMap;

    private ApplicationContext applicationContext;


    @PostConstruct
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
