package cn.tim.mybatis.scan;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * MapperFactoryBean通过实现InitializingBean来自动将其中包含的mapper加入到configuration当中.
 * User: luolibing
 * Date: 2017/9/8 15:17
 */
public class TimFactoryBean<T> implements FactoryBean<T>, InitializingBean {

    private Class<T> tClazz;

    public TimFactoryBean(Class<T> clazz) {
        this.tClazz = clazz;
    }

    @Override
    public T getObject() throws Exception {
        return null;
    }

    @Override
    public Class<?> getObjectType() {
        return tClazz;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
