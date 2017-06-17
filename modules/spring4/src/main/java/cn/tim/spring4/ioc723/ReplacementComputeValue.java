package cn.tim.spring4.ioc723;

import org.springframework.beans.factory.support.MethodReplacer;

import java.lang.reflect.Method;

/**
 * Created by LuoLiBing on 16/5/30.
 */
public class ReplacementComputeValue implements MethodReplacer {

    @Override
    public Object reimplement(Object obj, Method method, Object[] args) throws Throwable {
        String input = (String) args[0];
        return null;
    }
}
