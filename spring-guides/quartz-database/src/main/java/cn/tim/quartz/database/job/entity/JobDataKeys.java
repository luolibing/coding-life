package cn.tim.quartz.database.job.entity;

/**
 * Created by LuoLiBing on 16/6/24.
 */
public interface JobDataKeys {

    String callBackUrl = "callBackUrl";

    String callBackMethod = "callBackMethod";

    String callBackRequestBody = "callBackRequestBody";

    String callbackType = "callBackType";

    String className = "className";

    String invokeMethod = "invokeMethod";

    String invokeArgs = "invokeArgs";
}
