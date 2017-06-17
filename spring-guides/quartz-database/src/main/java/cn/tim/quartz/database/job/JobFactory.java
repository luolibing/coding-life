package cn.tim.quartz.database.job;

import cn.tim.quartz.database.job.entity.*;
import cn.tim.quartz.database.job.execute.DefaultJob;
import cn.tim.quartz.database.job.execute.JobBuilder;
import cn.tim.quartz.database.job.execute.mode.LocalActuator;
import cn.tim.quartz.database.job.execute.mode.RpcActuator;
import cn.tim.quartz.database.job.execute.policy.CronExecutionPolicy;
import cn.tim.quartz.database.job.execute.policy.ExecuteTimePolicy;
import cn.tim.quartz.database.job.execute.policy.TimedExecutionPolicy;
import cn.tim.quartz.database.utils.ApplicationContextProvider;
import org.jdto.DTOBinder;
import org.quartz.Job;
import org.quartz.JobDataMap;

/**
 * Created by LuoLiBing on 16/6/24.
 */
public class JobFactory {

    private static Job localJob = new DefaultJob(new LocalActuator());

    private static Job rpcJob = new DefaultJob(new RpcActuator());

    private static DTOBinder dtoBinder = ApplicationContextProvider.getBean(DTOBinder.class);

    public static Job createCallbackJob(JobDataMap jobDataMap) throws UnsupportedOperationException {
        Integer callbackType = jobDataMap.getIntegerFromString(JobDataKeys.callbackType);
        if(callbackType == null) {
            throw new UnsupportedOperationException();
        }
        if(callbackType == JobType.LOCAL.value()) {
            return localJob;
        } else if(callbackType == JobType.RPC.value()) {
            return rpcJob;
        }
        throw new UnsupportedOperationException();
    }

    public static JobBuilder createJobBuild(JobDataForm jobDataForm) throws UnsupportedOperationException {
        ExecuteTimePolicy timePolicy;
        JobData jobData = dtoBinder.bindFromBusinessObject(JobData.class, jobDataForm);
        JobDataMap jobDataMap = jobData.jobDataMap();
        if(JobPolicy.CRON.match(jobDataForm.getPolicy())) {
            timePolicy = new CronExecutionPolicy(jobDataForm.getCronExpression(), jobDataMap);
        } else if(JobPolicy.TIMER.match(jobDataForm.getPolicy())){
            timePolicy = new TimedExecutionPolicy(jobDataForm.getStartTime(), jobDataMap);
        } else {
            throw new UnsupportedOperationException();
        }
        return new DefaultJob(timePolicy);
    }
}
