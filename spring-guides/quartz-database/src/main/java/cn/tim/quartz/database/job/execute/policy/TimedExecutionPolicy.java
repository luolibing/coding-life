package cn.tim.quartz.database.job.execute.policy;

import cn.tim.quartz.database.job.execute.DefaultJob;
import cn.tim.quartz.database.utils.ApplicationContextProvider;
import org.quartz.*;

import java.util.Date;

/**
 * Created by LuoLiBing on 16/6/24.
 */
public class TimedExecutionPolicy extends ExecuteTimePolicy {

    private Date startTime;

    private JobDataMap jobData;

    private Scheduler scheduler = ApplicationContextProvider.getBean(Scheduler.class);

    public TimedExecutionPolicy(Date startTime, JobDataMap jobData) {
        this.startTime = startTime;
        this.jobData = jobData;
    }

    @Override
    public void build() throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(DefaultJob.class)
                .storeDurably(true)
                .setJobData(jobData)
                .build();
        Trigger trigger = TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .startAt(startTime)
                .build();
        scheduler.scheduleJob(jobDetail, trigger);
    }
}
