package cn.tim.mybatis.scan;

import java.lang.reflect.Proxy;

/**
 * User: luolibing
 * Date: 2017/9/8 15:21
 */
public class TimObjectProxyFactory<T> {

    private Class<T> clazz;

    public TimObjectProxyFactory(Class<T> clazz) {
        this.clazz = clazz;
    }

    protected T newInstance(TimObjectProxy<T> mapperProxy) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[] { clazz }, mapperProxy);
    }

    public T newInstance() {
        final TimObjectProxy <T> mapperProxy = new TimObjectProxy<T>(clazz);
        return newInstance(mapperProxy);
    }
}
