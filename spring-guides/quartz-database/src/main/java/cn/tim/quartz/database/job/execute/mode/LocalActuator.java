package cn.tim.quartz.database.job.execute.mode;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;

import java.lang.reflect.Method;

/**
 * Created by LuoLiBing on 16/6/24.
 */
public class LocalActuator implements Actuator {

    @Override
    public void execute(JobExecutionContext context) {
        JobDataMap jobDataMap = context.getMergedJobDataMap();
        String clazzName = (String) jobDataMap.get(className);
        String methodName = (String) jobDataMap.get(invokeMethod);
        System.out.println(clazzName + ":" + methodName + ":" + methodName);
        try {
            Class<?> clazz = Class.forName(clazzName);
            Object o = clazz.newInstance();
            Method method = clazz.getMethod(methodName);
            method.invoke(o);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
