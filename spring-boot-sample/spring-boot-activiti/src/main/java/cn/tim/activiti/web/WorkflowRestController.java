package cn.tim.activiti.web;

import cn.tim.activiti.entity.Report;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by luolibing on 2017/6/20.
 */
@RestController
public class WorkflowRestController {

    @Autowired
    private ProcessEngine processEngine;

    @Autowired
    private RuntimeService runtimeService;

    @GetMapping("/taskList")
    public List<Task> taskList() {
        return processEngine
                .getTaskService()
                .createTaskQuery()
                .list();
    }

    @PostMapping("/createReport")
    public void completeTask(String taskId) {
        Report report = new Report();
        report.setId(1L);
        report.setFile(new File("/share/1.report"));
        String yearMonth = new SimpleDateFormat("yyyyMM").format(new Date());
        report.setReportName(yearMonth + "月份报告");
        Map<String, Object> vars = new HashMap<>();
        vars.put("report", report);
        processEngine.getTaskService().complete(taskId, vars);
    }

    @PostMapping("/start-myprocess")
    public void startMyProcess() {

        String yearMonth = new SimpleDateFormat("yyyyMM").format(new Date());
        Map<String, Object> vars = Collections.singletonMap("yearMonth", yearMonth);
        // startProcess开启任务,传递参数
        runtimeService.startProcessInstanceByKey("myProcess", vars);
    }
}
