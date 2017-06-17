package cn.tim.quartz.database.job.execute.mode;

import org.quartz.JobExecutionContext;

/**
 * Created by LuoLiBing on 16/6/24.
 * 执行器
 */
public interface Actuator {

    public String callBackUrl = "callBackUrl";

    public String callBackMethod = "callBackMethod";

    public String callBackRequestBody = "callBackRequestBody";

    public String callbackType = "callBackType";

    public String className = "className";

    public String invokeMethod = "invokeMethod";

    public String invokeArgs = "invokeArgs";

    void execute(JobExecutionContext context);
}
