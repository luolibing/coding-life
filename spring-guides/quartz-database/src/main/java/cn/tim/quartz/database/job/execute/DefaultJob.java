package cn.tim.quartz.database.job.execute;

import cn.tim.quartz.database.job.execute.mode.Actuator;
import cn.tim.quartz.database.job.execute.policy.ExecuteTimePolicy;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;

/**
 * Created by LuoLiBing on 16/6/23.
 */
public class DefaultJob implements JobBuilder, Job {

    private ExecuteTimePolicy executeTimePolicy;

    private Actuator actuator;

    public DefaultJob() {
    }

    public DefaultJob(ExecuteTimePolicy executeTimePolicy) {
        this.executeTimePolicy = executeTimePolicy;
    }

    public DefaultJob(Actuator actuator) {
        this.actuator =actuator;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        actuator.execute(context);
    }

    @Override
    public void build() throws SchedulerException {
        executeTimePolicy.build();
    }
}
