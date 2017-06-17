package cn.tim.quartz.database.job.entity;

import org.quartz.JobDataMap;
import org.quartz.core.jmx.JobDataMapSupport;

import java.util.HashMap;
import java.util.Map;
import static cn.tim.quartz.database.job.entity.JobDataKeys.*;

/**
 * Created by LuoLiBing on 16/6/24.
 * job实体
 */
public class JobData {

    private String url;

    private String clazzName;

    private String method;

    private String requestBody;

    private String args;

    private Integer type;

    public JobDataMap jobDataMap() {
        Map<String, Object> jobMap = new HashMap<>();
        jobMap.put(callBackUrl, url);
        jobMap.put(callBackMethod, method);
        jobMap.put(callBackRequestBody, requestBody);
        jobMap.put(className, clazzName);
        jobMap.put(invokeMethod, method);
        jobMap.put(invokeArgs, args);
        jobMap.put(callbackType, type.toString());
        return JobDataMapSupport.newJobDataMap(jobMap);
    }

    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getClazzName() {
        return clazzName;
    }

    public void setClazzName(String clazzName) {
        this.clazzName = clazzName;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
