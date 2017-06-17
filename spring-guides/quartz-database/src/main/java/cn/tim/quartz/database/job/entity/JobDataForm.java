package cn.tim.quartz.database.job.entity;

import org.jdto.annotation.Source;

import java.util.Date;

/**
 * Created by LuoLiBing on 16/6/24.
 */
public class JobDataForm {

    private String url;

    @Source(value = "className")
    private String clazzName;

    private String method;

    private String requestBody;

    private Integer type;

    private Integer policy;

    private String cronExpression;

    private String args;

    private Date startTime;

    public boolean isLocal() {
        return JobType.LOCAL.match(type);
    }

    public boolean isCron() {
        return JobPolicy.CRON.match(policy);
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

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
    }

    public Integer getPolicy() {
        return policy;
    }

    public void setPolicy(Integer policy) {
        this.policy = policy;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
}
