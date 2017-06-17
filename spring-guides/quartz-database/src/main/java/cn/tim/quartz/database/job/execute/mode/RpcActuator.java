package cn.tim.quartz.database.job.execute.mode;

import org.quartz.JobExecutionContext;

/**
 * Created by LuoLiBing on 16/6/24.
 */
public class RpcActuator implements Actuator {

    @Override
    public void execute(JobExecutionContext context) {
        String url = (String) context.get(callBackUrl);
        String method = (String) context.get(callBackMethod);
        String requestBodyStr = (String) context.get("callBackRequestBody");
        System.out.println(url + ":" + method + ":" + requestBodyStr);
    }
}
