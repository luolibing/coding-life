package cn.tim.springsrc.ioc.replace;

import org.springframework.beans.factory.support.MethodOverride;

import java.lang.reflect.Method;

/**
 * Created by luolibing on 2017/5/11.
 */
public class WorkerMethodOverride extends MethodOverride {

    protected WorkerMethodOverride(String methodName) {
        super(methodName);
    }

    @Override
    public boolean matches(Method method) {
        return (method.getName().startsWith("get"));
    }
}
