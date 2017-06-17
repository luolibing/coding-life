package cn.tim.quartz.database.web;

import cn.tim.quartz.database.job.entity.JobDataForm;
import cn.tim.quartz.database.job.entity.JobPolicy;
import cn.tim.quartz.database.job.entity.JobType;
import cn.tim.quartz.database.service.JobService;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Date;

/**
 * Created by LuoLiBing on 16/6/24.
 */
@RestController
@RequestMapping(value = "/job")
public class JobController {

    @Autowired
    private JobService jobService;

    @RequestMapping(value = "/local/add")
    public Object addLocalJob(JobDataForm jobDataForm) throws SchedulerException {
        jobService.addJob(mock());
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/list")
    public Object listAllJob() throws SchedulerException {
        return Collections.singletonMap("result", jobService.getAllJob());
    }

    private JobDataForm mock() {
        JobDataForm dataForm = new JobDataForm();
        dataForm.setType(JobType.LOCAL.value());
        dataForm.setClazzName("cn.tim.quartz.database.task.SimpleLocalTask");
        dataForm.setMethod("execute");
        dataForm.setStartTime(new Date(new Date().getTime() + 1000 * 10));
        dataForm.setPolicy(JobPolicy.CRON.value());
        dataForm.setCronExpression("*/5 * * * * ?");
        dataForm.setArgs("1,2,3,4");
        return dataForm;
    }
}
