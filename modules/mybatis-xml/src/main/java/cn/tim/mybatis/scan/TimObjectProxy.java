package cn.tim.mybatis.scan;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * User: luolibing
 * Date: 2017/9/8 15:24
 */
public class TimObjectProxy<T> implements InvocationHandler {

    private Class<T> clazz;

    public TimObjectProxy(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return null;
    }
}
