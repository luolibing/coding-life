package cn.tim.aspect.api.config;


import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.util.Arrays;

/**
 * User: luolibing
 * Date: 2018/3/24 17:16
 */
public class MethodLogInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        System.out.println("methodName = " + invocation.getMethod().getName()
                + " , arg[] = " + Arrays.toString(invocation.getArguments()));
        try {
            return invocation.proceed();
        } catch (Exception e) {
            throw e;
        } finally {
            System.out.println("finish invoke " + invocation.getMethod().getName());
        }
    }
}
