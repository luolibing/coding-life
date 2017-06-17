package cn.tim.quartz.database.job.execute.policy;

import cn.tim.quartz.database.job.execute.DefaultJob;
import cn.tim.quartz.database.utils.ApplicationContextProvider;
import org.quartz.*;

/**
 * Created by LuoLiBing on 16/6/24.
 */
public class CronExecutionPolicy extends ExecuteTimePolicy {

    private String cronExpression;

    private JobDataMap jobData;

    private Scheduler scheduler = ApplicationContextProvider.getBean(Scheduler.class);

    public CronExecutionPolicy(String cronExpression, JobDataMap jobData) {
        this.cronExpression = cronExpression;
        this.jobData = jobData;
    }

    @Override
    public void build() throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(DefaultJob.class)
                .storeDurably(true)
                .setJobData(jobData)
                .build();
        CronTrigger trigger = TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                .build();
        scheduler.scheduleJob(jobDetail, trigger);
    }
}
