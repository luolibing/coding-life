package cn.tim.quartz.database.service;

import cn.tim.quartz.database.job.JobFactory;
import cn.tim.quartz.database.job.entity.JobDataForm;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by LuoLiBing on 16/6/24.
 */
@Service
public class JobService {

    @Autowired
    private Scheduler scheduler;

    public void addJob(JobDataForm jobDataForm) throws SchedulerException {
        JobFactory.createJobBuild(jobDataForm).build();
    }

    public void shutDownJob(TriggerKey triggerKey) throws SchedulerException {
        scheduler.unscheduleJob(triggerKey);
    }

    public List<String> getAllJob() throws SchedulerException {
        List<String> result = scheduler.getJobGroupNames();
        for (String groupName : scheduler.getJobGroupNames()) {

            for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {

                String jobName = jobKey.getName();
                String jobGroup = jobKey.getGroup();

                //get job's trigger
                List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);
                if(triggers.isEmpty()) {
                    continue;
                }
                Date nextFireTime = triggers.get(0).getNextFireTime();
                System.out.println("[jobName] : " + jobName + " [groupName] : "
                        + jobGroup + " - " + nextFireTime);

            }

        }
        return result;
    }
}
