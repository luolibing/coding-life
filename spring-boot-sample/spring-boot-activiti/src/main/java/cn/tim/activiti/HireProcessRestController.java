package cn.tim.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
public class HireProcessRestController {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private ApplicantRepository applicantRepository;

    @Autowired
    private ProcessEngine processEngine;

    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "/start-hire-process", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void startHireProcess(String name, String email, String phoneNumber) {
        // 查看当前正在执行的流程
        // Context.getExecutionContext().getProcessDefinition().getId();

        Applicant applicant = new Applicant(name, email, phoneNumber);
        applicantRepository.save(applicant);

        Map<String, Object> vars = Collections.<String, Object>singletonMap("applicant", applicant);
        // startProcess开启任务,传递参数
        runtimeService.startProcessInstanceByKey("test", vars);

        List<ProcessDefinition> list = processEngine.getRepositoryService().createProcessDefinitionQuery().list();
        for(ProcessDefinition processDefinition : list) {
            System.out.println("processDefinition = " + processDefinition);
        }
    }

}