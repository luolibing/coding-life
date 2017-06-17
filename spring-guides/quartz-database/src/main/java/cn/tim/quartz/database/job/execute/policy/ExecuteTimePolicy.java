package cn.tim.quartz.database.job.execute.policy;

import org.quartz.SchedulerException;

/**
 * Created by LuoLiBing on 16/6/24.
 */
public abstract class ExecuteTimePolicy {

    public String callBackUrl = "callBackUrl";

    public String callBackMethod = "callBackMethod";

    public String callBackRequestBody = "callBackRequestBody";

    public String callbackType = "callBackType";

    public String className = "className";

    public String invokeMethod = "invokeMethod";

    public String invokeArgs = "invokeArgs";

    public abstract void build() throws SchedulerException;
}
